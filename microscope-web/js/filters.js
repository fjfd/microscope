'use strict';

angular.module('zipkin.filters',[])
	.filter('getTime',function(){
		return function(date){
			var dateObj = new Date(date/1000),
				year = dateObj.getFullYear(),
				month = dateObj.getMonth(),
				date = dateObj.getDate(),
				hour = dateObj.getHours(),
				minute = dateObj.getMinutes();
			var time = month+'/'+date+'/'+year+'\n'+hour+':'+minute;
			return time;
		}
	})