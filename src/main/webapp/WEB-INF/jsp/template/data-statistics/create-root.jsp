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
    <form class="form-horizontal" action="${ctx}/template/data-statistics/root-form">
      <div style="display: none;">
        <input type="hidden" name="type" value="root">
        <input type="hidden" name="parentId" value="doc_tree">
        <input type="hidden" name="uuid" >
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-name" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-10">
          <input name="name" type="text" class="form-control" id="create-dataStatisticsTempleate-name" placeholder="名称" minlength="2" maxlength="30" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-colspan" class="col-sm-2 control-label">总列数</label>
        <div class="col-sm-10">
          <input name="colspan" type="text" class="form-control" id="create-dataStatisticsTempleate-colspan" placeholder="总列数" minlength="1" maxlength="2" digits="true" required>
        </div>
      </div>
    </form>
  </div>
  <div class="panel-footer text-right">
    <a href="javascript:" onclick="dataStatisticsTemplate.submitCreateForm(this)" class="btn btn-default btn-sm" role="button">
      <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 保 存
    </a>
    <a href="javascript:modalUtil.close('#createRootDataStatisticsTemplateModal')" class="btn btn-primary btn-sm crete-close-btn" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
    <a href="javascript:modalUtil.close('#updateRootDataStatisticsTemplateModal')" class="btn btn-primary btn-sm update-close-btn hide" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
  </div>
</div>