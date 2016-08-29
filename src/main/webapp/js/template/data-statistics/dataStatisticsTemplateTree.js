/**
 * Created by zhtt on 2016/8/8.
 */
$(function(){
    dataStatisticsTemplateTree.init();
});
var dataStatisticsTemplateTree={
    treeUrl:ctx+"/organization/tree?parentId=",
    treeId:"dataStatisticsTemplateTreeDiv1",
    tree:null
}
dataStatisticsTemplateTree.init=function(){
    dataStatisticsTemplateTree.tree=new zTreeUtil({
        treeDivId:dataStatisticsTemplateTree.treeId,
        inputId:"dataStatisticsTemplateTreeInput",
        url:dataStatisticsTemplateTree.treeUrl,
        ajaxLoad:true
    });
    //dataStatisticsTemplateTree.tree.comboTree(dataStatisticsTemplateTree.tree);
}
