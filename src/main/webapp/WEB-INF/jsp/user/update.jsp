<%--
  Created by IntelliJ IDEA.
  User: zhtt
  Date: 2016/8/6
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<div class="panel panel-default">
  <div class="panel-heading">更新用户</div>
  <div class="panel-body">
    <form class="form-horizontal" id="updateUserForm" action="${ctx}/user/update-form" method="post" enctype="multipart/form-data">
      <input type="hidden" name="id_" value="{time: 1470554950000, new: false, timeSecond: 1470554950, machine: 1481613764, inc: 922470185}">
      <div class="form-group">
        <label for="update-user-name" class="col-sm-2 control-label">姓名</label>
        <div class="col-sm-10">
          <input name="name" type="text" class="form-control" id="update-user-name" placeholder="姓名" minlength="2" maxlength="8" required>
        </div>
      </div>
      <div class="form-group">
        <label for="update-user-username" class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-10">
          <input name="username" type="text" class="form-control" id="update-user-username" placeholder="用户名" minlength="6" maxlength="12" required>
        </div>
      </div>
      <div class="form-group">
        <label for="update-user-password" class="col-sm-2 control-label">密码</label>
        <div class="col-sm-10">
          <input name="password" type="password" class="form-control" id="update-user-password" placeholder="密码" minlength="6" maxlength="12" required>
        </div>
      </div>
      <div class="form-group">
        <label for="update-user-password" class="col-sm-2 control-label">密码确认</label>
        <div class="col-sm-10">
          <input type="password" class="form-control" id="update-user-password1" placeholder="密码确认" minlength="6" maxlength="12" required>
        </div>
      </div>
      <div class="form-group">
        <label for="update-user-age" class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-10">
          <input name="age" type="text" class="form-control" id="update-user-age" placeholder="年龄" minlength="1" maxlength="3" required>
        </div>
      </div>
      <div class="form-group">
        <label for="update-user-org" class="col-sm-2 control-label">所属机构</label>
        <div class="col-sm-10">
          <input name="orgId" type="text" class="form-control" id="update-user-org" placeholder="所属机构" minlength="1" maxlength="32" required>
        </div>
      </div>
    </form>
  </div>
  <div class="panel-footer text-right">
    <a href="javascript:user.submitUpdateForm()" class="btn btn-default btn-sm" role="button">
      <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 保 存
    </a>
    <a href="javascript:modalUtil.close('#updateUserModal')" class="btn btn-primary btn-sm" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
  </div>
</div>