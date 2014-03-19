'use strict';

angular.module('microscope.app.filters', [])
	.filter('getDateTime', function () {
	    return function (date) {
	        var dateObj = new Date(date / 1000);
	        return dateObj.format('yyyy-MM-dd hh:mm:ss');
	    }
	});