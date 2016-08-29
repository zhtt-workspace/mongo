/**
 * Created by zhtt on 2016/8/9.
 */
$(function(){
    login.init();
});
var login={
    loginUrl:ctx+"/login/"
};
login.init=function(){
    login.initEvent();
}
login.initEvent=function(){
    $("#password").focus(function(){
        $("#left_hand").animate({left: "150",top: " -38"},
        {step: function(){
            if(parseInt($("#left_hand").css("left"))>140){
                $("#left_hand").attr("class","left_hand");
            }
        }}, 2000);
        $("#right_hand").animate({right: "-64",top: "-38px"},
        {step: function(){
            if(parseInt($("#right_hand").css("right"))> -70){
                $("#right_hand").attr("class","right_hand");
            }
        }}, 2000);
    });
    //失去焦点
    $("#password").blur(function(){
        $("#left_hand").attr("class","initial_left_hand");
        $("#left_hand").attr("style","left:100px;top:-12px;");
        $("#right_hand").attr("class","initial_right_hand");
        $("#right_hand").attr("style","right:-112px;top:-12px");
    });
}
/**
 * 登录
 */
login.login=function(){
    var username=$("input[name='username']").val();
    var password=$("input[name='password']").val();
    $.get(login.loginUrl+username+"/"+password,function(data){
        if(data.status="success"){
             if(data.data==null){
                 LobiboxUtil.notify("用户名或密码错误！");
            }else{
                 window.location.href =ctx+"/index"
            }
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
/** 回车登录 **/
login.fnKey = function(oEvent) {
    if (oEvent.keyCode == 13) {
        login.login();
    }
};