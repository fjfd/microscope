angular.module('reportModule',[]).
	controller('reportCtrl',function ($rootScope, $scope, $route, $http, $location) {

		$('.m-chart').click(function() {
    		$(this).next("li").toggle();
		});
		
		$('#chart_1').click(function() {

		    $.ajax({
		        type : 'post',
		        url: 'line.json',
		        data : {
		            'category1_id': $.trim($("#category1").val()),
		            'category2_id': $.trim($("#category2").val()),
		            'category3_id': $.trim($("#category3").val())
		        },
		        dataType: 'json',
		        success: function(rdata) {

		        	//折线图
		        	getline(rdata);

		        	//饼状图
		        	getPie(rdata);

		        	//柱状图
		        	getDrilldown(rdata)

		        	//表格
		        	getTable(rdata);
		        }

		    });

		});

		/*绘制折线图 */
		function getline(data) {
			var lineChart;
			lineChart = new Highcharts.Chart({  
	            chart: {
	                type: 'spline',
	                renderTo: 'line-chart', //设置显示图表的容器
	            },
	            title: {
	                text: 'db(feel)在2013-04-17的调用来源一览'
	            },

	            xAxis: {
	                categories: ['04-17', '04:00', '08:00', '12:00', '16:00', '20:00', '04-18']
	            },
	            yAxis: {
	                title: {
	                    text: '调用量'
	                },
	                labels: {
	                    formatter: function() {
	                        return this.value +'万'
	                    }
	                }
	            },
	            tooltip: {
	                crosshairs: true,
	                shared: true
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
	            series: data
    		}); 
		}

		/* 绘制饼状图 */
		function getPie(data) {
			var pieChart;
			pieChart = new Highcharts.Chart({
	            chart: {
	                renderTo: 'pie-chart', //设置显示图表的容器
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false
	            },
	            title: {
	                text: '(db@feel的调用来源分布)'
	            },
	            tooltip: {
	                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	            },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        color: '#000000',
	                        connectorColor: '#000000',
	                        format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                    }
	                }
	            },
	            series: [{
	                type: 'pie',
	                name: 'Browser share',
	                data: [
	                    ['女士',   45.0],
	                    ['男士',   26.8],
	                    {
	                        name: '儿童',
	                        y: 12.8,
	                        sliced: true,
	                        selected: true
	                    },
	                    ['居家', 15.4]
	                ]
	            }]
	        });
		}

		/* 绘制柱状图 */
		function getDrilldown(data) {

	        $('#column-chart').highcharts({
	            chart: {
	                type: 'column',
	                margin: [ 50, 50, 100, 80]
	            },
	            title: {
	                text: '访问量'
	            },
	            xAxis: {
	                categories: [
	                    'lg_core',
	                    'pic',
	                    'shop_sites',
	                    'simbauic',
	                    'bbc_prc',
	                    'feel',
	                    'Sao Paulo',
	                    'Mexico City',
	                    'Dehli',
	                    'Osaka'
	                ],
	                labels: {
	                    rotation: -45,
	                    align: 'right',
	                    style: {
	                        fontSize: '13px',
	                        fontFamily: 'Verdana, sans-serif'
	                    }
	                }
	            },
	            yAxis: {
	                min: 0,
	                title: {
	                    text: '访问量 (万)'
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            tooltip: {
	                pointFormat: '访问量: <b>{point.y:.1f} 万</b>',
	            },
	            series: [{
	                name: 'Population',
	                data: [34.4, 21.8, 20.1, 20, 19.6, 19.5, 19.1, 18.4, 18,
	                    17.3],
	                dataLabels: {
	                    enabled: true,
	                    rotation: -90,
	                    color: '#FFFFFF',
	                    align: 'right',
	                    x: 4,
	                    y: 10,
	                    style: {
	                        fontSize: '13px',
	                        fontFamily: 'Verdana, sans-serif',
	                        textShadow: '0 0 3px black'
	                    }
	                }
	            }]
	        });
		}

		function getTable(data) {
	        var jsonObj = {};
	        jsonObj.Rows = [
	            { id: 1, apply: "feel", type1: "读", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 2, apply: "feel", type1: "写", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 3, apply: "feel", type1: "读", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 4, apply: "feel", type1: "写", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 5, apply: "feel", type1: "读", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 6, apply: "feel", type1: "写", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 7, apply: "feel", type1: "读", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' },
	            { id: 8, apply: "feel", type1: "写", QRS: "16399.37",PV:'14亿1699万5330',call:'3.98',time_consuming:'5ms',machine_room:'50.33%',client_distribution:'56.23%' }
	        ];
	        var columns =
	            [
	                { display: '序号', name: 'id', type: 'int', mintWidth: 40, width: 100 },
	                { display: '应用', name: 'apply' },
	                { display: '类型', name: 'type1' },
	                { display: 'QRS', name: 'QRS' },
	                { display: '访问量', name: 'PV' },
	                { display: '调用均值', name: 'call' },
	                { display: '耗时', name: 'time_consuming' },
	                { display: '同机房', name: 'machine_room' },
	                { display: '客户端机房分布', name: 'client_distribution'}
	             ];
	        $("#zip_table").ligerGrid({
	            columns: columns,
	            data: jsonObj
	        });

		}


	})