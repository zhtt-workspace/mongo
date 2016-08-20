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

