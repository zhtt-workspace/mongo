/**
 * Created by zhtt on 2016/8/8.
 */
var dataStatisticsTemplateTree={
}
dataStatisticsTemplateTree.init=function(){
    dataStatisticsTemplate.tree=new zTreeUtil({
        treeDivId:dataStatisticsTemplate.treeId,
        url:dataStatisticsTemplate.treeUrl,
        ajaxLoad:true
    });
    $.get(dataStatisticsTemplate.treeUrl,function(data){
        if(data.status=="success"){

        }else{
            dataStatisticsTemplate.openForm('createRootModalId');
            LobiboxUtil.notify(data.message);
        }
    })
    //dataStatisticsTemplateTree.tree.comboTree();
}
