/**
 * Created by zhtt on 2016/8/8.
 */
$(function(){
    dataStatisticsTemplate.init();
});
var dataStatisticsTemplate={
    getUrl:ctx+"/template/data-statistics/get/",
    changeStateUrl:ctx+"/template/data-statistics/show/",
    deleteUrl:ctx+"/template/data-statistics/delete/",
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
    $('#updateRootDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        var nodeData=selected.nodes[0];
        $.get(dataStatisticsTemplate.getUrl+nodeData.uuid,function(data){
            data=data.data;
            $('#updateRootDataStatisticsTemplateModal input[name="uuid"]').val(data.uuid);
            $('#updateRootDataStatisticsTemplateModal input[name="name"]').val(data.name);
            $('#updateRootDataStatisticsTemplateModal input[name="colspan"]').val(data.colspan);
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
                if($("#"+modalDiv.id+" input[name='uuid']").val()==""){
                    dataStatisticsTemplateTree.addNode(data.data);
                }else{
                    dataStatisticsTemplateTree.updateNode(data.data);
                }
                modalUtil.close("#"+modalDiv.id);
            }else{
                LobiboxUtil.notify(data.message);
            }
        },
        error:function(data){
            LobiboxUtil.notify("操作失败！");
        }
    });
}
dataStatisticsTemplate.openUpdateForm=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    var data=selected.nodes;
    if(data==null){
        LobiboxUtil.notify("请选择要更新的节点！");
        return;
    }
    if(data[0].type){
        if(data[0].type=="field"){
            $("#openUpdateFieldDataStatisticsTemplateModelBtn").click();
        }else if(data[0].type=="group"){
            $("#openUpdateGroupDataStatisticsTemplateModelBtn").click();
        }else if(data[0].type=="root"){
            $("#openUpdateRootDataStatisticsTemplateModelBtn").click();
        }
    }else{
        LobiboxUtil.notify("节点类型未定义！");
    }

}
dataStatisticsTemplate.delete=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    if(selected.nodes==null){
        LobiboxUtil.notify("请选择要删除的节点！");
        return;
    }
    var data=selected.nodes[0];
    if(data.type!="field"){
        if(typeof data.children!="undefined"&&data.children.length>0){
            LobiboxUtil.notify("请先删除子节点！");
            return ;
        }
    }
    $.get(dataStatisticsTemplate.deleteUrl+data.uuid+"/"+data.parentId,function(data){
        if(data.status=="success"){
            LobiboxUtil.notify("删除成功！");
            dataStatisticsTemplateTree.removeNode(data.data);
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
dataStatisticsTemplate.showNode=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    if(selected.nodes==null){
        LobiboxUtil.notify("请选择要修改的节点！");
        return;
    }
    var data=selected.nodes[0];
    $.get(dataStatisticsTemplate.changeStateUrl+data.uuid+"/true",function(data){
        if(data.status=="success"){
            LobiboxUtil.notify("修改成功！");
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
dataStatisticsTemplate.hideNode=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    if(selected.nodes==null){
        LobiboxUtil.notify("请选择要修改的节点！");
        return;
    }
    var data=selected.nodes[0];
    $.get(dataStatisticsTemplate.changeStateUrl+data.uuid+"/false",function(data){
        if(data.status=="success"){
            LobiboxUtil.notify("修改成功！");
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
