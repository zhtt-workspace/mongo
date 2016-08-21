/**
 * Created by zhtt on 2016/8/20.
 */
var organizationUtil={};

/**
 * 打开新建机构节点的对话框，并对表单初始化信息
 */
organizationUtil.renderCreateFormByNode=function(node){
    organizationUtil.disableOrgType(node);
    var form=$("#"+organization.createFormId);
    if(node.orgType!="root"){
        var fullNameNode=form.find("input[name='fullName']");
        fullNameNode[0].defaultValue=node.fullName;
        fullNameNode[0].value=node.fullName;
    }
    form.find("input[name='name']").val("");
    form.find("input[name='parentId']").val(node.uuid);
    form.find("input[name='parentName']").val(node.fullName);
    form.find("input[name='leave']").val(parseInt(node.leave)+1);
    var childNodes=node.children;
    var childLength=typeof childNodes=="undefined"?0:childNodes.length;
    form.find("input[name='sort']").val(childLength);
    form.find("input[name='code']").val(node.code+(childLength<10?("0"+childLength):(childLength)));
}
organizationUtil.disableOrgType=function(node){
    $("#"+organization.createFormId+" input[name='orgType']").attr("disabled",true).removeAttr("checked");
    if(node==null){
        $("#"+organization.createFormId+" input[value='root']").removeAttr("disabled");
        $("#"+organization.createFormId+" input[value='root']")[0].checked=true;
    }else{
        switch (node.orgType){
            case "root":$("#"+organization.createFormId+" input[value='org']").removeAttr("disabled");$("#"+organization.createFormId+" input[value='org']").attr("checked",true);$("#"+organization.createFormId+" input[value='headquarters']").removeAttr("disabled");break;
            case "org":$("#"+organization.createFormId+" input[value='org']").removeAttr("disabled");$("#"+organization.createFormId+" input[value='org']").attr("checked",true);$("#"+organization.createFormId+" input[value='headquarters']").removeAttr("disabled");break;
            case "headquarters":$("#"+organization.createFormId+" input[value='dept']").removeAttr("disabled");$("#"+organization.createFormId+" input[value='dept']").attr("checked",true);break;
            case "dept":$("#"+organization.createFormId+" input[value='dept']").removeAttr("disabled");$("#"+organization.createFormId+" input[value='dept']").attr("checked",true);break;
            default :LobiboxUtil.notify("未类型："+node.orgType);break;
        }
    }
}