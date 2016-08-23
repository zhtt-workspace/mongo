/**
 * Created by zhtt on 2016/8/13.
 *order目前不严格，因为没有在后台验证(新增、删除)
 * 删除时，后台没有验证是否有子节点
 * 新增、删除后前台节点没有更新
 */
var organizationTree={
    tree:null
}
organizationTree.init=function(){
    organizationTree.tree=new zTreeUtil({
        treeDivId:organization.treeId,
        url:organization.treeUrl,
        ajaxLoad:true,
        onAsyncSuccess:organizationTree.onAsyncSuccess
    });
    organizationTree.tree.init();
}
/**
 * 异步加载成功后执行的方法
 * @param event
 * @param treeId
 * @param treeNode
 * @param msg
 */
organizationTree.onAsyncSuccess=function(event,treeId,treeNode,msg){
    try{
        var obj=$.parseJSON(msg);
        if(obj.length==0){
            $("#openCreateOrganizationModelBtn").click();
            setTimeout(function(){
                $("#"+organization.createModalId+" .panel-heading").html("请初始化机构树根节点！");
            },100);
        }else{
            organization.loadTableList();
        }
    }catch (e){}
}