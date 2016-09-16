/**
 * Created by zhtt on 2016/8/8.
 */
var dataStatisticsTemplateTree={}
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
    if(treeNode.show==true){
        $('a:contains("显示")').addClass("disabled");
        $('a:contains("隐藏")').removeClass("disabled");
    }else{
        $('a:contains("显示")').removeClass("disabled");
        $('a:contains("隐藏")').addClass("disabled");
    }
    if(treeNode.getPreNode()==null){
        $('a:contains("上移")').addClass("disabled");
    }else{
        $('a:contains("上移")').removeClass("disabled");
    }
    if(treeNode.getNextNode()==null){
        $('a:contains("下移")').addClass("disabled");
    }else{
        $('a:contains("下移")').removeClass("disabled");
    }
}
/**
 * 节点向上移动
 */
dataStatisticsTemplateTree.moveUp=function(data){
    var tree=dataStatisticsTemplate.tree.getSelectedNodes().zTree;
    if(tree){
        var node=tree.getNodeByParam("uuid",data.uuid);
        if(node.getPreNode()){
            tree.moveNode(node.getPreNode(),node,"prev");
        }
    }
}
/**
 * 节点向上移动
 */
dataStatisticsTemplateTree.moveDown=function(data){
    var tree=dataStatisticsTemplate.tree.getSelectedNodes().zTree;
    if(tree){
        var node=tree.getNodeByParam("uuid",data.uuid);
        if(node.getNextNode()){
            tree.moveNode(node,node.getNextNode(),"prev");
        }
    }
}
/**
 * 新建成功后，向树节点中添加节点
 * @param data
 dataStatisticsTemplateTree.addNode=function(data){
    if(dataStatisticsTemplate.tree==null){
        dataStatisticsTemplate.init();
    }else{
        var tree=dataStatisticsTemplate.tree.getSelectedNodes().zTree;
        if(tree){
            var parentNode=tree.getNodeByParam("uuid",data.parentId);
            tree.addNodes(parentNode, data);
        }else{
            dataStatisticsTemplateTree.init();
        }
    }
}
 */
/**
 * 新建成功后，向树节点中添加节点
 * @param data
 dataStatisticsTemplateTree.updateNode=function(data){
    var tree=dataStatisticsTemplate.tree.getSelectedNodes().zTree;
    if(tree){
        var node=tree.getNodeByParam("uuid",data.uuid);
        node.name=data.name;
        tree.updateNode(node);
    }
}
 */
/**
 * 新建成功后，向树节点中添加节点
 * @param data
 dataStatisticsTemplateTree.removeNode=function(data){
    var tree=dataStatisticsTemplate.tree.getSelectedNodes().zTree;
    if(tree){
        var node=tree.getNodeByParam("uuid",data.uuid);
        node.name=data.name;
        tree.removeNode(node);
    }
}
 */