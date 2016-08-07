/**
 * Created by zhtt on 2016/8/6.
 */
var user={
    createModalId:"createUserModal"
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
function initTable() {
    var queryUrl = '@Url.Content("~/Welcome/QueryList")' + '?rnd=' + +Math.random();
    $table = $('.user-table-list').bootstrapTable({
        method: 'post',
        contentType: "application/x-www-form-urlencoded",//必须的，操你大爷！！！！
        url: queryUrl,
        height: $(window).height() - 200,
        striped: true,
        pagination: true,
        singleSelect: false,
        pageSize: 50,
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
        field: 'Name',
        title: '姓名',
        width: 100,
        align: 'center',
        valign: 'middle',
        sortable: true
    }, {
        field: 'Gender',
        title: '性别',
        width: 40,
        align: 'left',
        valign: 'top',
        sortable: true
    }, {
        field: 'Birthday',
        title: '出生日期',
        width: 80,
        align: 'left',
        valign: 'top',
        sortable: true
    }, {
        field: 'CtfId',
        title: '身份证',
        width: 80,
        align: 'middle',
        valign: 'top',
        sortable: true
    }, {
            field: 'Address',        title: '地址',        width: 180,        align: 'left',        valign: 'top',        sortable: true
    }, {
        field: 'Tel',        title: '固定电话',        width: 100,        align: 'left',        valign: 'top',        sortable: true
    }, {        field: 'Mobile',        title: '手机号码',        width: 100,        align: 'left',        valign: 'top',        sortable: true
    }, {        field: 'operate',        title: '操作',        width: 100,        align: 'center',        valign: 'middle'
    }],
        onLoadSuccess:function(){    },
        onLoadError: function () {

        }
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