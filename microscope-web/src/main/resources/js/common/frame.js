(function($) {
    function getNavElement(e) {
        var t = $.trim(e.find(">a").text()),
            arr = [],
            subLength = e.find(">.dropdown-menu").length;
        arr.push("<li><a href='" + e.find(">a").attr("href") + "'>");
        if (subLength > 0) {

            arr.push('<i class="icon-angle-left"></i>')
        }
        arr.push(t + "</a>");
        if (subLength > 0) {
            arr.push(getNav(e.find(">.dropdown-menu")));
        }
        arr.push("</li>");
        return arr.join('');
    }

    function getNav(e) {
        var t = "";
        t += "<ul>";
        e.find(">li").each(function() {
            t += getNavElement($(this))
        });
        t += "</ul>";
        nav = t;
        return t
    }

    function initMobileNav() {
        if ($(".mobile-nav").length == 0) {
            var e = $("#navigation .main-nav"),
                nav = getNav(e);
            $("#navigation").append(nav);
            $("#navigation > ul").last().addClass("mobile-nav");
            $(".mobile-nav > li > a").click(function(e) {
                var t = $(this);
                if (t.next().length !== 0) {
                    e.preventDefault();
                    var n = t.next();
                    t.parents(".mobile-nav").find(".open").not(n).each(function() {
                        var e = $(this);
                        e.removeClass("open");
                        e.prev().find("i").removeClass("icon-angle-down").addClass("icon-angle-left")
                    });
                    n.toggleClass("open");
                    t.find("i").toggleClass("icon-angle-left").toggleClass("icon-angle-down");
                    fixMainHeight();
                }
            })
        }
    }

    function toggleMobileNav() {
        var e = $(".mobile-nav");
        e.toggleClass("open");
        e.find(".open").removeClass("open");
        fixMainHeight();
    }

    function toggleSideBar() {
        $("#left").toggle().toggleClass("forced-hide");
        $("#left").is(":visible") ? $("#main").css("margin-left", $("#left").width()) : $("#main").css("margin-left", 0);
    }

    function fixMainHeight() {
        $('#content').height($(window).height() - $('#navigation').outerHeight());
        $("#main").getNiceScroll().resize().show();
    }

    $(document).ready(function() {
        initMobileNav();
        $("#navigation").on("click", ".toggle-mobile", function(e) {
            e.preventDefault();
            toggleMobileNav()
        }).on("click", ".toggle-sidebar", function(e) {
            e.preventDefault();
            toggleSideBar();
        });
        fixMainHeight();
        $("#main").niceScroll({
            cursorborder: 0,
            cursoropacitymax: 0.8,
            cursorcolor: "#000",
            railoffset: {
                top: 0,
                left: -2
            },
            autohidemode: !1,
            horizrailenabled: !1
        });
        $("#main>.container-fluid").resize(function() {
            $("#main").getNiceScroll().resize().show();
        });
    });

    $(window).resize(function(e) {
        var e = $(window);
        if (e.width() > 840) {
            $(".mobile-nav").removeClass("open");
        }
        fixMainHeight();
    });

})(window.jQuery);
