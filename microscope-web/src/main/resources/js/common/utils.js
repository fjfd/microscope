'use strict';

(function () {
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        }

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };

    Array.prototype.contains = function (item) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == item) { return true; }
        }
        return false;
    };

    Array.prototype.remove = function (item) {
        var index = -1;
        for (var i = 0; i < this.length; i++) {
            if (this[i] == item) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            this.splice(index, 1);
            return true;
        }
        return false;
    };

})();