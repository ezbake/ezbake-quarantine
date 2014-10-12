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

'use strict';

/* Services */

var quarantineServices = angular.module('quarantineServices', ['ngResource']);

var baseUrl = 'rest/quarantine';

quarantineServices.factory('Quarantine', ['$resource',  
	function($resource){
		var property;
		var selectedEvent;
		return {
			Pipelines : 
				$resource(
					baseUrl + '/pipelines',
					{}
				),
			PipelineData :
			    $resource(
			        baseUrl + '/pipeline/:pipelineId',
			        {}
			    ),
			Pipeline : 
				$resource(
					baseUrl + '/:methodId/:pipeMetaId', 
					{
						pipeMetaId: '@pipeMetaId', 
						methodId: '@methodId'
					}
				),
			Pipe : 
				$resource(
					baseUrl + '/:methodId/:pipelineId/:pipeId', 
					{
						pipelineId: '@pipelineId',
						pipeId: '@pipeId',
						methodId: '@methodId',
						status : '@selectedStatus',
						pageNumber : '@pageNumber',
						pageSize : '@pageSize'
					}
				),
			StatusModifier :
				$resource(
					baseUrl + '/updateStatus'
				),
	        EventStatusModifier :
	            $resource(
	                baseUrl + '/updateStatus/:pipelineId/:pipeId/:event',
	                {
	                    pipelineId: '@pipelineId',
	                    pipeId: '@pipeId',
	                    oldStatus: '@selectedStatus',
	                    event: '@event',
                        status: '@newStatus',
                        comment: '@comment'
	                }
	            ),
			EventDetail :
				$resource(
					baseUrl + '/:methodId/:pipelineId/:pipeId',
					{
						methodId: '@methodId',
						pipelineId: '@pipelineId',
						pipeId: '@pipeId',
						status: '@selectedStatus',
						eventText: '@eventText'
					}
				),
			ExportData : 
				$resource(
					baseUrl + '/:methodId/:pipelineId',
					{
						methodId: '@methodId',
						pipelineId : '@pipelineId',
						ids : '@ids'
					}
				),
			QuarantinedObject :
			    $resource(
			        baseUrl + '/quarantinedObject',
			        {
			            ids : '@ids'
			        }
			    ),
			getProperty : 
				function(){
					return property;
				},
			setProperty : 
				function(value){
					property = value;
				},
			setSelectedEvent :
				function(value) {
					selectedEvent = value;
				},
			getSelectedEvent :
				function () {
					return selectedEvent;
				},
			openObjectDetailModal :
			    function (objectId, modal) {
                    var modalInstance = modal.open({
                        templateUrl: 'partials/object-detail-modal.html',
                        controller: 'ObjectDetailModalCtrl',
                        resolve:{
                          data : function () {
                            return objectId;
                          }
                        }
                      });
                      return modalInstance;
			    },
			parseTimeStamp : 
				function(timestamp){
					var result = timestamp.date.month + '/'
						+ timestamp.date.day + '/'
						+ timestamp.date.year + ' '
						+ timestamp.time.hour + ':'
						+ timestamp.time.minute + ':'
						+ timestamp.time.second;
					return result;
				}
		};
	}]);
