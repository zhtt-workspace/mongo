/**
 * Created by zhtt on 2016/9/11.
 */
function regexpUtil(){}
/** 去掉空白 **/
regexpUtil.trim=function(s){return s.replace(/(^\s*)|(\s*$)/g,"");};
/** 去掉前后空白 **/
regexpUtil.trimLR=function(s){return s.replace(/^\s+|\s+$/g, "");};
regexpUtil.trimNANforStr=function(s){return s.replace(/\D/g,"");};
/** 去掉输入框中的非数字部分 **/
regexpUtil.trimNANInputValue=function(v){
    return v.replace(/\D/g,'');
};
/** 去掉输入框中的非数字、字母部分 **/
regexpUtil.trimNANInputValue=function(v){
    return v.replace(/\W/g,'');
};
/** 必须以字母开头 **/
regexpUtil.beginWithALetterObj=function(v){
    if(v){
        var a=v.value,c=/[a-z||A-Z]/;
        var b=c.exec(a);
        if(b){
            return a.substring(b.index).replace(/\W/g,'');
        }else{
            return "";
        }
    }else{
        return "";
    }
};
/** SQL字段 **/
regexpUtil.filteSQL=function(obj){
    if(obj){
        var a=obj.value,c=/[a-z||A-Z]/;
        var b=c.exec(a);
        if(b){
            return a.substring(b.index).replace(/[^\w|\s|\(\)\,]/g,'');
        }else{
            return "";
        }
    }else{
        return "";
    }
};
/** 匹配汉字 **/
regexpUtil.trimNotCNInputValue=function(v){
    return v.replace(/[^\u4e00-\u9fa5]/g,'');
};
/** 汉字英文数字 **/
regexpUtil.trimNotCnEnDInputValue=function(v){
    return v.replace(/[^\da-z^\u4e00-\u9fa5]/gi,'');
};
regexpUtil.isEmail=function(str) {
    var reg = /^([a-zA-Z0-9_-])+@+([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])/;
    if(reg.exec(str)) {
        return false;
    }else {
        return true;
    }
};
/** IP **/
regexpUtil.isIP=function(obj){
    var val = /([0-9]{1,3}\.{1}){3}[0-9]{1,3}/;
    var ip=obj.value;
    var vald = val.exec(ip);
    if (vald == null) {
        alert('注意IP有效性');
        obj.focus();
        obj.select();
        return false;
    }
    if (vald != '') {
        if (vald[0] != ip) {
            alert('注意IP有效性');
            obj.focus();
            obj.select();
            return false;
        }
    }
};
regexpUtil.checkURL=function(obj){
    var urlpatern0 = /^https?:\/\/.+$/i;
    if(!urlpatern0.test(str)){
        alert("不合法：必须以'http:\/\/'或'https:\/\/'开头!");
        obj.focus();
        return false;
    }
};
/** url 端口号 **/
regexpUtil.checkUrlValid=function(obj){
    if(obj == null){
        return ture;
    }
    var str = obj.value;
    var urlpatern0 =/^https?:\/\/(([0-9])+(\.)?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/i;
    if(!urlpatern0.test(str)){
        obj.focus();
        return true;
    }
};

/**过滤特殊字符**/
regexpUtil.filterSpecialCode=function(obj){
    obj.value=obj.value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5\\]/g,'');
};










/** 连接字符串 **/
regexpUtil.joinStrings=function(){
    var str=[];
    for(var i=0,j=arguments.length;i<j;i++){
        str.push(arguments[i]);
    }
    return str.join("");
};
/** 判断是否有空值 **/
regexpUtil.hasNullValue=function(){
    for(var i=0,j=arguments.length;i<j;i++){
        if(arguments[i]==""){
            return true;
        }
    }
};
/** 判断是否有空值 **/
regexpUtil.hasNullValues=function(){
    for(var i=0,j=arguments.length;i<j;i++){
        for(var i1=0,j1=arguments[i].length;i1<j1;i1++){
            if(arguments[i][i1]==""){
                return true;
            }
        }
    }
};
/** 隐藏对象 */
regexpUtil.hide=function(args){
    var args_type =typeof args;
    if(args_type=="object"){
        obj.style.display="none";
    }else if(args_type=="string"){
        document.getElementById(args).style.display="none";
    }else {
        alert("regexpUtil.hide")
    }
};
/** 默认值 **/
regexpUtil.checkDefaultValue=function(obj){
    var event=window.event||event;
    if(event.type=="focus"){
        if(obj.value==obj.defaultValue){
            obj.value="";
        }else{
            obj.select();
        }
    }else if(event.type=="blur"){
        if(obj.value==""){
            obj.value=obj.defaultValue;
        }
    }
};
/** 选中指定select中的option **/
regexpUtil.checkOptionByValue=function(obj,val){
    for (var i = 0, len = obj.options.length; i < len; i++) {
        var option = obj.options[i];
        if(option.value==val){
            option.selected = true;
        }
    }
};
/** 验证系统管理中条件查询是否含有特殊字符 **/
regexpUtil.checkTeShuStr=function(obj){
    if(regexpUtil.trim(obj.value)!="" || obj.value!=null){
        var pattern= /^[a-z\d\u4E00-\u9FA5]+$/i;
        if(!pattern.test(obj.value)){
            obj.value="";
        }
    }
};
regexpUtil.checkURL=function(obj){
    var str = obj.value;
    var urlpatern0 =/^https?:\/\/((([0-9]{1,3}\.{1}){3}[0-9]{1,3})?)*(:\d+)?(\/((\.)?(\?)?=?&?[a-zA-Z0-9_-](\?)?)*)*$/i;
    if(!urlpatern0.test(str)){//不合法的URL
        obj.focus();
        return true;
    }
};
regexpUtil.validate=function(){
    for(var i=0,j=arguments.length;i<j;i++){
        if(!arguments[i].validatebox('isValid')){
            return false;
        }
    }
    return true;
};
