/**
 * Created by zhtt on 2016/8/10.
 */
var zhttlayout={};
$(function(){
    zhttlayout.resize();
});
var t;
$(window).resize(function() {
    clearTimeout(t);
    t=setTimeout(zhttlayout.resize,1);
});
zhttlayout.resize=function(){
    $(".zhtt-layout>.zhtt-layout-right").width($(".zhtt-layout").width()-$(".zhtt-layout>.zhtt-layout-left").width()-30);
}