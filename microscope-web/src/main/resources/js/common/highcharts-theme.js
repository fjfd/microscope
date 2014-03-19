Highcharts.theme = {
    chart: {
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
            stops: [
				[0, 'rgb(255, 255, 255)'],
				[1, 'rgb(240, 240, 255)']
            ]
        },
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1,
        marginTop: 30,
        marginRight: 30
    },
    // 默认的针对图表序列颜色数组。 默认是从第一个数据列起调用第一个颜色，有多少个数据列调用相应数量的，当数据列大于默认颜色数量时，重复从第一个颜色调用。
    colors: ['#50B432', '#058DC7', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    // 默认情况下，Highcharts 在图表的右下方放置一个版权信息及链接，通过配置 credits 选项，你可以去除或自定义版权信息
    credits: {
        enabled: false
    },
    // HTML 标签，可以放置在图表区的任意位置
    labels: {
        style: {
            color: '#99b'
        }
    },
    // 图例
    legend: {
        enabled: false,
        itemStyle: {
            font: '9pt Trebuchet MS, Verdana, sans-serif',
            color: 'black'

        },
        itemHoverStyle: {
            color: '#039'
        },
        itemHiddenStyle: {
            color: 'gray'
        }
    },
    // 导出模块按钮和菜单配置选项组。
    navigation: {
        buttonOptions: {
            theme: {
                stroke: '#CCCCCC'
            }
        }
    },
    // 图表标题
    title: {
        style: {
            color: '#000',
            font: 'bold 14px "Trebuchet MS", Verdana, sans-serif'
        }
    },
    tooltip: {
        style: {
            cursor: 'pointer'
        }
    },
    // 图表副标题
    subtitle: {
        style: {
            color: '#666666',
            font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
        }
    },
    xAxis: {
        gridLineWidth: 1,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            align: 'right',
            y: 20,
            x: 22,
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: '"Helvetica Neue",Helvetica,Arial,sans-serif'

            }
        }
    },
    yAxis: {
        minorTickInterval: 'auto',
        lineWidth: 1,
        lineColor: '#000',
        tickWidth: 1, // 刻度线宽度
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: '"Helvetica Neue",Helvetica,Arial,sans-serif'
            }
        }
    }
};

// Apply the theme
Highcharts.setOptions(Highcharts.theme);