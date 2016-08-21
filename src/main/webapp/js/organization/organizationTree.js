/**
 * Created by zhtt on 2016/8/13.
 * code处理、order目前不严格，因为没有在后台验证
 */
var organizationTree={}
organizationTree.init=function(){
    $.fn.zTree.init($("#"+organization.treeId), organizationTree.setting);
}
organizationTree.setting={
    view: {
        selectedMulti: false
    },
    async: {
        enable: true,
        url:organization.treeUrl,
        autoParam:["uuid"],
        otherParam:{"otherParam":"zTreeAsyncTest"}
    },
    callback: {
        onClick: onClick,
        onAsyncSuccess:onAsyncSuccess
    }
}
/**
 * 异步加载成功后执行的方法
 * @param event
 * @param treeId
 * @param treeNode
 * @param msg
 */
function onAsyncSuccess(event,treeId,treeNode,msg){
    try{
        var obj=$.parseJSON(msg);
        if(obj.length==0){
            $("#openCreateOrganizationModelBtn").click();
            setTimeout(function(){
                $("#"+organization.createModalId+" .panel-heading").html("请初始化机构树根节点！");
            },100);
        }
    }catch (e){
        LobiboxUtil.notify(e.message);
        LobiboxUtil.notify(e.description);
        LobiboxUtil.notify(e.number);
        LobiboxUtil.notify(e.name);
    }

}
/**
 * ztree节点的点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function onClick(event, treeId, treeNode){
    if(typeof treeNode.children=="undefined"){
        $.get(organization.treeUrl+treeNode.uuid,function(data){
            addZtreeNode({treeId:treeId,nodeData:data});
        });
    }
}
function addZtreeNode(obj){
    var treeObj = $.fn.zTree.getZTreeObj(obj.treeId);
    var nodes = treeObj.getSelectedNodes();
    //var nodeData = {name:"newNode1"};
    newNode = treeObj.addNodes(nodes[0], obj.nodeData);
}
function dataFilter(treeId, parentNode, childNodes){
    LobiboxUtil.notify("dataFilter");
}
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
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
        LobiboxUtil.notify(args.msg);
        return {"zTree":zTree,"nodes":null};
    }else{
        if(typeof args=="object"&&typeof args.callback=="function"){
            args.callback({"zTree":zTree,"nodes":nodes});
        }
        return {"zTree":zTree,"nodes":nodes};
    }
}

