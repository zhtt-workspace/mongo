/**
 * Created by zhtt on 2016/8/13.
 * code处理、order处理有问题
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
/**
 * 打开新建机构节点的对话框，并对表单初始化信息
 */
organizationTree.renderFormBySeectedNode=function(isLoop){
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
        organizationTree.disableOrgType(obj);
        var form=$("#"+organization.createFormId);
        if(obj.orgType!="root"){
            var fullNameNode=form.find("input[name='fullName']");
            if(fullNameNode.length==0){
                organizationTree.renderFormBySeectedNode(false);
            }else{
                fullNameNode[0].defaultValue=obj.fullName;
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
organizationTree.disableOrgType=function(node){
    $("#"+organization.createFormId+" input[name='orgType']").attr("disabled",true).removeAttr("checked");
    if(node==null){
        $("#"+organization.createFormId+" input[value='root']").removeAttr("disabled").attr("checked",true);
    }else{
        switch (node.orgType){
            case "root":$("#"+organization.createFormId+" input[value='org']").removeAttr("disabled").attr("checked",true);$("#"+organization.createFormId+" input[value='headquarters']").removeAttr("disabled");break;
            case "org":$("#"+organization.createFormId+" input[value='org']").removeAttr("disabled").attr("checked",true);$("#"+organization.createFormId+" input[value='headquarters']").removeAttr("disabled");break;
            case "headquarters":$("#"+organization.createFormId+" input[value='dept']").removeAttr("disabled").attr("checked",true);break;
            case "dept":$("#"+organization.createFormId+" input[value='dept']").removeAttr("disabled").attr("checked",true);break;
            default :LobiboxUtil.notify("未类型："+node.orgType);break;
        }
    }
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

