/**
 * Created by zhtt on 2016/8/8.
 */
$(function(){
    dataStatisticsTemplate.init();
});
var dataStatisticsTemplate={
    treeUrl:ctx+"/template/data-statistics/tree?tree=false",
    treeId:"dataStatisticsTemplateTreeDiv",
    tree:null
};
dataStatisticsTemplate.init=function(){
    dataStatisticsTemplateTree.init();
    dataStatisticsTemplate.initEvent();
}
dataStatisticsTemplate.initEvent=function(){
    $('#'+dataStatisticsTemplate.createModalId).on('shown.bs.modal', function () {

    });
}
dataStatisticsTemplate.openCreateForm=function(){
    $("#openCreateDataStatisticsTemplateModelBtn").click();
}
dataStatisticsTemplate.submitCreateForm=function(){

}
dataStatisticsTemplate.openUpdateForm=function(){
    $("#openUpdateDataStatisticsTemplateModelBtn").click();
}
dataStatisticsTemplate.delete=function(){

}
