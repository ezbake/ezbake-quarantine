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

import ezbake.quarantine.thrift.QuarantineEvent;
import ezbake.quarantine.thrift.QuarantineResult;

import java.util.List;

/**
 * Created by nkhan on 4/30/14.
 */
public class QuarantineMetadata {

   private QuarantineResult result;

    public QuarantineMetadata(QuarantineResult result) {
        this.result = result;
    }

    public String getId() {
        return result.getId();
    }

    public String getObjectStatus() {
        return result.getStatus().toString();
    }

    public String getPipelineId(){
        return result.getObject().getPipelineId();
    }

    public String getPipeId(){
        return result.getObject().getPipeId();
    }

    public boolean isSerializable(){
        return result.getObject().isSerializable();
    }

    public String getFormalVisibility(){
        return result.getObject().getVisibility().getFormalVisibility();
    }

    public List<QuarantineEvent> getEvents(){
        return result.getEvents();
    }
}
