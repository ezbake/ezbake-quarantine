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
  .controller('PipesCtrl', ['$scope', 'Quarantine', '$routeParams',
    function($scope, Quarantine, $routeParams) {
      //Set the pipeline id
      $scope.pipelineId = $routeParams.pipelineId;
      // Retrieve the meta data for selected pipeline
      $scope.pipelineMeta = Quarantine.getProperty();
      // Retrieve list of pipes for the given pipeline
      $scope.refresh = function (force) {
        $scope.loading = true;
        $scope.pipedata = Quarantine.Pipeline.query(
        {
          pipeMetaId: $routeParams.pipelineId,
          methodId: 'pipeDataForPipeline'
        }, function(data) {
          $scope.loading = false;
          retrievePipelineMeta(force);
        });
      }

      var retrievePipelineMeta = function(force){
        //If no meta data is found, then retrieve it from
        //the server
        if(force || ($scope.pipelineMeta == null && $scope.pipelineId != null)){
          Quarantine.Pipeline.get(
              {
                pipeMetaId : $scope.pipelineId,
                methodId : 'lastEventHeaderForPipeline'
              },
              //Call back function
              function(data){
                $scope.pipelineMeta = data;
            }
          )
        }
      }
  }]);