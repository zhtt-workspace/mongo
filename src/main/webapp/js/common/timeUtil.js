/**
 * Created by zhtt on 2016/9/11.
 */
var timeUtil={};

/** 获取当前时间  **/
timeUtil.getCurrentDateTime=function(){
    var today = new Date ();
    return timeUtil.format(today,'yyyy-MM-dd');
};
timeUtil.format = function(date,format){
    var o = {
        "M+" : date.getMonth()+1, //month
        "d+" : date.getDate(), //day
        "h+" : date.getHours(), //hour
        "m+" : date.getMinutes(), //minute
        "s+" : date.getSeconds(), //second
        "q+" : Math.floor((date.getMonth()+3)/3), //quarter
        "S" : date.getMilliseconds() //millisecond
    };

    if(/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o) {
        if(new RegExp("("+ k +")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
};
timeUtil.getByMillisecondsStr = function (str) {
    if (str == null || str == "") {
        return null;
    } else {
        var date = new Date();
        date.setTime(str);
        return timeUtil.format(date, 'yyyy-MM-dd');
    }
};