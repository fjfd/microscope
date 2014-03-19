'use strict';

angular.module('microscope.app.services', [], function ($provide) {
    $provide.factory("servHighChart", function () {
        return {
            initDurationChart: function (ele, arrX, arrY) {
                new Highcharts.Chart({
                    chart: {
                        renderTo: ele,
                        type: 'column',
                    },
                    title: null,
                    xAxis: {
                        categories: arrX,
                        title: {
                            text: '响应时间 (ms)'
                        }
                    },
                    yAxis: {
                        title: {
                            text: '请求次数'
                        }
                    },
                    tooltip: {
                        headerFormat: '', //'<span style="color:{series.color}">响应时间: </span><b>{point.key/2} ~ {point.key}</b><span style="color:{series.color}"> ms</span><br/>',
                        pointFormat: '<span style="color:{series.color}">{series.name}: </span><b>{point.y}</b><span style="color:{series.color}"> 次</span><br/>'
                    },
                    series: [{
                        name: "请求次数",
                        data: arrY
                    }]
                });
            },

            initAvgDurationChart: function (ele, arrX, arrY) {
                new Highcharts.Chart({
                    chart: {
                        renderTo: ele,
                        type: 'column',
                    },
                    title: null,
                    xAxis: {
                        categories: arrX,
                        title: {
                            text: '时间 (min)'
                        },
                        labels: {
                            x: 35
                        }
                    },
                    yAxis: {
                        title: {
                            text: '平均请求时间'
                        }
                    },
                    tooltip: {
                        headerFormat: '',
                        pointFormat: '<span style="color:{series.color}">{series.name}: </span><b>{point.y}</b><span style="color:{series.color}"> ms</span><br/>'
                    },
                    series: [{
                        name: "平均请求时间",
                        data: arrY
                    }]
                });
            },

            initTraceFailCountChart: function (ele, arrX, arrY) {
                new Highcharts.Chart({
                    chart: {
                        renderTo: ele,
                        type: 'column',
                    },
                    title: null,
                    xAxis: {
                        categories: arrX,
                        title: {
                            text: '时间 (min)'
                        },
                        labels: {
                            x: 35
                        }
                    },
                    yAxis: {
                        title: {
                            text: '失败请求次数'
                        },
                        min: 0
                    },
                    tooltip: {
                        headerFormat: '',
                        pointFormat: '<span style="color:{series.color}">{series.name}: </span><b>{point.y}</b><span style="color:{series.color}"> 次</span><br/>'
                    },
                    series: [{
                        name: "失败请求次数",
                        data: arrY
                    }]
                });
            },

            initTraceHitChart: function (ele, arrX, arrY) {
                new Highcharts.Chart({
                    chart: {
                        renderTo: ele,
                        type: 'column',
                    },
                    title: null,
                    xAxis: {
                        categories: arrX,
                        title: {
                            text: '时间 (min)'
                        },
                        labels: {
                            x: 35
                        }
                    },
                    yAxis: {
                        title: {
                            text: '请求次数'
                        }
                    },
                    tooltip: {
                        headerFormat: '',
                        pointFormat: '<span style="color:{series.color}">{series.name}: </span><b>{point.y}</b><span style="color:{series.color}"> 次</span><br/>'
                    },
                    series: [{
                        name: "请求次数",
                        data: arrY
                    }]
                });
            },

            initSourcePie: function (ele, pieArr) {
                new Highcharts.Chart({
                    chart: {
                        renderTo: ele,
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: null,
                    tooltip: {
                        headerFormat: '',
                        pointFormat: '<span style="color:{series.color}">{point.name} 数据库: </span><b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                color: '#000000',
                                connectorColor: '#000000',
                                format: '{point.name} 数据库: {point.percentage:.1f} %'
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        data: pieArr
                    }]
                });
            },

            initSourceLine: function (ele, series) {
                new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: ele,
                        marginRight: 100
                    },
                    title: null,
                    xAxis: {
                        title: {
                            text: '小时 (H)'
                        },
                        labels: {
                            align: 'center',
                            x: 0
                        },
                        max: 23,
                        categories: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
                    },
                    yAxis: {
                        title: {
                            text: '调用量 (次)'
                        },
                        labels: {
                            formatter: function () {
                                return this.value === 0 ? 0 : this.value / 10000 + 'w'
                            }
                        },
                        min: 0
                    },
                    tooltip: {
                        crosshairs: true,
                        headerFormat: '',
                        pointFormat: '<span style="color:{series.color}">{series.name} 调用量: </span><b>{point.y}</b>'
                    },
                    legend: {
                        enabled: true,
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
                        borderWidth: 0
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                radius: 4,
                                lineColor: '#666666',
                                lineWidth: 1
                            }
                        }
                    },
                    series: series
                });
            },

            initMsgSizeLine: function (ele, series) {
                new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: ele
                    },
                    title: null,
                    xAxis: {
                        title: {
                            text: '小时 (H)'
                        },
                        labels: {
                            align: 'center',
                            x: 0
                        },
                        max: 23,
                        categories: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
                    },
                    yAxis: {
                        title: {
                            text: '消息大小 (M)'
                        },
                        labels: {
                            formatter: function () {
                                return this.value === 0 ? 0 : parseInt(this.value / 1024 / 1024 / 10) * 10 + 'M'
                            }
                        },
                        min: 0
                    },
                    tooltip: {
                        crosshairs: true,
                        formatter: function () {
                            var size;
                            var y = this.y / 1024 / 1024;
                            if (y < 10) {
                                size = Math.round(this.y / 1024) + 'K';
                            } else {
                                size = Math.round(this.y / 1024 / 1024) + 'M';
                            }
                            return '<span style="color:' + this.series.color + '">' + this.series.name + ': </span><b>' + size + '</b>';
                        }
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                radius: 4,
                                lineColor: '#666666',
                                lineWidth: 1
                            }
                        }
                    },
                    series: series
                });
            },
            initMsgCountLine: function (ele, series) {
                new Highcharts.Chart({
                    chart: {
                        type: 'spline',
                        renderTo: ele
                    },
                    title: null,
                    xAxis: {
                        title: {
                            text: '小时 (H)'
                        },
                        labels: {
                            align: 'center',
                            x: 0
                        },
                        max: 23,
                        categories: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
                    },
                    yAxis: {
                        title: {
                            text: '消息数量'
                        },
                        labels: {
                            formatter: function () {
                                return this.value === 0 ? 0 : this.value / 10000 + 'w'
                            }
                        },
                        min: 0
                    },
                    tooltip: {
                        crosshairs: true,
                        formatter: function () {
                            var count,
                                    y = this.y / 10000;
                            if (y < 1) {
                                count = this.y;
                            } else {
                                count = Math.round(this.y / 10000) + 'w';
                            }
                            return '<span style="color:' + this.series.color + '">' + this.series.name + ': </span><b>' + count + '</b>';
                        }
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                radius: 4,
                                lineColor: '#666666',
                                lineWidth: 1
                            }
                        }
                    },
                    series: series
                });
            }
        }
    });

    $provide.factory("servTraceReport", ['$http', 'serverConfig',
        function ($http, serverConfig) {
            return {

            }
        }
    ]);
});
