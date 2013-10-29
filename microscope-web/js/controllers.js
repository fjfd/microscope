'use strict';

var ms = {};
ms.config = {
	hostname :'microscope.vipshop.com',
	port: '80'
	// hostname :'10.100.90.183',
	// port: '8080'
	// hostname :'192.168.52.145',
	// port: '8080'
};


angular.module('zipkin.controllers', ['reportModule']).
	controller('homeCtrl',function($scope, $location){
		$scope.path = $location.path();

	}).
	controller('traceCtrl', function ($rootScope, $scope, $route, $http, $location) {
		/**
		 * 查询条件
		 */
		$.ajax({
			url: 'http://'+ ms.config.hostname + ':' + ms.config.port + '/trace/queryCondition?callback=?',
			dataType: 'jsonp',
			global: false,
			success: function (data) {
				var queryList = data.appAndTrace;
				$scope.appList = [];
				$scope.queryApp = "";
				$scope.traceLists = [];
				$scope.traceList = [];
				$scope.queryTrace = "";

				//排序列表
				queryList.sort(function (a, b) {
					if (a.app.toLowerCase() > b.app.toLowerCase()) {
						return 1;
					}
				})

				_.each(queryList, function (v) {
					$scope.appList.push(v.app);
					$scope.traceLists.push(v.trace);
				})

//				进入页面后默认第一个app的第一个trace为查询项
				$scope.queryApp = $scope.appList[0];

				//下拉框联动
				var index;
				$scope.syncSelect = function () {
					index = $scope.appList.indexOf($scope.queryApp);
					$scope.traceList = $scope.traceLists[index];
				}()
				$scope.queryTrace = $scope.traceList[0];

				$rootScope.$apply();
			}

		});

		/**
		 * 日期和时间控件
		 * date:http://www.eyecon.ro/bootstrap-datepicker/
		 * time:http://jdewit.github.io/bootstrap-timepicker/
		 */

		var today = new Date(),
			date = today.getDate(),
			month = today.getMonth() + 1,
			year = today.getFullYear();

		/**
		 * data
		 */
		$scope.queryStartDate = month + '/' + date + '/' + year;
		$scope.queryEndDate = month + '/' + ( date + 1 ) + '/' + year;

		var _startDate = $('.query-start-date').datepicker({
			onRender: function (date) {
				var todayValue = new Date(today).valueOf();
				return date.valueOf() > todayValue ? 'disabled' : '';
			}
		}).on('changeDate',function () {
//				$('.datepicker').hide();
				_startDate.hide();
				$scope.queryStartDate = $('.query-start-date')[0].value;
				$('.query-start-time').focus();
			}).data('datepicker');

		var _endDate = $('.query-end-date').datepicker({
			onRender: function (date) {
				return date.valueOf() < _startDate.date.valueOf() ? 'disabled' : '';
			}
		}).on('changeDate',function (ev) {
//			$('.datepicker').hide();
				_endDate.hide();
				$scope.queryEndDate = $('.query-end-date')[0].value;
				$('.query-end-time').focus();
			}).data('datepicker');

		/**
		 * time
		 */
		$('.query-start-time').timepicker({
			minuteStep: 5,
			showSeconds: true,
			showInputs: false,
			showMeridian: false,
		}).on('show.timepicker', function (e) {
				$scope.queryStartTime = e.time.value;
			})
		
		$('.query-end-time').timepicker({
			minuteStep: 5,
			showSeconds: true,
			showInputs: false,
			showMeridian: false,
		}).on('changeTime.timepicker', function (e) {
				$scope.queryEndTime = e.time.value;

			})
		$scope.queryStartTime = $('.query-start-time')[0].value;
		$scope.queryEndTime = $('.query-end-time')[0].value;
		$scope.limitCount = 100;

		/**
		 * 查询结果列表
		 */
			//如果存在前次查询，将前次查询结果赋值给resultList;
		$scope.resultList = $rootScope.previousReultList ? $rootScope.previousReultList : {};
		$scope.reqCondition = function () {
			$.ajax({
				//url: 'http://10.101.0.216:8080/trace/traceList?' +
				url: 'http://'+ ms.config.hostname + ':' + ms.config.port + '/trace/traceList?' +
					'traceName=' + $scope.queryTrace +
					'&startTime=' + new Date($scope.queryStartDate + ' ' + $scope.queryStartTime).valueOf() +
					'&endTime=' + new Date($scope.queryEndDate + ' ' + $scope.queryEndTime).valueOf() +
					'&limit=' + $scope.limitCount +
					'&callback=?',
				dataType: 'jsonp',
				global: false,
				success: function (data) {
					$rootScope.previousReultList = $scope.resultList = data.traceList;
//					$rootScope.previousReultList = $scope.resultList;
					var duration = [];

					//排序
					$scope.predicate = 'durationMicro';

					var getDate = function (data) {
						var dateObj = data;
						return dateObj;
					}

					_.each(data, function (v) {
						duration.push(v.durationMicro);
					})
					$rootScope.$apply();
				}
			})
		}
		$scope.toShow = function (traceId) {
			$location.path('/show/' + traceId);
		}
	}).
	controller('showCtrl', function ($scope, $route, $routeParams, $rootScope) {

	    $(".overview_tree").css("display","block");

	    $("#timeline").click(function() {
	    	$(".overview_tree").css("display","none");
	        $(".dep_tree").css("display","none");
	        $(".timeline_chart").css("display","block");
	    });

	    $("#overview").click(function() {
	    	$(".dep_tree").css("display","none");
	        $(".timeline_chart").css("display","none");
	    	$(".overview_tree").css("display","block");
	    });

	    $("#dependencies").click(function() {
	        $(".timeline_chart").css("display","none");
	    	$(".overview_tree").css("display","none");
	        $(".dep_tree").css("display","block");
	    });



		$.ajax({
			//url: 'http://10.101.0.216:8080/trace/traceSpan?' +
			url: 'http://'+ ms.config.hostname + ':' + ms.config.port + '/trace/traceSpan?' +	
				'traceId=' + $routeParams.traceId +
				'&callback=?',
			dataType: 'jsonp',
			global: false,
			success: function (data) {

				var _trace = data;
				var $nodes = $('.gantt-nodes'),
					$firstNode = $nodes.children('li:first'),
					ganttOption = {};

				var _spanArr = [];

				
			//trace fn
				$.each(_trace.traceSpan.spans, function (i) {
					var _node = _trace.traceSpan.spans[i];
					if (!_node.parentId) {
						_spanArr.push(_node);
						$firstNode.append("<span class='trace_round'></span><a href='javascript:;' id=t_" + i + ">" + _node.name + "</a>").attr('data-id', _node.id);
						ganttOption.duration = _node.duration / 1000;
					} else {
						var _id = _node.parentId,
							_parent = $firstNode.find('li[data-id=' + _id + ']').length ? $firstNode.find('li[data-id=' + _id + ']') : $firstNode;

						if (_parent.length) {
							
							if(_parent.children('ul').length) {
								
								_parent.children('ul').append('<li data-id=' + _node.id + '><span class="trace_round"></span><a href="javascript:;" id=t_' + i + '>' + _node.name + '</a></li>');
							} else {
								_parent.append('<ul data-id='+ _node.parentId +'><li data-id=' + _node.id + '><a href="javascript:;" id=t_' + i + '>' + _node.name + '</a></li></ul>');
							}
						}	
						_spanArr.splice($nodes.find('li').index($('li[data-id=' + _node.id + ']')), 0, _node);
					}


					//树形菜单hover操作
					$("#t_"+ i).hover(function() {
						$(".gantt-nodes a").css("text-decoration", "none");
						$(this).addClass("gantt-bar_1");
						$(".gantt-chart li:eq(" + i + ")").addClass("gantt-bar_1");
					}, function() {
						$(".gantt-nodes a").removeClass("gantt-bar_1");
						$(".gantt-chart li:eq(" + i + ")").removeClass("gantt-bar_1");
					});
				});

				$("#browser").treeview();

				ganttOption.dLen = getGanttScale(ganttOption.duration);
				ganttOption.sub = getSub(ganttOption.duration);
				ganttOption.ganttLen = getLen(ganttOption.sub, ganttOption.dLen);
				ganttOption.ganttTotalLen = getLen(ganttOption.sub, ganttOption.dLen, 'total');
				ganttOption.arr = getGanttArr(ganttOption.sub, ganttOption.ganttLen);
				$scope.ganntOption = ganttOption;



				var $style = $("<style>.gantt-scale .item{ width:" + 100 / ganttOption.arr.length + "%; } .gantt-bar-main{ left:0; width:" + (ganttOption.duration / ganttOption.ganttTotalLen * 100) + "%;}</style>");
				$style.appendTo("body");


				/*		$('.ganttt-scale .item .flag').left(
				 $(this).width()/2
				 );*/


				var bigSpan = _spanArr[0];
				for (var i in _spanArr) {
					_spanArr[i].style = {
						width: _spanArr[i].duration / 1000 / ganttOption.ganttTotalLen * 100 + '%',
						left: (_spanArr[i].startTimestamp - bigSpan.startTimestamp) / 1000 / ganttOption.ganttTotalLen * 100 + '%'
					}
				}

				$scope.spans = _spanArr;
				$rootScope.$apply();

//number for option
				function getGanttScale(d) {
					return parseInt(d).toString().length;

				}

				function getSub(d) {
					if (d < 10 && d > 1) {
						return parseInt(d);
					} else if (d < 1) {
						return 1;
					} else {
						d = d / 10;
						return getSub(d);
					}
					;
				}

				function getLen(d, f, type) {
					return type ? (d + 1) * Math.pow(10, f - 1) : d * Math.pow(10, f - 1);
				}

				function getGanttArr(s, l) {
					var _arr = [];
					for (var i = 0; i <= s; i++) {
						_arr[i] = i * l / s;
					}
					return _arr;
				}

				//表格
                var jsonObj = {};
		        jsonObj.Rows = [
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },		            		            		            
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },		            		            		            
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },		            		            		            
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" },
		            { time: "1989/01/12", service: "SERVICE-find", name: "张三", annotation: "注释",host:"192.168.0.1" }		            		            		            		            		            		            		            
		        ];
		        var columns =
		            [
		                { display: 'Time', name: 'time', type: 'date', mintWidth: 40, width: 100 },
		                { display: 'Service', name: 'service' },
		                { display: 'Span name', name: 'name' },
		                { display: 'Annotation', name: 'annotation', type: 'string' },
		                { display: 'Host', name: 'host', type: 'string' }
		             ];
		        $(".timeline_chart").ligerGrid({
		            columns: columns,
		            data: jsonObj
		        });

				//依赖图
		       	var width = 960,
	        	height = 2200;

			    var cluster = d3.layout.cluster()
			        .size([height, width - 160]);

			    var diagonal = d3.svg.diagonal()
			        .projection(function(d) { return [d.y, d.x]; });

			    var svg = d3.select(".dep_tree").append("svg")
			        .attr("width", width)
			        .attr("height", height)
			      .append("g")
			        .attr("transform", "translate(40,0)");

			    d3.json("flare.json", function(error, root) {
			      var nodes = cluster.nodes(root),
			          links = cluster.links(nodes);

			      var link = svg.selectAll(".link")
			          .data(links)
			        .enter().append("path")
			          .attr("class", "link")
			          .attr("d", diagonal);

			      var node = svg.selectAll(".node")
			          .data(nodes)
			        .enter().append("g")
			          .attr("class", "node")
			          .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })

			      node.append("circle")
			          .attr("r", 4.5);

			      node.append("text")
			          .attr("dx", function(d) { return d.children ? -8 : 8; })
			          .attr("dy", 3)
			          .style("text-anchor", function(d) { return d.children ? "end" : "start"; })
			          .text(function(d) { return d.name; });
			    });

			    d3.select(self.frameElement).style("height", height + "px");
			}
		});
	})




