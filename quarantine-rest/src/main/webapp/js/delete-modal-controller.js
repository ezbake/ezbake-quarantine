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

quarantineAppControllers
  .controller('DeleteModalCtrl', ['$scope', '$modalInstance', 'data', 'Quarantine', '$q',
      function($scope, $modalInstance, data, Quarantine, $q){
        $scope.data = data;

        $scope.triggerDelete = function() {
          $scope.inProgress = true;
          if ($scope.data.isEventDetail) {
              Quarantine.QuarantinedObject.delete({
                ids : $scope.data.ids
              },
              function() {
                $modalInstance.close('success');
              },
              function(reason) {
                window.alert("Could not delete: " + reason.statusText);
                $modalInstance.dismiss('failed');
              });
            } else {
              var promises = [];
              for (var i = 0; i < $scope.data.selectedEvents.length; i++) {
                var event = $scope.data.selectedEvents[i];
                promises.push(
                  Quarantine.EventDetail.delete({
                    methodId : 'quarantinedObjects',
                    pipelineId : $scope.data.pipelineName,
                    pipeId : $scope.data.pipeName,
                    status : event.status,
                    eventText : event.event.event
                  }).$promise);
              }
              $q.all(promises).then(function(result) {
                $modalInstance.close('success');
              }, function(reason) {
                window.alert("Could not delete: " + reason.statusText);
                $modalInstance.dismiss('failed');
              });
            }
        }

        $scope.cancel = function() {
          $modalInstance.dismiss('cancel');
        }
      }
    ]);