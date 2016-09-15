/**
 * Created by zhtt on 2016/8/8.
 */
$(function(){
    dataStatisticsTemplate.init();
});
var dataStatisticsTemplate={
    getUrl:ctx+"/template/data-statistics/get/",
    tableUrl:ctx+"/template/data-statistics/table?table=false",
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
    dataStatisticsTemplateTable.init();
    dataStatisticsTemplate.initEvent();
}
dataStatisticsTemplate.initEvent=function(){
    $('#createGroupDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        $('#createGroupDataStatisticsTemplateModal input[name="parentId"]').val(selected.nodes[0].uuid);
    });
    $('#createFieldDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        $('#createFieldDataStatisticsTemplateModal input[name="parentId"]').val(selected.nodes[0].uuid);
    });
    $('#updateGroupDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        var nodeData=selected.nodes[0];
        $.get(dataStatisticsTemplate.getUrl+nodeData.uuid,function(data){
            data=data.data;
            $('#updateGroupDataStatisticsTemplateModal input[name="parentId"]').val(data.parentId);
            $('#updateGroupDataStatisticsTemplateModal input[name="uuid"]').val(data.uuid);
            $('#updateGroupDataStatisticsTemplateModal input[name="name"]').val(data.name);
            $('#updateGroupDataStatisticsTemplateModal input[name="colspan"]').val(data.colspan);
        });
    });
    $('#updateFieldDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        var nodeData=selected.nodes[0];
        $.get(dataStatisticsTemplate.getUrl+nodeData.uuid,function(data){
            data=data.data;
            $('#updateFieldDataStatisticsTemplateModal input[name="parentId"]').val(data.parentId);
            $('#updateFieldDataStatisticsTemplateModal input[name="uuid"]').val(data.uuid);
            $('#updateFieldDataStatisticsTemplateModal input[name="name"]').val(data.name);
            $('#updateFieldDataStatisticsTemplateModal input[name="colspan"]').val(data.colspan);
            $('#updateFieldDataStatisticsTemplateModal input[name="unit"]').val(data.unit);
            $('#updateFieldDataStatisticsTemplateModal input[name="decimalDigits"]').val(data.decimalDigits);
            $('#updateFieldDataStatisticsTemplateModal input[name="maxNumber"]').val(data.maxNumber);
            $('#updateFieldDataStatisticsTemplateModal input[name="minNumber"]').val(data.minNumber);
            $('#updateFieldDataStatisticsTemplateModal input[name="beyondRemind"]').val(data.beyondRemind);
        });
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
            $("#"+dataStatisticsTemplate[flag]).click();
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
                dataStatisticsTemplateTree.addNodeByParentId(data.data);
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
}
dataStatisticsTemplate.openUpdateForm=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    var data=selected.nodes[0];
    if(data.type=="field"){
        $("#openUpdateFieldDataStatisticsTemplateModelBtn").click();
    }else if(data.type=="group"){
        $("#openUpdateGroupDataStatisticsTemplateModelBtn").click();
    }
}
dataStatisticsTemplate.delete=function(){

}
