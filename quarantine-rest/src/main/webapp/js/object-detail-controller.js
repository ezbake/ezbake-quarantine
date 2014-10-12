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
  .controller('ObjectDetailCtrl', ['$scope', 'Quarantine', '$routeParams', '$modal',
    function($scope, Quarantine, $routeParams, $modal){
      $scope.pipelineName = $routeParams.pipelineId;
      $scope.pipeName = $routeParams.pipeId;
      $scope.objectId = $routeParams.id;

      //Check box filter functionality
      $scope.update = function () {
        $scope.loading = true;
        Quarantine.QuarantinedObject.get({
                ids: $routeParams.id
              },
              function (result) {
                $scope.objectDetails = result;
                $scope.loading = false;
              }
          );
      }

      //Trigger for opening error detail modal
      $scope.showErrorDetail = function(errorMsg) {
        openErrorDetailModal(errorMsg);
      }

      //Error message detail modal
      var openErrorDetailModal = function (modalData) {
        var modalInstance = $modal.open({
          templateUrl: 'partials/error-detail-modal.html',
          controller: 'DetailModalCtrl',
          resolve:{
            data : function () {
              return modalData;
            }
          }
        });
        return modalInstance;
      }

      //Trigger for opening error detail modal
      $scope.showAdditionalMetadata = function(additionalMetadata) {
        openAdditionalMetadataModal(additionalMetadata);
      }

      //Error message detail modal
      var openAdditionalMetadataModal = function (additionalMetadata) {
        var modalInstance = $modal.open({
          templateUrl: 'partials/additional-metadata-modal.html',
          controller: 'DetailModalCtrl',
          windowClass: 'wide-modal',
          resolve:{
            data : function () {
              return additionalMetadata;
            }
          }
        });
        return modalInstance;
      }
  }]);