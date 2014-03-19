'use strict';

angular.module('microscope.app.exception', ['ui.validate', 'microscope.app.filters'])

.controller('exceptionCtrl', ['$rootScope', '$scope', '$http', 'serverConfig',
	function($rootScope, $scope, $http, serverConfig) {

		// 页面进入时首次请求
		$http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/trace/excepCondition?callback=JSON_CALLBACK')
			.success(function(data) {
				var queryList = data.result,
					appList = [];

				if (queryList && queryList.length > 0) {
					$scope.appObjList = queryList;
					for (var i = 0; i < $scope.appObjList.length; i++) {
						appList.push($scope.appObjList[i].app);
					}
				}
				$scope.appList = appList;
				$scope.queryApp = $scope.appList[0];
				$scope.bindIP();
			});

		$scope.bindIP = function() {
			for (var i = 0; i < $scope.appObjList.length; i++) {
				if ($scope.appObjList[i].app === $scope.queryApp) {
					$scope.ipList = $scope.appObjList[i].ip;
					$scope.queryIP = $scope.ipList[0];
					$scope.query();
				}
			}
		};

		// 设置查询条件
		var today = new Date(),
			weekAgo = new Date(today.getTime() - 1 * 1 * 60 * 60 * 1000);

		$scope.queryStartDate = weekAgo.format('yyyy-MM-dd');
		$scope.queryEndDate = today.format('yyyy-MM-dd');
		$scope.dateOptions = {
			format: 'yyyy-mm-dd',
			autoclose: true,
			endDate: today,
			todayHighlight: true

		};

		$scope.queryStartTime = weekAgo.format('hh:mm:ss');
		$scope.queryEndTime = today.format('hh:mm:ss');
		$scope.timeOptions = {
			minuteStep: 1,
			secondStep: 1,
			showSeconds: true,
			showInputs: false,
			showMeridian: false
		};

		$scope.limitCount = 20;
		$scope.querying = false;

		$scope.query = function() {
			if ($scope.querying) {
				return;
			};
			$scope.resultList = null;
			// 查询
			$scope.querying = true;
			$('.side-loading').show();
			// 转换时间为毫秒数
			var startDateTime = new Date(($scope.queryStartDate + ' ' + $scope.queryStartTime).replace(/-/g, '\/')).valueOf();
			var endDateTime = new Date(($scope.queryEndDate + ' ' + $scope.queryEndTime).replace(/-/g, '\/')).valueOf();
			$http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/trace/excepList?' +
				'appName=' + $scope.queryApp +
				'&ipAddress=' + $scope.queryIP +
				'&startTime=' + startDateTime +
				'&endTime=' + endDateTime +
				'&limit=' + $scope.limitCount +
				'&callback=JSON_CALLBACK')
				.success(function(data) {
					for (var i = 0, l = data.result.length; i < l; i++) {
						data.result[i].Date = parseInt(data.result[i].Date);

						data.result[i].StackList =[];

						if (data.result[i].Stack) {
							var stack = data.result[i].Stack;
							var stackArr = stack.split(/[\r\n\t]+/);
							for (var j = 0; j < stackArr.length; j++) {
								if (j === 0) {
									data.result[i].StackList.push(stackArr[j]);
								} else {
									data.result[i].StackList.push(stackArr[j]);
								}
							}
							console.log(stackArr);
						}
					}
					$scope.resultList = data.result;
					$scope.querying = false;
					$('.side-loading').hide();
				}).error(function() {
					$scope.querying = false;
					$('.side-loading').hide();
				});
		};

		$scope.showDetail = function(traceId) {
            // 显示详情
            var tr = $('#detail' + traceId);
            if (tr.children('td').children().length > 0) {
                if (!tr.is(':visible')) {
                    tr.slideDown();
                } else {
                    tr.hide();
                }
                return;
            }

            $.ajax({
                url: 'http://' + serverConfig.hostname + ':' + serverConfig.port + '/trace/traceSpan?' + 'traceId=' + traceId + '&callback=?',
                dataType: 'jsonp',
                global: false,
                success: function(data) {
                    var spans = data.result.spans,
                        ganttItem = [];

                    var sortTree = function(spans) {

                        var result = [];
                        // 获取根
                        for (var i = spans.length - 1; i >= 0; i--) {
                            if (spans[i].parentId === 0) {
                                result.push(spans.splice(i, 1)[0]);
                            }
                        }

                        var toDo = [];
                        for (var i = 0; i < result.length; i++) {
                            toDo.push(result[i]);
                        }



                        while (toDo.length) {
                            var node = toDo.shift(); // the parent node
                            // get the children spans
                            for (var i = spans.length - 1; i >= 0; i--) {
                                var row = spans[i];
                                if (row.parentId === node.id) {
                                    var row = spans.splice(i, 1)[0];
                                    if (node.children) {
                                        node.children.push(row);
                                    } else {
                                        node.children = [row];
                                    }
                                    toDo.push(row);
                                }
                            }
                        }
                        return result;
                    };

                    var createTree = function(tree, bg) {

                        if (tree && tree.length > 0) {

                            var ul = [],
                                len = tree.length;
                            if (tree[0].parentId === 0) {
                                ul.push('<ul class="gantt-tree">')
                            } else if (!bg) {
                                ul.push('<ul class="no-bg">');
                            } else {
                                ul.push('<ul>');
                            }

                            for (var i = 0; i < len; i++) {

                                var node = tree[i];
                                ganttItem.push(node);

                                ul.push('<li>');

                                if (node.children && node.children.length > 0) {
                                    if (node.parentId === 0) {
                                        if (len === 1) {
                                            ul.push('<i class="close-parent-root"></i>');
                                        } else {
                                            if (len === 0 || i === len - 1) {
                                                ul.push('<i class="close-root"></i>');
                                            } else {
                                                ul.push('<i class="close-center"></i>');
                                            }
                                        }
                                    } else {
                                        if (len === 1) {
                                            ul.push('<i class="close-child-root"></i>');
                                        } else {
                                            if (len === 0 || i === len - 1) {
                                                ul.push('<i class="close-root"></i>');
                                            } else {
                                                ul.push('<i class="close-center"></i>');
                                            }
                                        }
                                    }
                                } else {
                                    if (len === 0 || i === len - 1) {
                                        ul.push('<i class="link-end"></i>');
                                    } else {
                                        ul.push('<i class="link-center"></i>');
                                    }
                                }

                                ul.push('<a href="javascript:;" title="' + node.name + '">' + node.name + '</a>');

                                // 创建子树
                                if (node.children && node.children.length > 0) {
                                    if (node.children.length > 1) { // 按开始时间排序
                                        node.children.sort(function(a, b) {
                                            return a.start_time - b.start_time;
                                        })
                                    }
                                    ul.push(createTree(node.children, len > 1))
                                }

                                ul.push('</li>');
                            }
                            ul.push('</ul>');
                            return ul.join('');
                        }
                        return '';
                    };

                    var createTable = function(items) {
                        var html = [];
                        html.push('<div class="gantt-task">');
                        html.push('    <div class="gantt-head">');
                        html.push('        <div class="gantt-task-type">类型</div>');
                        html.push('        <div class="gantt-task-status">状态</div>');
                        html.push('        <div class="gantt-task-ip">IP地址</div>');
                        html.push('        <div class="gantt-task-time">开始时间</div>');
                        html.push('        <div class="gantt-task-timeline">时间轴</div>');
                        html.push('    </div>');
                        html.push('    <div class="gantt-timeline">');
                        html.push('        <ul class="gantt-time">');

                        var totalTtime = items[0].duration,
                            parentSrarttime = items[0].start_time;

                        for (var i = 0, len = items.length; i < len; i++) {
                            var width = items[i].duration === 0 ? 1 : items[i].duration / totalTtime * 100;
                            var left = (items[i].start_time - parentSrarttime) / totalTtime * 100;
                            items[i].style = 'width:' + width + '%;left:' + left + '%;';
                            items[i].start_time = new Date(parseInt(items[i].start_time)).format('yyyy-MM-dd hh:mm:ss');
                            if (items[i].debug) {
                                html.push('<li data-toggle="tooltip" data-cotent="' + items[i].debug + '">');
                            } else {
                                html.push('<li>');
                            }

                            html.push('    <div class="gantt-task-type">' + items[i].type + '</div>');
                            html.push('    <div class="gantt-task-status">' + items[i].status + '</div>');
                            html.push('    <div class="gantt-task-ip">' + items[i].ipadress + '</div>');
                            html.push('    <div class="gantt-task-time">' + items[i].start_time + '</div>');
                            html.push('    <div class="gantt-bar">');
                            html.push('        <i style="' + items[i].style + '"">');
                            html.push('            <span>' + items[i].duration + 'ms</span>');
                            html.push('        </i>');
                            html.push('    </div>');
                            html.push('</li>');
                        }
                        html.push('        </ul>');
                        html.push('    </div>');
                        html.push('</div>');
                        return html.join('');
                    };

                    var combineHtml = function() {
                        var html = [];
                        var tree = sortTree(spans);
                        var treeHtml = createTree(tree, 0);
                        var tableHtml = createTable(ganttItem);
                        html.push('<div class="gantt-container">');
                        html.push('    <div class="gantt-grid">');
                        html.push('        <div class="gantt-head gantt-task-name">方法名</div>');
                        html.push('        <div class="gantt-tree-wrap">' + treeHtml + '</div>');
                        html.push('    </div>');
                        html.push(tableHtml);
                        html.push('</div>');

                        return html.join('');
                    };

                    var render = function() {
                        var html = combineHtml();
                        tr.children('td').empty().append(html);
                        var $html = tr.children('td');

                        var $ganttTree = $html.find('.gantt-tree');
                        var $ganttTime = $html.find('.gantt-time');
                        var $lis = $ganttTree.find('li');
                        $lis.last().children('a').css('border', 0);


                        for (var i = 0; i < $lis.length; i++) {
                            (function(index) {
                                $($lis[index]).children('a').bind('mouseenter', function() {
                                    $ganttTime.children('li:eq(' + index + ')').addClass('hover');
                                }).bind('mouseleave', function() {
                                    $ganttTime.children('li:eq(' + index + ')').removeClass('hover');
                                });
                            })(i);
                        }

                        $ganttTime.children('li').each(function(i, e) {
                            var index = i;
                            var $ele = $(e);
                            if ($ele.attr('data-cotent')) {
                                $ele.popover({
                                    trigger: 'hover',
                                    placement: 'top',
                                    content: $ele.attr('data-cotent'),
                                    delay: {
                                        show: 100,
                                        hide: 500
                                    }
                                });
                            }

                            $ele.bind('mouseenter', function() {
                                $(this).addClass('hover');
                                $ganttTree.find('li:eq(' + index + ')').children('a').addClass('hover');
                            }).bind('mouseleave', function() {
                                $(this).removeClass('hover')
                                $ganttTree.find('li:eq(' + index + ')').children('a').removeClass('hover');
                            });
                        });

                        tr.slideDown();
                    };

                    render();
                }
            });


        };
	}
]);