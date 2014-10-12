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

// Intercepting HTTP calls with AngularJS.
angular.module('quarantine.interceptor', [])
.config(function ($provide, $httpProvider) {

  // Intercept http calls.
  $provide.factory('interceptor', function ($q) {
    return {
      // On response failture
      responseError: function (rejection) {
        if (rejection.status == 403) {
          window.location = "./error.html";
          return;
        }
        return $q.reject(rejection);
      }
    };
  });

  // Add the interceptor to the $httpProvider.
  $httpProvider.interceptors.push('interceptor');
});