/**
 * Created by zhtt on 2016/8/8.
 */
$(function(){
    dataStatisticsTemplate.init();
});
var dataStatisticsTemplate={
    treeUrl:ctx+"/template/data-statistics/tree?tree=false",
    treeId:"dataStatisticsTemplateTreeDiv",
    createRootModalId:"openCreateRootDataStatisticsTemplateModelBtn",
    createGroupModalId:"openCreateGroupDataStatisticsTemplateModelBtn",
    createFieldModalId:"openCreateFieldDataStatisticsTemplateModelBtn",
    createUrl:ctx+"/template/data-statistics/form",
    tree:null
};
dataStatisticsTemplate.init=function(){
    dataStatisticsTemplateTree.init();
    dataStatisticsTemplate.initEvent();
}
dataStatisticsTemplate.initEvent=function(){
    $('#'+dataStatisticsTemplate.createRootModalId).on('shown.bs.modal', function () {

    });
}
dataStatisticsTemplate.openForm=function(flag){
    if(flag){
        if(dataStatisticsTemplate[flag]){
            $("#"+dataStatisticsTemplate[flag]).click()
        }else{
            LobiboxUtil.notify("打开窗口无效");
        }
    }else{
        LobiboxUtil.notify("打开窗口无效");
    }
}
dataStatisticsTemplate.submitCreateForm=function(obj){
    var modalDiv=obj.parentNode.parentNode.parentNode.parentNode.parentNode;
    formUtil.submit({
        form:$("#"+modalDiv.id+" form"),
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("保存成功！");
                modalUtil.close("#"+modalDiv.id);
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
}
dataStatisticsTemplate.openUpdateForm=function(){
    $("#openUpdateDataStatisticsTemplateModelBtn").click();
}
dataStatisticsTemplate.delete=function(){

}
