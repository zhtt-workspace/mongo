/**
 * Created by zhtt on 2016/8/13.
 *order目前不严格，因为没有在后台验证(新增、删除)
 * 删除时，后台没有验证是否有子节点
 * 新增、删除后前台节点没有更新
 */
var organizationTree={}
organizationTree.init=function(){

    var orgTree=new orgTreeUtil({
        treeDivId:organization.treeId,
        url:organization.treeUrl,
        onClick:organizationTree.onClick,
        onAsyncSuccess:organizationTree.onAsyncSuccess
    });
    orgTree.init();
}
/**
 * ztree节点的点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
organizationTree.onClick=function(event, treeId, treeNode){
    if(typeof treeNode.children=="undefined"){
        $.get(organization.treeUrl+treeNode.uuid,function(data){
            organizationTree.addZtreeNode({treeId:treeId,nodeData:data});
        });
    }
}
/**
 * 向ztree中添加新节点
 * @param obj
 */
organizationTree.addZtreeNode=function(obj){
    var treeObj = $.fn.zTree.getZTreeObj(obj.treeId);
    var nodes = treeObj.getSelectedNodes();
    //var nodeData = {name:"newNode1"};
    newNode = treeObj.addNodes(nodes[0], obj.nodeData);
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
    }catch (e){
        LobiboxUtil.notify(e.message);
        LobiboxUtil.notify(e.description);
        LobiboxUtil.notify(e.number);
        LobiboxUtil.notify(e.name);
    }
}
/**
 * 获取选中的节点
 * @param args
 * @returns {*}
 */
organizationTree.getSelectedNodes=function(args){
    if(typeof args=="string"){
        args={msg:args};
    }
    var zTree = $.fn.zTree.getZTreeObj(organization.treeId);
    if(zTree==null||zTree.getNodes().length==0){
        if($('#'+organization.createModalId).is(":hidden")){
            LobiboxUtil.notify("您还没有新建机构，请选新建机构！");
            $("#openCreateOrganizationModelBtn").click();
        }
        return {"zTree":null,"nodes":null};;
    }
    var nodes = zTree.getSelectedNodes();
    if(nodes.length==0){
        if(args.msg){
            LobiboxUtil.notify(args.msg);
        }
        return {"zTree":zTree,"nodes":null};
    }else{
        if(typeof args=="object"&&typeof args.callback=="function"){
            args.callback({"zTree":zTree,"nodes":nodes});
        }
        return {"zTree":zTree,"nodes":nodes};
    }
}

