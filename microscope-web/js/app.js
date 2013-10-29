'use strict';

var labjs = $LAB
	.script('lib/jquery-1.10.2.min.js').wait()
	.script('lib/angular.min.js')
	.script('lib/bootstrap.min.js')
	.script('lib/datepicker.js')
	.script('lib/timepicker.js')
	.script('lib/d3.v3.js')
	.script('js/services.js')
	.script('js/controllers.js')
	.script('js/reportModule.js')
	.script('js/filters.js')
	.script('js/directives.js')
	.script('lib/highcharts.js')
	.script('lib/highcharts-more.js')
	.script('lib/exporting.js')
	.script('lib/serial.js')
	.script('js/ligerui.all.js')
	.script('js/jquery.treeview.js');

labjs.wait(function(){
	angular.module('zipkin', ['zipkin.controllers','zipkin.filters']).
		config(['$routeProvider',function($routeProvider){
			$routeProvider.
				when('/trace', {templateUrl: 'partials/trace.html', controller: 'traceCtrl'}).
				when('/report', {templateUrl: 'partials/report.html', controller: 'reportCtrl'}).
				when('/show/:traceId', {templateUrl: 'partials/show.html', controller: 'showCtrl'}).
				otherwise({redirectTo: '/trace'})
		}]);
	angular.element(document).ready(function() {
		angular.bootstrap(document, ['zipkin']);
	});
});

