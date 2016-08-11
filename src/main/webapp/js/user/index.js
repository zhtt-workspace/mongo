/**
 * Created by zhtt on 2016/8/6.
 */
$(function(){
    user.initTable();
});
var user={
    createModalId:"createUserModal",
    userListTableId:"userListTable"
};
user.closeCreateModal=function(){
    $("#"+user.createModalId).modal("hide");
}
user.submitCreateModal=function(){
    user.submitForm({
        form:$("#createUserForm"),
        url:ctx+"/user/create-form",
        success:function(data){
            if(data.status=="success"){
                alert("发送成功！");
            }else{
                alert(data.message);
            }
        }
    });
    user.closeCreateModal();
}
user.submitForm=function(obj){
    if(obj.form.valid){
        if(!obj.form.valid()){
            return;
        }
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
                alert("连接失败");
            }
        },
        success: function(data) {
            if(obj.success){
                obj.success(data);
            }else{
                alert("操作成功");
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
    }
    var newSelects = $.parseJSON(JSON.stringify(selects));
}
/**
 * 删除用户
 */
user.delete=function(){
    var selects =$('#'+user.userListTableId).bootstrapTable('getSelections');
    if(selects.length==0){
        LobiboxUtil.notify("请勾选要删除的用户！");
        return;
    }
    var newSelects = $.parseJSON(JSON.stringify(selects));
}
user.initTable=function() {
    var queryUrl = 'http://localhost:8080/sm/user/query?name=an&username=username&limit=2&skip=0';
    $table = $('#'+user.userListTableId).bootstrapTable({
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        url: queryUrl,
        height: $(window).height() - 250,
        striped: true,
        pagination: true,
        singleSelect: false,
        pageSize: 2,
        pageList: [10, 50, 100, 200, 500],
        search: false, //不显示 搜索框
        showColumns: false, //不显示下拉框（选择显示的列）
        sidePagination: "server", //服务端请求
        queryParams: queryParams,
        minimunCountColumns: 2,
        columns: [{
            field: 'state',
            checkbox: true
            }, {
                field: 'name',        title: '姓名',        width: 100,        align: 'center',        valign: 'middle',        sortable: true
            }, {
                field: 'username',        title: '用户名',        width: 40,        align: 'left',        valign: 'top',        sortable: true
            }, {
                field: 'password',        title: '密码',        width: 80,        align: 'left',        valign: 'top',        sortable: true
            }, {
                field: 'age',        title: '年龄',        width: 80,        align: 'middle',        valign: 'top',        sortable: true
            }, {
                    field: 'createTime',        title: '录入时间',        width: 180,        align: 'left',        valign: 'top',        sortable: true
            }, {
                field: 'orgId',        title: '所属机构',        width: 100,        align: 'left',        valign: 'top',        sortable: true
            }, {        field: 'operate',        title: '操作',        width: 100,        align: 'center',        valign: 'middle'
            }],
        onLoadSuccess:function(){},
        onLoadError: function () {}
    });
}
//传递的参数
function queryParams(params) {
    return {
        pageSize: params.pageSize,
        pageIndex: params.pageNumber,
        UserName: $("#txtName").val(),
        Birthday: $("#txtBirthday").val(),
        Gender: $("#Gender").val(),
        Address: $("#txtAddress").val(),
        name: params.sortName,
        order: params.sortOrder
    };
}