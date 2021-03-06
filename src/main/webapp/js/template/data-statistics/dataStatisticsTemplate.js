/**
 * Created by zhtt on 2016/8/8.
 */
$(function(){
    dataStatisticsTemplate.init();
});
var dataStatisticsTemplate={
    getUrl:ctx+"/template/data-statistics/get/",
    moveUrl:ctx+"/template/data-statistics/move/",
    changeStateUrl:ctx+"/template/data-statistics/show/",
    deleteUrl:ctx+"/template/data-statistics/delete/",
    tableUrl:ctx+"/template/data-statistics/table?table=false",
    treeUrl:ctx+"/template/data-statistics/tree?tree=false",
    treeId:"dataStatisticsTemplateTreeDiv",
    createRootModalId:"openCreateRootDataStatisticsTemplateModelBtn",
    createGroupModalId:"openCreateGroupDataStatisticsTemplateModelBtn",
    createFieldModalId:"openCreateFieldDataStatisticsTemplateModelBtn",
    createUrl:ctx+"/template/data-statistics/form",
    cloneTreeUrl:ctx+"/template/data-statistics/clone-tree",
    tree:null
};
dataStatisticsTemplate.init=function(){
    dataStatisticsTemplateTree.init();
    dataStatisticsTemplateTable.init();
    dataStatisticsTemplate.initEvent();
}
dataStatisticsTemplate.initEvent=function(){
    $('#createGroupDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        $("#updateRootDataStatisticsTemplateModal .crete-close-btn").show();
        $("#updateRootDataStatisticsTemplateModal .update-close-btn").hide();
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        $('#createGroupDataStatisticsTemplateModal input[name="parentId"]').val(selected.nodes[0].uuid);
        $("#createGroupDataStatisticsTemplateModal form").validate(); $("#createGroupDataStatisticsTemplateModal form").valid();
    });
    $('#createFieldDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        $("#updateRootDataStatisticsTemplateModal .crete-close-btn").show();
        $("#updateRootDataStatisticsTemplateModal .update-close-btn").hide();
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        $('#createFieldDataStatisticsTemplateModal input[name="parentId"]').val(selected.nodes[0].uuid);
        $("#createFieldDataStatisticsTemplateModal form").validate(); $("#createFieldDataStatisticsTemplateModal form").valid();
    });
    $('#updateGroupDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        $("#updateRootDataStatisticsTemplateModal .crete-close-btn").hide();
        $("#updateRootDataStatisticsTemplateModal .update-close-btn").removeClass("hide").show();
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        var nodeData=selected.nodes[0];
        $.get(dataStatisticsTemplate.getUrl+nodeData.uuid,function(data){
            data=data.data;
            $('#updateGroupDataStatisticsTemplateModal input[name="parentId"]').val(data.parentId);
            $('#updateGroupDataStatisticsTemplateModal input[name="uuid"]').val(data.uuid);
            $('#updateGroupDataStatisticsTemplateModal input[name="name"]').val(data.name);
            $('#updateGroupDataStatisticsTemplateModal input[name="colspan"]').val(data.colspan);
        });
        $("#updateGroupDataStatisticsTemplateModal form").validate(); $("#updateGroupDataStatisticsTemplateModal form").valid();
    });
    $('#updateFieldDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        $("#updateRootDataStatisticsTemplateModal .crete-close-btn").hide();
        $("#updateRootDataStatisticsTemplateModal .update-close-btn").removeClass("hide").show();
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
        $("#updateFieldDataStatisticsTemplateModal form").validate(); $("#updateFieldDataStatisticsTemplateModal form").valid();
    });
    $('#updateRootDataStatisticsTemplateModal').on('shown.bs.modal', function () {
        $("#updateRootDataStatisticsTemplateModal .crete-close-btn").hide();
        $("#updateRootDataStatisticsTemplateModal .update-close-btn").removeClass("hide").show();
        var selected=dataStatisticsTemplate.tree.getSelectedNodes();
        var nodeData=selected.nodes[0];
        $.get(dataStatisticsTemplate.getUrl+nodeData.uuid,function(data){
            data=data.data;
            $('#updateRootDataStatisticsTemplateModal input[name="uuid"]').val(data.uuid);
            $('#updateRootDataStatisticsTemplateModal input[name="name"]').val(data.name);
            $('#updateRootDataStatisticsTemplateModal input[name="colspan"]').val(data.colspan);
        });
        $("#updateRootDataStatisticsTemplateModal form").validate(); $("#updateRootDataStatisticsTemplateModal form").valid();
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
    ajaxUtil.submit({
        form:$("#"+modalDiv.id+" form"),
        success:function(data){
                LobiboxUtil.notify("保存成功！");
                if($("#"+modalDiv.id+" input[name='uuid']").val()==""){
                    dataStatisticsTemplate.tree.addNode(data);
                }else{
                    dataStatisticsTemplate.tree.updateNode(data);
                }
                modalUtil.close("#"+modalDiv.id);
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
    LobiboxUtil.confirm({msg:"确定要删除吗？",fn:function() {
        $.get(dataStatisticsTemplate.deleteUrl+data.uuid+"/"+data.parentId,function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("删除成功！");
                //dataStatisticsTemplateTree.removeNode(data.data);
                dataStatisticsTemplate.tree.removeNode(data.data);
            }else{
                LobiboxUtil.notify(data.message);
            }
        });
    }});
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
dataStatisticsTemplate.moveUp=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    if(selected.nodes==null){
        LobiboxUtil.notify("请选择要修改的节点！");
        return;
    }
    var data=selected.nodes[0];
    $.get(dataStatisticsTemplate.moveUrl+data.uuid+"/-1",function(data){
        if(data.status=="success"){
            LobiboxUtil.notify("修改成功！");
            dataStatisticsTemplateTree.moveUp(data.data);
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
dataStatisticsTemplate.moveDown=function(){
    var selected=dataStatisticsTemplate.tree.getSelectedNodes();
    if(selected.nodes==null){
        LobiboxUtil.notify("请选择要修改的节点！");
        return;
    }
    var data=selected.nodes[0];
    $.get(dataStatisticsTemplate.moveUrl+data.uuid+"/1",function(data){
        if(data.status=="success"){
            LobiboxUtil.notify("修改成功！");
            dataStatisticsTemplateTree.moveDown(data.data);
        }else{
            LobiboxUtil.notify(data.message);
        }
    });
}
