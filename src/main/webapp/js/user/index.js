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
user.submitCreateModal=function(){
    $("#"+user.createModalId+" from").validate({
        rules: {
            create_user_confirm_password: {
                required: true,
                minlength: 6,
                equalTo: "#create-user-password"
            }
        },
        messages: {
            create_user_confirm_password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                equalTo: "Please enter the same password as above"
            }
        }
    });
    var status=user.submitForm({
        form:$("#createUserForm"),
        url:user.createUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("保存成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
    if(status){
        modalUtil.close("#"+user.createModalId);
    }
}
user.submitForm=function(obj){
    if(obj.form.valid){
        if(!obj.form.valid()){return false;}
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
    return true;
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
    user.submitForm({
        form:$("#updateUserForm"),
        url:user.updateUrl,
        success:function(data){
            if(data.status=="success"){
                LobiboxUtil.notify("保存成功！");
            }else{
                LobiboxUtil.notify(data.message);
            }
        }
    });
    modalUtil.close("#"+user.createModalId);
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
        var uuid=[];
        for(var i=0;i<selects.length;i++){
            uuid.push(selects[i].uuid);
        }
        LobiboxUtil.confirm({msg:"确定要删除吗？",fn:function() {
            $.ajax({
                url: user.deleteUrl,
                type: 'POST',
                data: JSON.stringify(uuid),
                contentType: "application/json",
                dataType: 'json',
                success: function (data) {
                    if(data.status=="success"){
                        LobiboxUtil.notify("删除成功！");
                    }else{
                        LobiboxUtil.notify("删除失败！"+data.message);
                    }
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
        height: $(window).height() - 220,
        striped: true,
        pagination: true,
        singleSelect: false,
        pageSize: 2,
        pageList: [2,10, 50, 100, 200, 500],
        search: false, //不显示 搜索框
        showColumns: false, //不显示下拉框（选择显示的列）
        sidePagination: "server", //服务端请求
        queryParams: user.queryParams,
        minimunCountColumns: 2,
        columns: [{
            field: 'state',
            checkbox: true
            }, {
                field: 'name',title: '姓名',width: 100,align: 'center',valign: 'middle',sortable: true,formatter:function(value, row, index){return '<i class="glyphicon glyphicon-user"></i> '+value;}
            },  {
                field: 'username',title: '用户名',width: 80,align: 'middle',valign: 'top',sortable: true
            }, {
                field: 'createTime',title: '录入时间',width: 180,align: 'left',valign: 'top',sortable: true,formatter:function(value, row, index){return '<i class="glyphicon glyphicon-time"></i> '+value;}
            }, {
                field: 'orgId',title: '所属机构',width: 100,align: 'left',valign: 'top',sortable: true
            }, {
                field: 'operate', title: '操作',width: 100, align: 'center',formatter: user.operateformater
        }],
        onLoadSuccess:function(){},
        onLoadError: function () {}
    });
}
/**
 * 对操作列进行格式化
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
user.operateformater=function(value, row, index){
    return [
        '<a class="digital" href="javascript:void(0)" title="详情">',
        '<i class="glyphicon glyphicon-list-alt"></i>',
        '</a>  ',
        '<a class="like" href="javascript:void(0)" title="修改">',
        '<i class="glyphicon glyphicon-edit"></i>',
        '</a>  ',
        '<a class="remove" href="javascript:void(0)" title="删除">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>'
    ].join('');
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