/**
 * Created by zhtt on 2016/8/20.
 */
var organizationUtil={};

/**
 * 打开新建机构节点的对话框，并对表单初始化信息
 */
organizationUtil.renderFormBySeectedNode=function(isLoop){
    if(isLoop==false){
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj(organization.treeId);
    if(zTree==null||zTree.getNodes().length==0){
        organizationTree.disableOrgType(null);
        return;
    }
    var nodes = zTree.getSelectedNodes();
    if(nodes.length==1){
        var obj=nodes[0];
        organizationUtil.disableOrgType(obj);
        var form=$("#"+organization.createFormId);
        if(obj.orgType!="root"){
            var fullNameNode=form.find("input[name='fullName']");
            if(fullNameNode.length==0){
                organizationUtil.renderFormBySeectedNode(false);
            }else{
                fullNameNode[0].defaultValue=obj.fullName;
                fullNameNode[0].value=obj.fullName;
            }
        }
        form.find("input[name='parentId']").val(obj.uuid);
        form.find("input[name='parentName']").val(obj.fullName);
        form.find("input[name='leave']").val(parseInt(obj.leave)+1);
        var childNodes=obj.children;
        var childLength=typeof childNodes=="undefined"?0:childNodes.length;
        form.find("input[name='sort']").val(childLength);
        form.find("input[name='code']").val(obj.code+(childLength<10?("0"+childLength):(childLength)));
    }else{
        modalUtil.close('#'+organization.createModalId)
        LobiboxUtil.notify("添加子节点时，请选择一个父节点！");
    }
}
organizationUtil.disableOrgType=function(node){
    $("#"+organization.createFormId+" input[name='orgType']").attr("disabled",true).removeAttr("checked");
    if(node==null){
        $("#"+organization.createFormId+" input[value='root']").removeAttr("disabled").attr("checked",true);
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