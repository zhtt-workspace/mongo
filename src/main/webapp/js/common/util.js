var util={};
/**
 * 为标签绑定事件之前检查是否已经绑定事件
 * @param obj：
 *  el:通过jquery获取标签的选择器表达式
 *  eventType:需要绑定的事件的类型
 *  event:需要绑定的事件
 */
util.bindEvent=function(obj){
    $(obj.el).each(function () {
        var e = $._data(this, "events");
        if (e && e[obj.eventType]) {
        } else {
            obj.event();
        }
    });
}
/**
 * 根据 text 选中 select 的 option
 * @param obj
 * @param text
 */
util.selectByText=function(obj,text){
    for (var i = 0, len = obj.options.length; i < len; i++) {
        var option = obj.options[i];
        if ((option.value == text) || (option.value == "" && text == null)) {
            option.setAttribute("selected",true);
        } else {
            option.removeAttribute("selected");
        }
    }
};
/**
 * 根据 value 选中 select 的 option
 * @param obj
 * @param v
 */
util.selectByValue=function(obj,v){
    for (var i = 0, len = obj.options.length; i < len; i++) {
        var option = obj.options[i];
        if ((option.value == v) || (option.value == "" && v == null)) {
            option.setAttribute("selected",true);
        } else {
            option.removeAttribute("selected");
        }
    }
};
/** 添加事件 **/
util.addEvent=function(obj,eventtype,eventfn){
    if (document.all){
        obj.attachEvent (eventtype,eventfn);
    }else{
        obj.addEventListener(eventtype, eventfn);
    }
};
/** 删除事件 **/
util.removeEvent=function(obj,eventtype,eventfn){
    if (document.all){
        obj.detachEvent (eventtype,eventfn);
    }else{
        obj.removeEventListener(eventtype,eventfn, false);
    }
};
/** 网页中添加js文件 **/
util.addJsFile=function(urlStr){
    var ga = document.createElement('script');
    ga.type = 'text/javascript';
    ga.async = true;
    ga.src = urlStr;
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(ga, s);//自己决定要引入JS文件的位置
};
/** 获取鼠标点击时X坐标  **/
util.getX=function (e){
    e = e || window.event;
    return e.pageX || e.clientX + document.body.scroolLeft;
};
/** 获取鼠标点击时Y坐标  **/
util.getY=function (e){
    e = e|| window.event;
    return e.pageY || e.clientY + document.boyd.scrollTop;
};