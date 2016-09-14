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
    $('#createGroupDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        $('#createGroupDataStatisticsTemplateModal input[name="parentId"]').val(selected.nodes[0].uuid);
    });
    $('#createFieldDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        $('#createGroupDataStatisticsTemplateModal input[name="parentId"]').val(selected.nodes[0].uuid);
    });
}
dataStatisticsTemplate.openForm=function(flag){
    if(flag){
        if(dataStatisticsTemplate[flag]!=dataStatisticsTemplate.createRootModalId){
            if(dataStatisticsTemplate.tree==null){
                LobiboxUtil.notify("模板树未创建！");
                dataStatisticsTemplate.openForm('createRootModalId');
                return;
            }
            var selected=dataStatisticsTemplate.tree.getSelectedNodes();
            if(selected.zTree==null){
                LobiboxUtil.notify("模板树未创建！");
                dataStatisticsTemplate.openForm('createRootModalId');
                return;
            }else if(selected.nodes==null){
                LobiboxUtil.notify("请选择父节点！");
                return;
            }
        }
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
