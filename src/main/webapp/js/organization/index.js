/**
 * Created by zhtt on 2016/8/11.
 */
$(function(){
    organization.init();
})
var organization={
    createUrl:ctx+"/organization/create-form",
    createFormId:ctx+"/organization/create-form",
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