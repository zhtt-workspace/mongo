<%--
第一次打开时，由于数据库中没有模板树，初始化根节点
  Created by IntelliJ IDEA.
  User: zhtt
  Date: 2016/8/6
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<div class="panel panel-default">
  <div class="panel-heading">初始化根节点</div>
  <div class="panel-body">
    <form class="form-horizontal" id="createDataStatisticsTemplateForm" action="${ctx}/template/data-statistics/init-root-form">
      <div class="form-group">
        <label for="create-user-name" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-10">
          <input name="name" type="text" class="form-control" id="create-user-name" placeholder="名称">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-username" class="col-sm-2 control-label">列数</label>
        <div class="col-sm-10">
          <input name="username" type="text" class="form-control" id="create-user-username" placeholder="用户名">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-password" class="col-sm-2 control-label">单位</label>
        <div class="col-sm-10">
          <input name="password" type="text" class="form-control" id="create-user-password" placeholder="密码">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-password" class="col-sm-2 control-label">小数位数</label>
        <div class="col-sm-10">
          <input type="text" class="form-control" id="create-user-password1" placeholder="小数位数">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-age" class="col-sm-2 control-label">最大值</label>
        <div class="col-sm-10">
          <input name="age" type="text" class="form-control" id="create-user-age" placeholder="最大值">
        </div>
      </div>
      <div class="form-group">
        <label for="create-user-org" class="col-sm-2 control-label">最小值</label>
        <div class="col-sm-10">
          <input name="orgId" type="text" class="form-control" id="create-user-org" placeholder="最小值">
        </div>
      </div>
    </form>
  </div>
  <div class="panel-footer text-right">
    <a href="javascript:dataStatisticsTemplate.submitCreateModal()" class="btn btn-default btn-sm" role="button">
      <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 保 存
    </a>
    <a href="javascript:modalUtil.close('#createDataStatisticsTemplateModal')" class="btn btn-primary btn-sm" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
  </div>
</div>