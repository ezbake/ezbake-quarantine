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
    .controller('PipeDetailCtrl', ['$scope', 'Quarantine', '$routeParams', '$modal', '$window', '$q', '$http',
    function($scope, Quarantine, $routeParams, $modal, $window, $q, $http) {
        //Toggle between even detail and pipe detail
        var isEventDetail = false;
        //Extract pipe data
        $scope.pipelineName = $routeParams.pipelineId;
        $scope.pipeName = $routeParams.pipeId;
        // Status check boxes
        $scope.isRowSelected = [];
        $scope.statuses = {"Approved For Reingest" : true, "Quarantined" : true,
        "Archived" : true, "Cannot Be Reingested" : true, "Reingested" : true,
        "Prepared To Reingest" : true};

        //Check box filter functionality
        $scope.update = function (isDetail) {
          var selectedStatuses = [];
          for (var key in $scope.statuses){
            if($scope.statuses[key]){
              selectedStatuses.push(key);
            }
          }
          if (isDetail){
            $scope.selEventText = Quarantine.getSelectedEvent();
            //If we do not have a selected event, then we just
            //navigate the user back to the previous page
            if ($scope.selEventText == null) {
              $window.history.back();
            } else {
              isEventDetail = true;
              $scope.refreshDetail(selectedStatuses);
            }
          } else {
            isEventDetail = false;
            $scope.refresh(selectedStatuses);
          }
        }

        // Export functionality
        $scope.triggerDownload = function () {
          var modalData = {};
            var ids = [];
            if (isEventDetail) {
              ids = getSelectedIds();
              executeDownloadModal(ids.length);
            } else {
              var selectedEvents = getSelectedEvents();
              var numberOfIds = 0;
              for (var i = 0; i < selectedEvents.length; i++) {
                numberOfIds += selectedEvents[i].count;
              }
              executeDownloadModal(numberOfIds);
            }
        }

        var executeDownloadModal = function(numberOfIds) {
            //Only process if the user has actually selected row(s)
            if (numberOfIds > 0) {
              //Populate modal data
              var modalData = {};
              modalData["title"] = 'Enter pass phrase';
              modalData["objCount"] = numberOfIds;
              modalData["labelOne"] = 'Description';
              modalData["valOne"] = 'Your passphrase must be at least 14 characters including 1 '
              +'of each: upper and lower case letter(s), number(s) and special character(s)';
              modalData["labelTwo"] = 'Pass phrase';
              modalData["rowCount"] = 0;
              var modalInstance = open(modalData);
              //Get comment text from modal
                modalInstance.result.then(function (key) {
                    var selIds = [];
                    if (isEventDetail) {
                      selIds = getSelectedIds();
                      $scope.downloadUrl = generateExportUrl(selIds, key);
                      //Using as a back up
                      $window.location.href = $scope.downloadUrl;
                    } else {
                      var promises = [];
                      var selectedEvents = getSelectedEvents();
                      for (var i = 0; i < selectedEvents.length; i++) {
                        var event = selectedEvents[i];
                        promises.push(
                          Quarantine.EventDetail.query({
                            methodId : 'objectIdsForEvent',
                            pipelineId : $scope.pipelineName,
                            pipeId : $scope.pipeName,
                            status : event.status,
                            eventText : event.event.event
                          }).$promise);
                      }
                      $q.all(promises).then(function(result) {
                        for (var i = 0; i < result.length; i++) {
                          for (var j = 0; j < result[i].length; j++) {
                            selIds.push(result[i][j].id);
                          }
                        }
                        if (selIds.length > 0) {
                            $scope.downloadUrl = generateExportUrl(selIds, key);
                            //Using as a back up
                            $window.location.href = $scope.downloadUrl;
                        } else {
                            alert("Could not retrieve IDs for the given events. Please contact a system administrator.");
                        }
                      }, function(reason) {
                        window.alert("Could not update: " + reason);
                      });
                    }
                });
            } else {
              alert("Please select at least one row");
            }
        }

        // Delete functionality
        $scope.triggerDelete = function () {
          var modalData = {
            isEventDetail: isEventDetail,
            pipelineName: $scope.pipelineName,
            pipeName: $scope.pipeName
          };
          if (isEventDetail) {
            modalData["ids"] = getSelectedIds();
          } else {
            modalData["selectedEvents"] = getSelectedEvents();
          }
          openDeleteConfirmModal(modalData);
        }

        //Error message detail modal
        var openDeleteConfirmModal = function (modalData) {
          var modalInstance = $modal.open({
            templateUrl: 'partials/delete-confirm-modal.html',
            controller: 'DeleteModalCtrl',
            resolve:{
              data : function () {
                return modalData;
              }
            }
          });
          modalInstance.result.then(function(){
            $scope.uncheckMaster();
            if(isEventDetail) {
              $scope.refreshDetail();
            } else {
              $scope.refresh();
            }
          });
          return modalInstance;
        }

        // Generates urls for export data using the array
        // of ids provided as a query param
        var generateExportUrl = function(queryParam, passPhrase) {
          var basUrl = '/quarantine/rest/quarantine/exportData/' + $routeParams.pipelineId;
          var regEx = new RegExp(',', 'g');
          var key = 'ids=';
          var result = basUrl +'?passPhrase=' + encodeURIComponent(passPhrase) + '&' + key;
          var val = String(queryParam).replace(regEx, '&' + key);
          result += val;

          return result;
        }

        //Reloads the original data for the specified status
        $scope.refresh = function (selectedStatus) {
          $scope.loading = true;
          $scope.pipeDetailData = {};
          $scope.pipeDetailData = Quarantine.Pipe.get(
            {
              pipelineId : $routeParams.pipelineId,
              pipeId : $routeParams.pipeId,
              methodId : 'getEventCountPerPipe',
              status : selectedStatus
            },
            function (data) {
              $scope.isRowSelected = [];
              if (data != null && data.eventsWithCounts != null){
                var length = data.eventsWithCounts.length;
                for (var i = 0; i < length; i++) {
                  $scope.isRowSelected[i] = false;
                }
              }
              $scope.loading = false;
            }
          );
        }

        //Reload event detail for the specified status(es)
        $scope.refreshDetail = function (selectedStatus) {
          $scope.loading = true;
          $scope.page = $scope.page ? $scope.page : 0;
          $scope.eventData = {};
          $scope.eventData = Quarantine.EventDetail.get(
            {
              methodId : 'objectIdsForEvent',
              pipelineId : $scope.pipelineName,
              pipeId : $scope.pipeName,
              status : selectedStatus,
              eventText : $scope.selEventText,
              pageNumber : $scope.page,
              pageSize : 30 // TODO : Change this from being hard coded in future release
            },
            function (data) {
              $scope.isRowSelected = [];
              var length = data.ids.length;
              if(length > 0) {
                for (var i = 0; i < length; i++) {
                  $scope.isRowSelected[i] = false;
                }
              }
              $scope.loading = false;
            }
          );
        }

        // increment the page we are on
        $scope.incrementPage = function () {
            $scope.page = $scope.page + 1;
            $scope.refreshDetail();
        }

        // increment the page we are on
        $scope.decrementPage = function () {
            $scope.page = $scope.page - 1;
            $scope.refreshDetail();
        }

        //Checkbox functionality
        $scope.isChecked = false;
        $scope.checkAll = function (isChecked) {
          //Go through and check/uncheck each row
          for (var i = 0; i < $scope.isRowSelected.length; i++) {
            $scope.isRowSelected[i] = isChecked;
          }
        }
        //Uncheck master view
        $scope.uncheckMaster = function () {
          $scope.isChecked = false;
        }

        var getSelectedIds = function () {
          var ids = [];
          var objCount = 0;
           //Populate an array of ids for the selected objects
          for (var i=0; i<$scope.isRowSelected.length; i++) {
            if ($scope.isRowSelected[i]) {
              if (isEventDetail) {
                ids[objCount] = $scope.eventData.ids[i].id;
              } else {
                ids[objCount] = $scope.pipeDetailData.eventsWithCounts[i].event.id;
              }
              objCount++;
            }
          }
          return ids;
        }

        var getSelectedEvents = function () {
          var events = [];
           //Populate an array of ids for the selected objects
          for (var i=0; i<$scope.isRowSelected.length; i++) {
            if ($scope.isRowSelected[i]) {
              if (isEventDetail) {
                events.push($scope.eventData.ids[i]);
              } else {
                events.push($scope.pipeDetailData.eventsWithCounts[i]);
              }
            }
          }
          return events;
        }

        $scope.updateStatus = function (newStatus) {
          var modalData = {};
          if (isEventDetail) {
            modalData["ids"] = getSelectedIds();
            executeUpdateModal(modalData["ids"].length, modalData, newStatus);
          } else {
            modalData["selectedEvents"] = getSelectedEvents();
            var numberOfIds = 0;
            for (var i = 0; i < modalData["selectedEvents"].length; i++) {
              numberOfIds += modalData["selectedEvents"][i].count;
            }
            executeUpdateModal(numberOfIds, modalData, newStatus);
          }
        }

        var executeUpdateModal = function(numberOfIds, modalData, newStatus) {
            //Only process if the user has actually selected row(s)
            if (numberOfIds > 0) {
              //Populate data for the modal
              modalData["title"] = 'Enter Update Comment';
              modalData["objCount"] = numberOfIds;
              modalData["labelOne"] = 'Selected Action';
              modalData["valOne"] = (newStatus == 'a') ? "Approve For Reingest" : "Archive";
              modalData["labelTwo"] = 'Comment';
              modalData["rowCount"] = 8;
              modalData["status"] = (newStatus == 'a') ? "APPROVED_FOR_REINGEST" : "ARCHIVED";
              modalData["pipelineName"] = $scope.pipelineName;
              modalData["pipeName"] = $scope.pipeName;
              modalData["isEventDetail"] = isEventDetail;
              //Open modal for comment
              var modalInstance  = open(modalData);
              //Get comment text from modal
              modalInstance.result.then(function (result) {
                if (isEventDetail) {
                  $scope.refreshDetail();
                } else {
                  $scope.refresh();
                }
              });
            } else {
              alert("Please select at least one row");
            }
        }

        //Trigger for opening error detail modal
        $scope.showErrorDetail = function(errorMsg) {
          openErrorDetailModal(errorMsg);
        }

        // Modal functionality
        var open = function(modalData) {
          var modalInstance = $modal.open({
            templateUrl: 'partials/modal.html', //html defining modal
            controller: 'PipeModalCtrl',        //modal controller
            backdrop: 'static',
            resolve : {
              data : function (){
                return modalData; //data that's passed to the modal
              }
            }
          });
          return modalInstance;
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

        $scope.setEvent = function(index) {
          Quarantine.setSelectedEvent($scope.pipeDetailData.eventsWithCounts[index].event.event);
        }
      }
  ]);