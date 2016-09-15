/**
 * Created by zhtt on 2016/8/8.
 */
var dataStatisticsTemplateTree={
}
dataStatisticsTemplateTree.init=function(){
    $.get(dataStatisticsTemplate.treeUrl,function(data){
        if(data.status=="success"){
            dataStatisticsTemplate.tree=new zTreeUtil({
                treeDivId:dataStatisticsTemplate.treeId,
                zNodes:[data.data],
                onClick:dataStatisticsTemplateTree.onClick
            });
            dataStatisticsTemplate.tree.init();
            dataStatisticsTemplate.tree.getSelectedNodes().zTree.expandAll(true);
        }else{
            dataStatisticsTemplate.openForm('createRootModalId');
            LobiboxUtil.notify(data.message);
        }
    });
}
/**
 * 新建成功后，向树节点中添加节点
 * @param data
 */
dataStatisticsTemplateTree.addNodeByParentId=function(data){
    var tree=dataStatisticsTemplate.tree.getSelectedNodes().zTree;
    if(tree){
        var parentNode=tree.getNodeByParam("uuid",data.parentId);
        tree.addNodes(parentNode, data);
    }
}
/**
 * 点击节点时，做一些验证等操作
 * @param event
 * @param treeId
 * @param treeNode
 */
dataStatisticsTemplateTree.onClick=function(event, treeId, treeNode){
    if(treeNode.type=="field"){
        $('a:contains("新增")').addClass("disabled");
    }else{
        $('a:contains("新增")').removeClass("disabled");
    }
}