/**
 * Created by zhtt on 2016/8/13.
 */
var formUtil={}
formUtil.submit=function(obj){
    if(typeof obj.form!="object"){
        LobiboxUtil.notify("form对象为空");return false;
    }
    var url=obj.url||ctx+obj.form.attr("action");
    if(typeof url!="string"){
        LobiboxUtil.notify("提交地址无效："+url);return false;
    }
    if(obj.form.valid){
        if(!obj.form.valid()){return false;}
    }
    $.ajax({
        cache: true,
        type: "POST",
        url:url,
        data:obj.form.serialize(),// 你的formid
        async: false,
        error: function(request) {
            if(obj.error){
                obj.error(data);
            }else{
                LobiboxUtil.notify(data.message);
            }
        },
        success: function(data) {
            if(obj.success){
                obj.success(data);
            }else{
                LobiboxUtil.notify("操作成功");
            }
        }
    });
    return true;
}