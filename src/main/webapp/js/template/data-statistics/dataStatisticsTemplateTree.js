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
                zNodes:[data.data]
            });
            dataStatisticsTemplate.tree.init();
            dataStatisticsTemplate.tree.getSelectedNodes().zTree.expandAll(true);
        }else{
            dataStatisticsTemplate.openForm('createRootModalId');
            LobiboxUtil.notify(data.message);
        }
    });
}