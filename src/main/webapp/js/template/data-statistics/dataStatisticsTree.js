/**
 * Created by zhtt on 2016/8/8.
 */
var dataStatisticsTree={
    treeUrl:ctx+"/organization/tree?parentId=",
    treeId:"dataStatisticsTreeDiv1",
    tree:null
}

$(document).ready(function(){
    dataStatisticsTree.tree=new zTreeUtil({
        treeDivId:dataStatisticsTree.treeId,
        inputId:"dataStatisticsTreeInput",
        url:dataStatisticsTree.treeUrl,
        ajaxLoad:true
    });
    dataStatisticsTree.tree.comboTree(dataStatisticsTree.tree);
});