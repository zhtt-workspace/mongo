/**
 * Created by zhtt on 2016/8/6.
 */
$(function(){
    user.initTable();
});
var user={
    createModalId:"createUserModal",
    updateModalId:"updateUserModal",
    userListTableId:"userListTable",
    userListUrl:ctx+'/user/query',
    deleteUrl:ctx+'/user/delete',
    createUrl:ctx+"/user/create-form",
    updateUrl:ctx+"/user/update-form"
};
user.closeCreateModal=function(){
    $("#"+user.createModalId).modal("hide");
}
user.closeUpdateModal=function(){
    $("#"+user.updateModalId).modal("hide");
}
user.submitCreateModal=function(){
    user.submitCreateForm({
        form:$("#createUserForm"),
        url:user.createUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("发送成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
    user.closeCreateModal();
}
user.submitCreateForm=function(obj){
    if(obj.form.valid){
        if(!obj.form.valid()){return;}
    }
    $.ajax({
        cache: true,
        type: "POST",
        url:obj.url||obj.form.attr("action"),
        data:obj.form.serialize(),// 你的formid
        async: false,
        error: function(request) {
            if(obj.error){
                obj.error(data);
            }else{
                LobiboxUtil.notify("连接失败");
            }
        },
        success: function(data) {
            if(obj.success){
                obj.success(data);
            }else{
                LobiboxUtil.notify("操作成功");
            }
        }
    });
}
/**
 * 更新用户
 */
user.update=function(){
    var selects =$('#'+user.userListTableId).bootstrapTable('getSelections');
    if(selects.length==0){
        LobiboxUtil.notify("请勾选要修改的用户！");
        return;
    }else if(selects.length==1){
        $("#openUpdateUserModelBtn").click();
    }else{
        LobiboxUtil.notify("默认修改第一条记录！");
        $("#openUpdateUserModelBtn").click();
    }
    setTimeout(function(){
        var data=selects[0];
        var form=$("form[id='updateUserForm']");
        form.find("input[name='name']").val(data.name);
        form.find("input[name='username']").val(data.username);
        form.find("input[name='password']").val(data.password);
        form.find("input[name='age']").val(data.age);
        form.find("input[name='orgId']").val(data.orgId);
    },300)
    //var newSelects = $.parseJSON(JSON.stringify(selects));
}
user.submitUpdateModal=function(){
    user.submitCreateForm({
        form:$("#updateUserForm"),
        url:user.updateUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("发送成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
    user.closeUpdateModal();
}
/**
 * 删除用户
 */
user.delete=function(){
    var selects =$('#'+user.userListTableId).bootstrapTable('getSelections');
    if(selects.length==0){
        LobiboxUtil.notify("请勾选要删除的用户！");
        return;
    }else{
        LobiboxUtil.confirm({msg:"确定要删除吗？",fn:function() {
            $.get(user.deleteUrl,{uuid:selects[0].uuid}, function (data) {
                if(data.status=="success"){
                    LobiboxUtil.notify("删除成功！");
                }else{
                    LobiboxUtil.notify("删除失败！"+data.message);
                }
            });
        }});
    }
    //var newSelects = $.parseJSON(JSON.stringify(selects));
}
user.initTable=function() {
    var queryUrl = user.userListUrl;
    $table = $('#'+user.userListTableId).bootstrapTable({
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        url: queryUrl,
        height: $(window).height() - 250,
        striped: true,
        pagination: true,
        singleSelect: false,
        pageSize: 2,
        pageList: [4,10, 50, 100, 200, 500],
        search: false, //不显示 搜索框
        showColumns: false, //不显示下拉框（选择显示的列）
        sidePagination: "server", //服务端请求
        queryParams: user.queryParams,
        minimunCountColumns: 2,
        columns: [{
            field: 'state',
            checkbox: true
            }, {
                field: 'name',title: '姓名',width: 100,align: 'center',valign: 'middle',sortable: true
            },  {
                field: 'username',title: '用户名',width: 80,align: 'middle',valign: 'top',sortable: true
            }, {
                field: 'createTime',title: '录入时间',width: 180,align: 'left',valign: 'top',sortable: true
            }, {
                field: 'orgId',title: '所属机构',width: 100,align: 'left',valign: 'top',sortable: true
            }, {
                field: 'operate',title: '操作',width: 100,align: 'center',valign: 'middle'
            }],
        onLoadSuccess:function(){},
        onLoadError: function () {}
    });
}
//传递的参数
user.queryParams=function(params) {
    return {
        offset: typeof params=="undefined"?0:params.offset,
        limit: typeof params=="undefined"?2:params.limit,
        name: $("#query-user-name").val()==""?".":$("#query-user-name").val(),
        username: $("#query-user-username").val()==""?".":$("#query-user-username").val(),
        startdate: $("#query-user-startdate").val(),
        endDate: $("#query-user-endDate").val(),
        order: typeof params=="undefined"?"asc":params.sortOrder
    };
}
user.query=function(){
    $('#'+user.userListTableId).bootstrapTable('refresh',user.queryParams());
}