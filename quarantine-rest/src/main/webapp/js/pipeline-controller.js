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
  .controller('PipelinesCtrl', ['$scope', 'Quarantine', '$q',
    function($scope, Quarantine, $q) {
      // Used to send data to the Pipes page
      var selectedPipeline = {};

      $scope.refresh = function(){
        $scope.loading = true;
        $scope.pipelines = [];
        Quarantine.Pipelines.query({},
         function(data) {
              var promises = [];
              for (var i = 0; i < data.length; i++) {
                promises.push(
                  Quarantine.PipelineData.query({
                    pipelineId : data[i].pipelineId
                  }).$promise);
              }
              $q.all(promises).then(function(result) {
                var pipelineResults = [];
                for (var i = 0; i < result.length; i++) {
                  if (result[i] && result[i].length > 0) {
                    pipelineResults.push(result[i][0]);
                  }
                }
                $scope.pipelines = pipelineResults;
                $scope.loading = false;
              }, function(reason) {
                window.alert("Could not retrieve pipelines: " + reason.statusText);
                $scope.loading = false;
              });
         });
      }
      // Set selected pipeline
      $scope.setSelectedPipeline = function(index){
        selectedPipeline["date"] = $scope.pipelines[index].object.timestamp;
        selectedPipeline["event_type"] = $scope.pipelines[index].event_type;
        Quarantine.setProperty(selectedPipeline);
      }
  }]);