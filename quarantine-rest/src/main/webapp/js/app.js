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
// Declare app level module which depends on filters, and services
var quarantineAppControllers = angular.module('quarantineApp.controllers', []);

var quarantineApp = angular.module('quarantineApp', [
		'ngRoute',
		'quarantineApp.controllers',
		'quarantineServices',
		'ui.bootstrap',
		'angularFileUpload',
		'quarantine.interceptor'
	]);

quarantineApp.config(['$routeProvider',
	function($routeProvider){
		$routeProvider.when('/pipelines', 
			{
				templateUrl: 'partials/pipelines.html', 
				controller: 'PipelinesCtrl'
			});
		$routeProvider.when('/pipelines/:pipelineId', 
			{
				templateUrl: 'partials/pipes.html', 
				controller: 'PipesCtrl'
			});
		$routeProvider.when('/pipelines/:pipelineId/:pipeId', 
			{
				templateUrl: 'partials/pipe-detail.html',
				controller: 'PipeDetailCtrl'
			});
		$routeProvider.when('/pipelines/:pipelineId/:pipeId/event-detail', 
			{
				templateUrl: 'partials/error-detail.html',
				controller: 'PipeDetailCtrl'
			});
        $routeProvider.when('/pipelines/:pipelineId/:pipeId/:id/object-detail',
            {
                templateUrl: 'partials/object-detail.html',
                controller: 'ObjectDetailCtrl'
            });
		$routeProvider.otherwise(
			{
				redirectTo: '/pipelines'
		});
}]);