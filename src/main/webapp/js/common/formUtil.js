/**
 * Created by zhtt on 2016/8/13.
 */
var formUtil={}
formUtil.submit=function(obj){
    if(obj.form.valid){
        //if(!obj.form.valid()){return false;}
    }
    $.ajax({
        cache: true,
        type: "POST",
        url:obj.url||obj.form.attr("action"),
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