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
    updateFormId:ctx+"/organization/update-form",
    treeUrl:ctx+"/organization/tree?parentId=",
    treeId:"orgTreeDiv"
}
organization.init=function(){
    organizationTree.init();
    organization.initEvent();
}
organization.initEvent=function(){
    $('#'+organization.createModalId).on('show.bs.modal', function () {
        organizationTree.renderFormBySeectedNode();
    })
    $('#'+organization.createModalId).on('shown.bs.modal', function () {
        organizationTree.renderFormBySeectedNode();
        $("#"+organization.createFormId+" input[name='name']").blur(function(){
            $("#"+organization.createFormId+" input[name='fullName']").val(this.value);
        });
    })
}
organization.submitCreateForm=function(){
    $("#"+organization.createFormId).validate();
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