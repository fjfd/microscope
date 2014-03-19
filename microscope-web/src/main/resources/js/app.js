(function() {
    'use strict';
    // 加载脚本
    var labjs = $LAB
        .script('js/lib/jquery.min.js').wait()
        .script('js/lib/bootstrap.min.js')
        .script('js/lib/bootstrap-datepicker.js')
        .script('js/lib/bootstrap-tooltip.js')
        .script('js/lib/bootstrap-popover.js')
        .script('js/lib/timepicker.min.js')
        .script('js/lib/jquery.nicescroll.min.js')
        .script('js/lib/jquery.ba-resize.min.js')
        .script('js/lib/highcharts.js')
        .script('js/lib/highcharts-more.js')
        .script('js/common/highcharts-theme.js')
        .script('js/lib/angular.min.js')
        .script('js/lib/ui-bootstrap-tpls-0.10.0.min.js')
        .script('js/lib/angular-ui-router.min.js')
        .script('js/lib/ui-utils.min.js')
        .script('js/common/utils.js')
        .script('js/common/filters.js')
        .script('js/common/services.js')
        .script('js/common/ui.js')
        .script('js/trace-ctrl.js')
        .script('js/report-ctrl.js')
        .script('js/exception-ctrl.js')

    .wait(function() {
        angular.module('microscope.app', [
            'ui.microscope',
            'microscope.app.trace',
            'microscope.app.report',
            'microscope.app.exception'
        ]);

        angular.module('microscope', [
            'ui.bootstrap',
            'ui.router',
            'microscope.app'
        ])

        .constant('serverConfig', {
            // hostname: 'microscope.tools.vip.com',
            // port: '80'
            // hostname :'10.100.90.183',
            // port: '8080'
            //hostname: '192.168.52.145',
            //port: '8080'
            hostname: '10.101.3.111',
            port: '8080'
        })

        .config(['$stateProvider', '$urlRouterProvider',
            function($stateProvider, $urlRouterProvider) {
                $urlRouterProvider
                //.when('/c?id', '/contacts/:id')
                //.when('/user/:id', '/contacts/:id')
                .otherwise('/trace');

                $stateProvider
                    .state('home', {
                        url: '/home',
                        templateUrl: 'partials/home.html',
                        controller: ''
                    })
                    .state('alert', {
                        url: '/alert',
                        templateUrl: 'partials/alert.html',
                        controller: ''
                    })
                    .state('trace', {
                        url: '/trace',
                        templateUrl: 'partials/trace.html',
                        controller: 'traceCtrl'
                    })
                    .state('exception', {
                        url: '/exception',
                        templateUrl: 'partials/exception.html',
                        controller: 'exceptionCtrl'
                    })
                    .state('report', {
                        url: '/report',
                        templateUrl: 'partials/report.html',
                        controller: ''
                    })
                    .state('trace-detail', {
                        url: '/trace/detail/{traceId}',
                        templateUrl: 'partials/trace-detail.html',
                        controller: 'traceDetailCtrl'
                    })

                .state('report.top10', {
                    url: '/top10',
                    templateUrl: 'partials/report-top10.html',
                    controller: ''
                })

                .state('report.monitor', {
                    url: '/monitor',
                    templateUrl: 'partials/report-monitor.html',
                    controller: ''
                })

                .state('report.trace', {
                    url: '/trace',
                    templateUrl: 'partials/report-trace.html',
                    controller: 'reportTraceCtrl'
                })
                    .state('report.source', {
                        url: '/invoke-source',
                        templateUrl: 'partials/report-source.html',
                        controller: 'reportInvokeSource'
                    })
                    .state('report.depend', {
                        url: '/invoke-depend',
                        templateUrl: 'partials/report-depend.html',
                        controller: ''
                    })
                    .state('report.msg', {
                        url: '/msg',
                        templateUrl: 'partials/report-msg.html',
                        controller: 'reportMsg'
                    });
            }
        ])

        .run(['$rootScope', '$state', '$stateParams',
            function($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]);

        angular.element(document).ready(function() {
            angular.bootstrap(document, ['microscope']);
        });
    });
})();
