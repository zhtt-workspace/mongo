<%--
  Created by IntelliJ IDEA.
  organiztion: zhtt
  Date: 2016/8/6
  Time: 19:59
  To change this template org File | Settings | File Templates.
--%>
<div class="panel panel-default">
  <div class="panel-heading">新建机构</div>
  <div class="panel-body">
    <form class="form-horizontal" id="createOrganiztionForm" action="${ctx}/organiztion/create-form">
      <div class="form-group">
        <label for="create-organiztion-name" class="col-sm-2 control-label">机构名称</label>
        <div class="col-sm-10">
          <input name="name" type="text" class="form-control" id="create-organiztion-name" placeholder="机构名称" maxlength="20">
        </div>
      </div>
      <div class="form-group">
        <label for="create-organiztion-fullName" class="col-sm-2 control-label">机构全称</label>
        <div class="col-sm-10">
          <input name="fullName" type="text" class="form-control" id="create-organiztion-fullName" placeholder="机构全称"  disabled>
        </div>
      </div>
      <div class="form-group">
        <label for="create-organiztion-code" class="col-sm-2 control-label">编码</label>
        <div class="col-sm-10">
          <input name="code"  type="text" class="form-control" id="create-organiztion-code" placeholder="编码" disabled>
        </div>
      </div>
      <div class="form-group">
        <label for="create-organiztion-parentId" class="col-sm-2 control-label">上级机构</label>
        <div class="col-sm-10">
          <input name="parentId" type="text" class="form-control" id="create-organiztion-parentId" placeholder="上级机构" disabled>
        </div>
      </div>
    </form>
  </div>
  <div class="panel-footer text-right">
    <a href="javascript:organiztion.submitCreateModal()" class="btn btn-default btn-sm" role="button">
      <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 保 存
    </a>
    <a href="javascript:modalUtil.close('#createOrganizationModal')" class="btn btn-primary btn-sm" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
  </div>
</div>