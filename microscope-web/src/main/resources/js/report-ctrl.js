'use strict';

angular.module('microscope.app.report', ['microscope.app.services'])

    .controller('reportTraceCtrl', ['$rootScope', '$scope', '$http', 'serverConfig', 'servHighChart', function ($rootScope, $scope, $http, serverConfig, servHighChart) {
        var now = new Date();
        $scope.visibleTab = [];
        $scope.queryYear = now.getFullYear();
        $scope.queryMonth = now.getMonth() + 1;
        $scope.queryDay = now.getDate();
        $scope.queryHour = now.getHours() > 0 ? now.getHours() - 1 : 0;
        var loadCount = 0;

        $http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/report/appAndIP?callback=JSON_CALLBACK')
            .success(function (data) {
                var apps = data.result;
                $scope.appList = [];
                for (var key in apps) {
                    $scope.appList.push(key);
                };
                $scope.queryApp = $scope.appList[0];
                $scope.appAndIpList = apps;

                $scope.ipList = $scope.appAndIpList[$scope.queryApp];
                $scope.queryIp = $scope.ipList[0];

                $scope.query();
                watch();
            });


        $scope.query = function () {
            if (loadCount < 5) {
                loadCount++;
                return;
            }

            $('.chart-content').empty();

            $http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/report/traceReport' +
                '?appName=' + $scope.queryApp +
                ($scope.traceType ? '&type=' + $scope.traceType : '') +
                '&ipAdress=' + $scope.queryIp +
                '&year=' + $scope.queryYear +
                '&month=' + $scope.queryMonth +
                '&day=' + $scope.queryDay +
                '&hour=' + $scope.queryHour +
                '&callback=JSON_CALLBACK')
                .success(function (data) {
                    $rootScope.firstLoad = false;
                    $scope.traceReportList = data.result;
                    if (!$scope.typeArr) {
                        $scope.typeArr = [];
                        for (var i = 0, l = $scope.traceReportList.length; i < l; i++) {
                            $scope.typeArr.push($scope.traceReportList[i].Type);
                        }
                    }
                });
        }

        function watch() {
            $scope.$watch('queryApp', function () {
                $scope.query;
                $scope.typeArr = null;
            });
            $scope.$watch('queryIp', $scope.query);
            $scope.$watch('queryYear', $scope.query);
            $scope.$watch('queryMonth', $scope.query);
            $scope.$watch('queryDay', $scope.query);
            $scope.$watch('queryHour', $scope.query);
        }

        $scope.showChart = function (type, name) {
            var ele = $('#trace-id-' + type);
            if (ele.is(':visible')) {
                ele.hide();
            }
            else {
                if (ele.find('.chart-content').children().length === 0) {
                    $http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/report/overTimeReport' +
                        '?appName=' + $scope.queryApp +
                        '&ipAdress=' + $scope.queryIp +
                        '&type=' + type +
                        (name ? '&name=' + name : '') +
                        '&year=' + $scope.queryYear +
                        '&month=' + $scope.queryMonth +
                        '&day=' + $scope.queryDay +
                        '&hour=' + $scope.queryHour +
                        '&callback=JSON_CALLBACK')
                        .success(function (d) {
                            var data = d.result;
                            var duration = data.duration,
                                durationX = [],
                                durationY = [];
                            for (var i in duration) {
                                if (i != 0) {
                                    durationX.push(i);
                                }
                                if (i != 65536) {
                                    durationY.push(duration[i]);
                                }
                            }
                            var avgDuration = data.avgDuration,
                                avgX = [],
                                avgY = [];
                            for (var i in avgDuration) {
                                avgX.push(i);
                                avgY.push(avgDuration[i]);
                            }

                            var hit = data.hit,
                                hitX = [],
                                hitY = [];
                            for (var i in hit) {
                                hitX.push(i);
                                hitY.push(hit[i]);
                            }

                            var fail = data.fail,
                                failX = [],
                                failY = [];
                            for (var i in fail) {
                                failX.push(i);
                                failY.push(fail[i]);
                            }

                            var charts = ele.find('.chart-content');

                            servHighChart.initDurationChart(charts[0], durationX, durationY);
                            servHighChart.initAvgDurationChart(charts[1], avgX, avgY);
                            servHighChart.initTraceHitChart(charts[2], hitX, hitY);
                            servHighChart.initTraceFailCountChart(charts[3], failX, failY);

                        });
                }
                ele.show();
            }
        };

        $scope.showTraceType = function (type) {
            if (!$scope.visibleTab.contains(type)) {
                $scope.visibleTab.push(type)
            }
            $scope.traceType = type;
            $scope.traceReportList = null;
            $scope.query();
        };

        $scope.hideTab = function (type) {
            $scope.visibleTab.remove(type);
            if ($scope.traceType === type) {
                $scope.showTraceType();
            }
        }
    }
    ])

    .controller('reportInvokeSource', ['$rootScope', '$scope', '$http', 'serverConfig', 'servHighChart', function ($rootScope, $scope, $http, serverConfig, servHighChart) {
        var now = new Date();
        $scope.queryYear = now.getFullYear();
        $scope.queryMonth = now.getMonth() + 1;
        $scope.queryDay = now.getDate();
        var loadCount = 0;

        function query() {
            if (loadCount < 3) {
                loadCount++;
                return;
            }
            $http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/report/sourceReport' +
                '?year=' + $scope.queryYear +
                '&month=' + $scope.queryMonth +
                '&day=' + $scope.queryDay +
                '&callback=JSON_CALLBACK')
                .success(function (d) {
                    var data = d.result;
                    $scope.detailResult = data.detailResult;

                    var hourResult = data.hourresult;
                    var hourObj = {},
                        series = [];
                    for (var i = 0; i < hourResult.length; i++) {
                        if (!hourObj.hasOwnProperty(hourResult[i].app)) {
                            hourObj[hourResult[i].app] = [];
                        }
                        hourObj[hourResult[i].app][hourResult[i].hour] = hourResult[i].count;
                    }
                    for (var i in hourObj) {
                        series.push({ name: i, data: hourObj[i], cursor: 'pointer' });
                    }
                    servHighChart.initSourceLine('trace-source-line', series);

                    var distResult = data.distResult;
                    var pieArr = new Array();
                    for (var i in distResult) {
                        pieArr[i] = [distResult[i].app, distResult[i].count];
                    }
                    servHighChart.initSourcePie('trace-source-pie', pieArr);
                });
        }

        function watch() {
            $scope.$watch('queryYear', query);
            $scope.$watch('queryMonth', query);
            $scope.$watch('queryDay', query);
        }

        watch();
        query();
    }])

    .controller('reportMsg', ['$rootScope', '$scope', '$http', 'serverConfig', 'servHighChart', function ($rootScope, $scope, $http, serverConfig, servHighChart) {
        var now = new Date();
        $scope.queryYear = now.getFullYear();
        $scope.queryMonth = now.getMonth() + 1;
        $scope.queryDay = now.getDate();

        var loadCount = 0;

        function query() {
            if (loadCount < 3) {
                loadCount++;
                return;
            }
            $http.jsonp('http://' + serverConfig.hostname + ':' + serverConfig.port + '/report/msgReport' +
                '?year=' + $scope.queryYear +
                '&month=' + $scope.queryMonth +
                '&day=' + $scope.queryDay +
                '&callback=JSON_CALLBACK')
                .success(function (d) {
                    var data = d.result;
                    $scope.totalSize = Math.round(data.msgReport.msg_size / 1024 / 1024);
                    $scope.totalCount = Math.round(data.msgReport.msg_num / 10000);

                    $scope.detailResult = data.detailResult;

                    var trend = data.msgReportTrend,
                        trendSize = [],
                        trendCount = [];
                    for (var i = 0; i < trend.length; i++) {
                        trendSize[trend[i].hour] = trend[i].msg_size;
                        trendCount[trend[i].hour] = trend[i].msg_num;
                    }
                    var seriesSize = [],
                        seriesCount = [];
                    seriesSize.push({ name: '消息大小', data: trendSize, cursor: 'pointer' })
                    seriesCount.push({ name: '消息数量', data: trendCount, cursor: 'pointer' })
                    servHighChart.initMsgSizeLine('report-msg-size', seriesSize);
                    servHighChart.initMsgCountLine('report-msg-count', seriesCount);

                });
        }

        function watch() {
            $scope.$watch('queryYear', query);
            $scope.$watch('queryMonth', query);
            $scope.$watch('queryDay', query);
        }

        watch();
        query();
    }]);
