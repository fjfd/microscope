angular.module('ui.microscope', [])

    .constant('uiDatePickerConfig', {})

    .directive('uiDatePicker', ['uiDatePickerConfig',
        function (uiDatePickerConfig) {
            'use strict';
            var options;
            options = {};
            angular.extend(options, uiDatePickerConfig);

            return {
                require: 'ngModel',
                link: function (scope, element, attrs, controller) {

                    var getDateOptions = function () {
                        return angular.extend({}, uiDatePickerConfig, scope.$eval(attrs.uiDatePicker));
                    };

                    var initWidget = function () {
                        var opts = getDateOptions(),
                            onChangeDate = angular.noop;
                        if (controller) {
                            onChangeDate = function (e) {
                                scope.$apply(function () {
                                    controller.$setViewValue(e.date);
                                });
                            };

                            element.on('blur', function () {
                                scope.$apply(function () {
                                    controller.$setViewValue(element.datepicker('getDate'));
                                });
                            });

                            controller.$render = function () {
                                var date = controller.$viewValue;
                                if (angular.isDefined(date) && date !== null && !angular.isDate(date)) {
                                    throw new Error('Invalid date format');
                                }
                                element.datepicker("setValue", date);
                            };
                        }

                        element.datepicker('remove');
                        element.datepicker(opts)
                            .on('changeDate', function (e) {
                                onChangeDate(e);
                            });
                    }

                    scope.$watch(getDateOptions, initWidget, true);
                }
            };
        }
    ])

    .constant('uiTimePickerConfig', {})

    .directive('uiTimePicker', ['uiTimePickerConfig',
        function (uiTimePickerConfig) {
            'use strict';
            var options;
            options = {};
            angular.extend(options, uiTimePickerConfig);

            return {
                require: '?ngModel',
                link: function (scope, element, attrs, controller) {

                    var getTimeOptions = function () {
                        return angular.extend({}, uiTimePickerConfig, scope.$eval(attrs.uiTimePicker));
                    };

                    var initWidget = function () {
                        var opts = getTimeOptions(),
                            onChangeTime = angular.noop;

                        if (controller) {
                            onChangeTime = function (e) {
                                scope.$apply(function () {
                                    controller.$setViewValue(e.time.value);
                                });
                            };

                            controller.$render = function () {
                                var time = controller.$viewValue;
                                if (angular.isDefined(time) && time !== null && !angular.isDate(time)) {
                                    throw new Error('Invalid time format');
                                }
                                element.datepicker("setValue", time);
                            };
                        }

                        element.timepicker(opts)
                            .on('changeTime.timepicker', function (e) {
                                onChangeTime(e);
                            });
                    }
                    initWidget();
                }
            };
        }
    ])

    .constant('uiDateTimeSelectorConfig', {})

    .directive('uiDateTimeSelector', ['uiDateTimeSelectorConfig',
        function (uiDateTimeSelectorConfig) {
            'use strict';
            var options;
            options = {};
            angular.extend(options, uiDateTimeSelectorConfig);

            return {
                require: '?ngModel',
                link: function (scope, element, attrs, controller) {
                    var getOptions = function () {
                        return angular.extend({}, uiDateTimeSelectorConfig, scope.$eval(attrs.uiDateTimeSelector));
                    };

                    var initWidget = function () {
                        var now = new Date(),
                            year = now.getFullYear(),
                            curMonth = now.getMonth() + 1,
                            curDay = now.getDate(),
                            curHour = now.getHours(),
                            years = [];
                        while (year >= 2013) {
                            years.push(year);
                            year--;
                        }

                        var selects = angular.element(element).find('select'),
                            selectYear,
                            selectMonth,
                            selectDay,
                            selectHour;
                        for (var i = 0, l = selects.length; i < l; i++) {
                            var ele = angular.element(selects[i]);
                            if (ele.hasClass('selector-year')) {
                                selectYear = ele;
                            } else if (ele.hasClass('selector-month')) {
                                selectMonth = ele;
                            } else if (ele.hasClass('selector-day')) {
                                selectDay = ele;
                            } else if (ele.hasClass('selector-hour')) {
                                selectHour = ele;
                            }
                        }


                        var initYear = function () {
                            if (selectYear) {
                                selectYear.empty();
                                for (var i = 0, l = years.length; i < l; i++) {
                                    selectYear.append('<option>' + years[i] + '</option>');
                                }
                                selectYear.val(now.getFullYear());
                                selectYear.bind('change', function () {
                                    initDay();
                                });
                            }
                        }

                        var initMonth = function () {
                            if (selectMonth) {
                                selectMonth.empty();
                                for (var i = 1; i <= 12; i++) {
                                    selectMonth.append('<option>' + i + '</option>');
                                }
                                selectMonth.bind('change', function () {
                                    initDay();
                                });
                                selectMonth.val(curMonth);
                            }
                        }

                        var initDay = function () {
                            if (selectDay) {
                                var oldDay = selectDay.val();
                                selectDay.empty();
                                selectDay.empty();
                                var maxDay = new Date(selectYear.val(), selectMonth.val(), 0).getDate();
                                for (var i = 1; i <= maxDay; i++) {
                                    selectDay.append('<option>' + i + '</option>');
                                }
                                selectDay.val(oldDay > 0 ? oldDay : curDay);
                            }
                        }

                        var initHour = function () {
                            if (selectHour) {
                                selectHour.empty();
                                for (var i = 0; i <= 23; i++) {
                                    selectHour.append('<option>' + i + '</option>');
                                }
                                selectHour.val(curHour > 0 ? curHour - 1 : 0);
                            }
                        }

                        initYear();
                        initMonth();
                        initDay();
                        initHour();
                    };

                    scope.$watch(getOptions, initWidget, true);
                }
            };
        }
    ])

    .directive('uiAutocomplete', ['$compile', '$parse', '$document',
        function ($compile, $parse, $document) {

            var HOT_KEYS = [9, 13, 27, 38, 40];

            return {
                require: 'ngModel',
                link: function (originalScope, element, attrs, controller) {

                    var onSelectCallback = $parse(attrs.onSelect),
                        activeItem = -1,
                        matchItems,
                        $setModelValue = $parse(attrs.ngModel).assign,
                        ignoreCase = originalScope.$eval(attrs.ignoreCase) !== false; // 是否忽略大小写
                    popUpEle = angular.element('<ul class="dropdown-menu ui-autocomplete"></ul>');

                    element.after(popUpEle);

                    var escapeRegexp = function (queryToEscape) {
                        return queryToEscape.replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
                    };

                    var setMatchItems = function (inputValue) {
                        popUpEle.empty();
                        matchItems = originalScope.$eval(attrs.uiAutocomplete);
                        if (inputValue) {
                            var arr = matchItems.slice(0),
                                reg,
                                matched = false;
                            for (var i = arr.length - 1; i >= 0; i--) {
                                reg = new RegExp(escapeRegexp(inputValue), 'gi');
                                if (ignoreCase) {
                                    matched = reg.test(arr[i]);
                                } else {
                                    matched = arr[i].indexOf(inputValue) >= 0;
                                }
                                if (matched) {
                                    arr[i] = arr[i].replace(new RegExp(escapeRegexp(inputValue), 'gi'), '<strong>$&</strong>');
                                }
                                else {
                                    arr.splice(i, 1);
                                }
                            }
                            matchItems = arr;
                        }
                        if (matchItems && matchItems.length > 0) {
                            for (var i = 0, l = matchItems.length; i < l; i++) {
                                popUpEle.append('<li>' + matchItems[i] + '</li>');
                            }
                            if (inputValue) {
                                activeItem = 0;
                                hilightItem(activeItem);
                            }
                            show();
                        } else {
                            hide();
                        }
                    };

                    var show = function () {
                        popUpEle.css('display', 'block');
                    };

                    var hide = function () {
                        popUpEle.css('display', 'none');
                    };

                    var hilightItem = function (index) {
                        popUpEle.find('.active').removeClass('active');
                        popUpEle.find('li:eq(' + index + ')').addClass('active');
                    };

                    var selectItem = function (index) {
                        var text = popUpEle.find('li:eq(' + index + ')').text();
                        $setModelValue(originalScope, text);
                        onSelectCallback(originalScope);
                        hide();
                    };

                    controller.$render = function () {
                        $setModelValue(controller.$viewValue);
                    }

                    element.bind('focus click', function (evt) {
                        evt.target.value = '';
                        $setModelValue(originalScope, '');
                        setMatchItems('');
                    });

                    element.bind('keydown', function (evt) {
                        if (matchItems.length === 0 || HOT_KEYS.indexOf(evt.which) === -1) {
                            return;
                        }

                        evt.preventDefault();

                        if (evt.which === 40) {  // down
                            activeItem = (activeItem + 1) % matchItems.length;
                            hilightItem(activeItem);

                        } else if (evt.which === 38) { // up
                            activeItem = (activeItem ? activeItem : matchItems.length) - 1;
                            hilightItem(activeItem);

                        } else if (evt.which === 13 || evt.which === 9) { // enter tab
                            selectItem(activeItem);
                        } else if (evt.which === 27) { // esc
                            evt.stopPropagation();
                            hide();
                        }
                    });

                    controller.$parsers.unshift(function (inputValue) {
                        $setModelValue(originalScope, inputValue);
                        setMatchItems(inputValue);
                    });

                    var dismissClickHandler = function (evt) {
                        if (element[0] !== evt.target) {
                            hide();
                        }
                    };

                    $document.bind('click', dismissClickHandler);

                    popUpEle.on('click', 'li', function () {
                        selectItem($(this).index());
                    });

                    originalScope.$on('$destroy', function () {
                        $document.unbind('click', dismissClickHandler);
                    });

                }
            }
        }])
;
