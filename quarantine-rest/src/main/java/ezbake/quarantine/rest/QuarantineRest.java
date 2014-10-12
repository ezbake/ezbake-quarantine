/*   Copyright (C) 2013-2014 Computer Sciences Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */

package ezbake.quarantine.rest;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import ezbake.base.thrift.EzSecurityTokenException;
import ezbake.configuration.EzConfiguration;
import ezbake.configuration.EzConfigurationLoaderException;
import ezbake.quarantine.client.QuarantineClient;
import ezbake.quarantine.thrift.*;
import ezbake.security.client.EzSecurityTokenWrapper;
import ezbake.security.client.EzbakeSecurityClient;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.*;

@Path("quarantine")
public class QuarantineRest {
    private static final Logger logger = LoggerFactory.getLogger(QuarantineRest.class);
    private final Set<ObjectStatus> allStatuses = Sets.newHashSet(ObjectStatus.values());
    private final Set<ObjectStatus> quarantinedOnly = Sets.newHashSet(ObjectStatus.QUARANTINED);

    private static QuarantineClient client = null;
    private static EzbakeSecurityClient security = null;

    static {
        logger.info("Initializing Quarantine resource");
        try {
            Properties props = new EzConfiguration().getProperties();
            client = new QuarantineClient(props);
            security = new EzbakeSecurityClient(props);
        } catch (EzConfigurationLoaderException e) {
            logger.error("Could not load ezconfiguration properties", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves information about the last event for a given pipeline
     * @param pipelineId the pipeline id to retrieve the info for
     * @return last event for the provided pipeline
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pipeline/{pipelineId}")
    public String getLastEventForPipeline(@PathParam("pipelineId") String pipelineId) {
        try {
            EventWithCount event = client.getLatestEventForPipeline(pipelineId, allStatuses);
            String json = new TSerializer(new TSimpleJSONProtocol.Factory()).toString(event.getEvent());

            long size = event.getCount();
            String eventType = (EventType.ERROR.equals(event.getEvent().getType())) ? "ERROR" : "STATUS_UPDATE";

            return String.format("[{ \"count\" : %d, \"object\" : %s, \"event_type\": \"%s\"}]", size, json, eventType);
        } catch (IOException e) {
            return new String();
        } catch (TException e) {
            return new String();
        }
    }

    /**
     * Retrieves the last event type and time of event for a given
     * pipeline
     * @param pipelineId pipeline to check
     * @return json structure containing time and event type
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/lastEventHeaderForPipeline/{pipelineId}")
    public String getLastEventHeaderForPipeline(@PathParam("pipelineId") String pipelineId) {
        try {
            EventWithCount event = client.getLatestEventForPipeline(pipelineId, allStatuses);
            String dateTime = Long.toString(event.getEvent().getTimestamp());
            String eventType = event.getEvent().getType().toString();
            return String.format("{\"date\" : %s, \"event_type\": \"%s\"}", dateTime, eventType);
        } catch (IOException e) {
            return new String();
        }
    }

    /**
     * Retrieves meta data for each pipe in the given pipeline
     * @param pipelineId id of the pipe to use
     * @return json structure containing the meta data
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pipeDataForPipeline/{pipelineId}")
    public Set<EventWithCount> getPipeDataForPipeline(@PathParam("pipelineId") String pipelineId){
        try {
            Set<EventWithCount> eventWithCounts = client.getPipeMetaForPipeline(pipelineId);
            return eventWithCounts;
        } catch (IOException e) {
            logger.error("Error in pipeDataForPipeline", e);
            return null;
        }
    }

    /**
     * Retrieves a list of unique events along with count of how many times each
     * event occurred for a given pipeline and pipe id.
     * @param pipelineId the pipeline that contains this pipe
     * @param pipeId the pipe to retrieve the events for
     * @return list of unique events
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getEventCountPerPipe/{pipelineId}/{pipeId}")
    public PipeData getEventCountPerPipe(@PathParam("pipelineId") String pipelineId,
                                         @PathParam("pipeId") String pipeId, @QueryParam("status") List<String> status) {
        try {
            Set<ObjectStatus> statuses = getStatusSet(status);
            Set<EventWithCount> eventWithCounts = client.getEventCountPerPipe(pipelineId, pipeId, statuses);
            //Tally up the counts for header
            long sum = 0;
            for (EventWithCount event : eventWithCounts) {
                sum += event.getCount();
            }
            //Retrieve latest event for header
            QuarantineEvent latestEvent = client.getLatestEventForPipe(pipelineId, pipeId, allStatuses);
            return new PipeData(sum, latestEvent, eventWithCounts);
        } catch (IOException e) {
            logger.error("Error in getEventCountPerPipe for pipeline: " + pipelineId + " pipeId: " + pipeId, e);
            return null;
        }
    }

    /**
     * Updates the status for provided is and the status
     * @param obj an object containing the object ids and the new status
     * @return 201 if success, 500 on failure
     */
    @POST
    @Path("/updateStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatus(StatusMapper obj) {
        try {
            StringBuilder sb = new StringBuilder();

            String newStatus = obj.getStatus();
            String comment = obj.getComment();
            List<String> ids = obj.getIds();

            ObjectStatus status = ObjectStatus.valueOf(obj.getStatus());
            if (comment == null)
                comment = "";

            sb.append("\n User dn: ").append(security.fetchTokenForProxiedUser().getUserId()).append('\n');
            sb.append("New status : ").append(newStatus).append('\n');
            sb.append("Comment : ").append(comment).append('\n');
            sb.append("ids : ").append(ids).append('\n');
            logger.info(sb.toString());

            client.updateStatus(ids, status, comment);
            logger.info("SUCCESS!!!");
            return Response.status(200).entity("success").build();
        } catch (IOException | TException e) {
            logger.error("FAILURE!!!", e);
            return Response.status(500).entity(obj).build();
        }
    }

    /**
     * Updates the status for provided is and the status
     * @return 201 if success, 500 on failure
     */
    @POST
    @Path("/updateStatus/{pipelineId}/{pipeId}/{event}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatusOfEvent(@PathParam("pipelineId") String pipelineId,
                                        @PathParam("pipeId") String pipeId,
                                        @PathParam("event") String oldEvent,
                                        @QueryParam("oldStatus") String oldStatus,
                                        EventStatusMapper obj) {
        try {
            StringBuilder sb = new StringBuilder();

            String newStatus = obj.getStatus();
            String comment = obj.getComment();

            ObjectStatus status = ObjectStatus.valueOf(obj.getStatus());
            if (comment == null)
                comment = "";

            sb.append("\n User dn: ").append(security.fetchTokenForProxiedUser().getUserId()).append('\n');
            sb.append("New status : ").append(newStatus).append('\n');
            sb.append("Comment : ").append(comment).append('\n');
            logger.info(sb.toString());

            client.updateEventStatus(pipelineId, pipeId, ObjectStatus.valueOf(oldStatus), status, oldEvent, obj.getComment());
            logger.info("SUCCESS!!!");
            return Response.status(200).entity("success").build();
        } catch (IOException | TException e) {
            logger.error("FAILURE!!!", e);
            return Response.status(500).entity(obj).build();
        }
    }

    /**
     * Retrieves events associated with provided error message
     * for pipe for a given pipeline
     * @return a json structure containing the ids and time stamps of the objects
     *         that match requested event text.
     */
    @GET
    @Path("/objectIdsForEvent/{pipelineId}/{pipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public IdsResponse getObjectIdsForEventText(@PathParam("pipelineId") String pipelineId,
                                                      @PathParam("pipeId") String pipeId,
                                                      @QueryParam("eventText") String eventText,
                                                      @QueryParam("status") List<String> status,
                                                      @QueryParam("pageNumber") int pageNumber,
                                                      @QueryParam("pageSize") int pageSize) {
        try {
            //for logging
            StringBuilder sb = new StringBuilder();
            EzSecurityTokenWrapper token = security.fetchTokenForProxiedUser();
            sb.append("\n Username: ").append(token.getUsername()).append('\n');
            sb.append("User dn: ").append(token.getUserId()).append('\n');
            sb.append("Pipeline Id: ").append(pipelineId).append('\n');
            sb.append("Pipe Id: ").append(pipeId).append('\n');
            sb.append("Event Text: ").append(eventText).append('\n');
            sb.append("Statuses: ").append(status).append('\n');
            logger.info(sb.toString());
            Set<ObjectStatus> statuses = getStatusSet(status);
            IdsResponse eventDetails =
                    client.getObjectsForPipeAndEvent(pipelineId, pipeId, eventText, statuses, pageNumber, pageSize);
            return eventDetails;
        } catch (IOException e) {
            logger.error("IOException while trying to retrieve objects for pipe and event", e);
            return null;
        } catch (EzSecurityTokenException e) {
            logger.error("EzSecurityTokenException while trying to retrieve objects for pipe and event", e);
            return null;
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getErrorCount/{pipelineId}/{pipeId}")
    public long getErrorCount(@PathParam("pipelineId") String pipelineId, @PathParam("pipeId") String pipeId) {
        try {
            return client.getCountPerPipe(pipelineId, pipeId, quarantinedOnly);
        } catch (IOException e) {
            return 0L;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pipelines")
    public List<PipelineIdWrapper> getPipelinesForUser() {
        try {
            return Lists.transform(client.getPipelinesForUser(), new Function<String, PipelineIdWrapper>() {
                @Override
                public PipelineIdWrapper apply(String input) {
                    return new PipelineIdWrapper(input);
                }
            });
        } catch (IOException e) {
            logger.error("Couldn't retrieve items from quarantine", e);
            return Lists.newArrayList();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/quarantinedObject/")
    public QuarantineMetadata getObjectForId(@QueryParam("ids") String id) {
        try {
            return new QuarantineMetadata(client.getObjectForId(id));
        } catch (IOException e) {
            return null;
        } catch (ObjectNotQuarantinedException e) {
            return null;
        }
    }

    @DELETE
    @Path("/quarantinedObject/")
    public void deleteQuarantinedObjects(@QueryParam("ids") List<String> ids) {
        try {
            client.deleteObjects(ids);
        } catch (IOException e) {
            logger.error("Could not delete from Quarantine", e);
        }
    }

    @DELETE
    @Path("/quarantinedObjects/{pipelineId}/{pipeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void bulkDeleteQuarantinedObjects(@PathParam("pipelineId") String pipelineId,
                                             @PathParam("pipeId") String pipeId,
                                             @QueryParam("eventText") String eventText,
                                             @QueryParam("status") String status) {
        try {
            client.deleteObjectsByEvent(pipelineId, pipeId, ObjectStatus.valueOf(status), eventText);
        } catch (IOException e) {
            logger.error("Could not delete from Quarantine", e);
        }
    }

    @GET
    @Path("/exportData/{pipelineId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportData(@PathParam("pipelineId") String pipelineId, @QueryParam("passPhrase") String passPhrase,
                               @QueryParam("ids") List<String> ids) {

        try {
            logger.info("Pass phrase: " + passPhrase);
            ByteBuffer buffer = client.exportData(ids, passPhrase);
            final byte[] dataBuffer = buffer.array();
            final int chunkSize = 1024 * 1024;

            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                    //Returned data is smaller than chunk size return in all at once
                    if (dataBuffer.length <= chunkSize) {
                        outputStream.write(dataBuffer);
                    } else {
                        //Write chunks of 1 MB
                        int i = 0;
                        byte[] chunkBuff;
                        int numBytes = 0;

                        while(i < dataBuffer.length) {
                            //Determine buff size
                            if  ((i+chunkSize) <= dataBuffer.length) {
                                numBytes = chunkSize;
                            } else {
                                numBytes = dataBuffer.length - chunkSize;
                            }
                            chunkBuff = new byte[numBytes];
                            copyArr(chunkBuff, dataBuffer, numBytes, i);
                            outputStream.write(chunkBuff);
                            i += chunkSize;
                        }
                    }
                }

                private void copyArr(byte[] dst, byte[] src, int limit, int offset) {
                    for (int i=0; i<limit; i++) {
                        dst[i] = src[i+offset];
                    }
                }
            };
            return Response.ok(stream).header("content-disposition", "attachment; filename = " +
                    pipelineId + System.currentTimeMillis() + ".qr").build();
        } catch (IOException e) {
            logger.error("Upload failed for pipeline:  " + pipelineId, e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/downloadData")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadData(ExportParams params) {

        logger.info("\nPipeline id: " + params.getPipelineId());
        logger.info("\nPass phrase: " + params.getPassPhrase());
        logger.info("\n ids: " + params.getIds()+ "\n");
        String passPhrase = params.getPassPhrase();
        List<String> ids = params.getIds();
        String pipelineId = params.getPipelineId();

        try {
            ByteBuffer buffer = client.exportData(ids, passPhrase);
            final byte[] dataBuffer = buffer.array();
            final int chunkSize = 1024 * 1024;

            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                    //Returned data is smaller than chunk size return in all at once
                    if (dataBuffer.length <= chunkSize) {
                        outputStream.write(dataBuffer);
                    } else {
                        //Write chunks of 1 MB
                        int i = 0;
                        byte[] chunkBuff;
                        int numBytes = 0;

                        while(i < dataBuffer.length) {
                            //Determine buff size
                            if  ((i+chunkSize) <= dataBuffer.length) {
                                numBytes = chunkSize;
                            } else {
                                numBytes = dataBuffer.length - chunkSize;
                            }
                            chunkBuff = new byte[numBytes];
                            copyArr(chunkBuff, dataBuffer, numBytes, i);
                            outputStream.write(chunkBuff);
                            i += chunkSize;
                        }
                    }
                }

                private void copyArr(byte[] dst, byte[] src, int limit, int offset) {
                    for (int i=0; i<limit; i++) {
                        dst[i] = src[i+offset];
                    }
                }
            };
            return Response.ok(stream).header("content-disposition", "attachment; filename = " +
                    pipelineId + System.currentTimeMillis() + ".qr").build();
        } catch (IOException e) {
            logger.error("Upload failed for pipeline:  " + pipelineId, e);
            return Response.serverError().build();
        }
    }

    /**
     * Imports quarantine object(s) back into the system.  The
     * key provided here must match the one that was used for
     * exporting
     */
    @POST
    @Path("/importData")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream is,
                               @FormDataParam("file") FormDataContentDisposition fileDetail,
                               @FormDataParam("key") String key){
        try {
            StringBuilder sb = new StringBuilder();
            //for logging
            EzSecurityTokenWrapper token = security.fetchTokenForProxiedUser();
            sb.append("\n Username: ").append(token.getUsername()).append('\n');
            sb.append("User dn: ").append(token.getUserId()).append('\n');
            sb.append("\n FileInfo: ").append(fileDetail);

            logger.info(sb.toString());
            byte[] data = ByteStreams.toByteArray(is);
            ByteBuffer buffer = ByteBuffer.wrap(data);
            ImportResult result = client.importData(buffer, key);
            return Response.status(200).entity(result).build();
        } catch (IOException | TException e) {
            logger.error("Failed to upload file: ", e);
            return Response.status(500).entity("failure").build();
        }
    }

    private Set<ObjectStatus> getStatusSet(List<String> statuses){
        Set<ObjectStatus> statusSet = Sets.newHashSet();
        for (String status : statuses){
            logger.info("STATUS IS : " + status);
            ObjectStatus s = getStatusSet(status);
            if (s != null) {
                statusSet.add(s);
            }
        }
        return statusSet.isEmpty() ? allStatuses : statusSet;
    }

    /**
     * Returns an object status for the provided string
     * @param status String representation of status
     * @return object status
     */
    private ObjectStatus getStatusSet(String status) {
        if (status != null) {
            return ObjectStatus.valueOf(status.toUpperCase().replace(' ', '_'));
        }
        return null;
    }
}
