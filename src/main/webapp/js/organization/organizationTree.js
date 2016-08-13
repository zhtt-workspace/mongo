/**
 * Created by zhtt on 2016/8/13.
 */
var organiztionTree={}
organiztionTree.init=function(){
    $.get(organization.treeUrl,function(data){
        if(typeof data=="object"&&data.length>0){

        }else{
            if(data.length==0){
                $("#openCreateOrganizationModelBtn").click();
            }else{
                LobiboxUtil.notify("加载树失败！");
            }
        }
    });
}
organiztionTree.setting={
    callback: {
        onClick: organiztionTreeOnclick
    }
}
function organiztionTreeOnclick(event, treeId, treeNode){

}