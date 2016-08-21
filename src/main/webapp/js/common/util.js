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