<%--
  Created by IntelliJ IDEA.
  User: zhtt
  Date: 2016/8/6
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<div class="panel panel-default">
  <div class="panel-heading">新建用户</div>
  <div class="panel-body">
    <form class="form-horizontal" id="createUserForm" action="${ctx}/user/create-form">
      <div class="form-group">
        <label for="create-user-name" class="col-sm-2 control-label">姓名</label>
        <div class="col-sm-10">
          <input name="name" type="text" class="form-control" id="create-user-name" placeholder="姓名">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-username" class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-10">
          <input name="username" type="text" class="form-control" id="create-user-username" placeholder="用户名">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-password" class="col-sm-2 control-label">密码</label>
        <div class="col-sm-10">
          <input name="password" type="password" class="form-control" id="create-user-password" placeholder="密码">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-password" class="col-sm-2 control-label">密码确认</label>
        <div class="col-sm-10">
          <input type="password" class="form-control" id="create-user-password1" placeholder="密码确认">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-age" class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-10">
          <input name="age" type="password" class="form-control" id="create-user-age" placeholder="年龄">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-org" class="col-sm-2 control-label">所属机构</label>
        <div class="col-sm-10">
          <input name="orgId" type="password" class="form-control" id="create-user-org" placeholder="所属机构">
        </div>
      </div>
    </form>
  </div>
  <div class="panel-footer text-right">
    <a href="javascript:user.submitCreateModal()" class="btn btn-default btn-sm" role="button">
      <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 保 存
    </a>
    <a href="javascript:user.closeCreateModal()" class="btn btn-primary btn-sm" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
  </div>
</div>