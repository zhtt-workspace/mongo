/**
 * Created by zhtt on 2016/8/11.
 */
$(function(){
    organization.init();
})
var organization={
    createModalId:"createOrganizationModal",
    createFormId:"createOrganiztionForm",
    createUrl:ctx+"/organization/create-form",
    updateModalId:"updateOrganizationModal",
    updateFormId:"updateOrganiztionForm",
    updateUrl:ctx+"/organization/update-form",
    treeUrl:ctx+"/organization/tree?parentId=",
    treeId:"orgTreeDiv"
}
organization.init=function(){
    organizationTree.init();
    organization.initEvent();
}
/**
 * 为html标签绑定事件
 */
organization.initEvent=function(){
    $('#'+organization.createModalId).on('shown.bs.modal', function () {
        organizationUtil.renderFormBySeectedNode(true);
        util.bindEvent({
            el:"#"+organization.createFormId+" input[name='name']",
            eventType:"blur",
            event:function(){
                $("#"+organization.createFormId+" input[name='name']").blur(function(){
                    $("#"+organization.createFormId+" input[name='fullName']").val($("#"+organization.createFormId+" input[name='fullName']")[0].defaultValue+this.value);
                });
            }
        });
    });
    $('#'+organization.updateModalId).on('shown.bs.modal', function () {
        organizationTree.getSelectedNodes({msg:"请选择要更新的节点",callback:function(obj){
            var node=obj.nodes[0];
            var form=$("#"+organization.updateFormId);
            form.find("input[name='orgType']").filter("[value='"+node.orgType+"']").removeAttr("disabled").attr("checked",true);
            form.find("input[name='uuid']").val(node.uuid);
            form.find("input[name='name']").val(node.name);
            form.find("input[name='code']").val(node.code);
            form.find("input[name='fullName']").val(node.fullName);
            var parentNode=node.getParentNode();
            util.bindEvent({
                el:"#"+organization.updateModalId+" input[name='name']",
                eventType:"blur",
                event:function(){
                    $("#"+organization.updateModalId+" input[name='name']").blur(function(){
                        $("#"+organization.updateModalId+" input[name='fullName']").val(parentNode==null?this.value:(parentNode.fullName+this.value));
                    });
                }
            });
        }});
    });
}
/**
 * 提交新建表单
 */
organization.submitCreateForm=function(){
    $("#"+organization.createFormId).validate();
    var len=$("#"+organization.createFormId+" input[name='orgType']").filter(":checked").length;
    if(len==0){
        LobiboxUtil.notify("请选择机构类型！");
        return;
    }
    formUtil.submit({
        form:$("#"+organization.createFormId),
        url:organization.createUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("保存成功！");
                modalUtil.close("#"+organization.createModalId);
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
}
/**
 * 打开更新节点的form表单
 */
organization.openUpdateForm=function(){
    organizationTree.getSelectedNodes({msg:"请选择要更新的节点",callback:function(obj){
        $("#openUpdateOrganizationModelBtn").click();
    }});
}
/**
 * 提交新建表单
 */
organization.submitUpdateForm=function(){
    $("#"+organization.updateFormId).validate();
    var len=$("#"+organization.updateFormId+" input[name='orgType']").filter(":checked").length;
    if(len==0){
        LobiboxUtil.notify("请选择机构类型！");
        return;
    }
    formUtil.submit({
        form:$("#"+organization.updateFormId),
        url:organization.updateUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("保存成功！");
                modalUtil.close("#"+organization.updateFormId);
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
}