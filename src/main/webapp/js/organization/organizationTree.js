/**
 * Created by zhtt on 2016/8/13.
 */
var organizationTree={}
organizationTree.init=function(){
    $.get(organization.treeUrl,function(data){
        if(typeof data=="object"&&data.length>0){
            $.fn.zTree.init($("#"+organization.treeId), organizationTree.setting);
        }else{
            if(data.length==0){
                $("#openCreateOrganizationModelBtn").click();
                setTimeout(function(){
                    $("#"+organization.createModalId+" .panel-heading").html("请初始化机构树根节点！");
                },100);
            }else{
                LobiboxUtil.notify("加载树失败！");
            }
        }
    });
}
organizationTree.setting={
    view: {
        selectedMulti: false
    },
    async: {
        enable: true,
        url:organization.treeUrl,
        autoParam:["uuid"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    callback: {
        onClick: organizationTreeOnclick,
        onAsyncSuccess: onAsyncSuccess
    }
}
/**
 * 打开新建机构节点的对话框，并对表单初始化信息
 */
organizationTree.renderFormBySeectedNode=function(){
    var zTree = $.fn.zTree.getZTreeObj(organization.treeId),
    nodes = zTree.getSelectedNodes();
    if(nodes.length==1){
        var obj=nodes[0];
        var form=$("#"+organization.createFormId);
        form.find("input[name='fullName']").val(obj.name);
    }else{
        modalUtil.close('#'+organization.createModalId)
        LobiboxUtil.notify("添加子节点时，请选择一个父节点！");
    }
}
function organizationTreeOnclick(event, treeId, treeNode){

}





















var setting = {
    view: {
        selectedMulti: false
    },
    async: {
        enable: true,
        url:organization.treeUrl,
        autoParam:["uuid"],
        otherParam:{"otherParam":"zTreeAsyncTest"},
        dataFilter: filter
    },
    callback: {
        onClick: organizationTreeOnclick,
        beforeAsync: beforeAsync,
        onAsyncError: onAsyncError,
        onAsyncSuccess: onAsyncSuccess
    }
};

function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i=0, l=childNodes.length; i<l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}
var log, className = "dark";
function beforeAsync(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
    return true;
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    showLog("[ "+getTime()+" onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
    showLog("[ "+getTime()+" onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
}

function showLog(str) {
    if (!log) log = $("#log");
    //log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 8) {
        log.get(0).removeChild(log.children("li")[0]);
    }
}
function getTime() {
    var now= new Date(),
        h=now.getHours(),
        m=now.getMinutes(),
        s=now.getSeconds(),
        ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
}

function refreshNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("orgTreeDiv"),
        type = e.data.type,
        silent = e.data.silent,
        nodes = zTree.getSelectedNodes();

    for (var i=0, l=nodes.length; i<l; i++) {
        zTree.reAsyncChildNodes(nodes[i], type, silent);
        if (!silent) zTree.selectNode(nodes[i]);
    }
}

$(document).ready(function(){

    /*$("#refreshNode").bind("click", {type:"refresh", silent:false}, refreshNode);
    $("#refreshNodeSilent").bind("click", {type:"refresh", silent:true}, refreshNode);
    $("#addNode").bind("click", {type:"add", silent:false}, refreshNode);
    $("#addNodeSilent").bind("click", {type:"add", silent:true}, refreshNode);*/
});