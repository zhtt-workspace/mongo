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
    treeUrl:ctx+"/organization/tree",
    treeId:"orgTreeDiv"
}
organization.init=function(){
    organiztionTree.init();
}
organization.submitCreateForm=function(){

}