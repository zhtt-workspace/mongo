/**
 * Created by zhtt on 2016/8/13.
 */
var formUtil={}
/**
 * {
 *  ###下面是需传入的参数###
 *  form:form（必选,jquery对象）,
 *  url:url（可选）,
 *  ###下面是可回调的方法###
 *  error:errorCallBack（可选），
 *  success:successCallback（可选）
 * }
 * @param obj
 * @returns {boolean}
 */
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
        data:obj.data||obj.form.serialize(),// 你的formid
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
/**
 * 函数功能： 将fomr内的信息转成json
 * 参数说明：form：jquery的form对象
 */
formUtil.getumberJsonStr=function(form){
    var json=[];
    form.find("input[name]").filter(":text").each(function(){
        var value=$.trim(this.value);
        if(!(value==""||value=="0"||isNaN(value))){
            json.push('"'+this.name+'":'+(value.indexOf(".")==-1?parseInt(value):parseFloat(value)));
        }
    });
    return json.join(",");
}