<%--
第一次打开时，由于数据库中没有模板树，初始化根节点
  Created by IntelliJ IDEA.
  User: zhtt
  Date: 2016/8/6
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<div class="panel panel-default">
  <div class="panel-heading">新建字段项</div>
  <div class="panel-body">
    <form class="form-horizontal" action="${ctx}/template/data-statistics/field-form">
      <div style="display: none;">
        <input type="hidden" name="type" value="field">
        <input type="hidden" name="parentId">
        <input type="hidden" name="uuid" >
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-name" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-10">
          <input name="name" type="text" class="form-control" id="create-dataStatisticsTempleate-name" placeholder="名称" minlength="2" maxlength="30" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-colspan" class="col-sm-2 control-label">列数</label>
        <div class="col-sm-10">
          <input name="colspan" type="text" class="form-control" id="create-dataStatisticsTempleate-colspan" placeholder="列数" minlength="1" maxlength="1" digits="true" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-unit" class="col-sm-2 control-label">计量单位</label>
        <div class="col-sm-10">
          <input name="unit" type="text" class="form-control" id="create-dataStatisticsTempleate-unit" placeholder="计量单位" minlength="1" maxlength="5" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-decimalDigits" class="col-sm-2 control-label">小数位数</label>
        <div class="col-sm-10">
          <input name="decimalDigits" type="text" class="form-control" id="create-dataStatisticsTempleate-decimalDigits" placeholder="小数位数" minlength="1" maxlength="3" digits="true" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-maxNumber" class="col-sm-2 control-label">最大值</label>
        <div class="col-sm-10">
          <input name="maxNumber" type="text" class="form-control" id="create-dataStatisticsTempleate-maxNumber" placeholder="最大值" minlength="1" maxlength="4" digits="true" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-minNumber" class="col-sm-2 control-label">最小值</label>
        <div class="col-sm-10">
          <input name="minNumber" type="text" class="form-control" id="create-dataStatisticsTempleate-minNumber" placeholder="最小值" minlength="1" maxlength="4" digits="true" required>
        </div>
      </div>
      <div class="form-group">
        <label for="create-dataStatisticsTempleate-beyondRemind" class="col-sm-2 control-label">阀值提醒</label>
        <div class="col-sm-10">
          <input name="beyondRemind" type="text" class="form-control" id="create-dataStatisticsTempleate-beyondRemind" placeholder="阀值提醒" minlength="1" maxlength="30" required>
        </div>
      </div>
    </form>
  </div>
  <div class="panel-footer text-right">
    <a href="javascript:" onclick="dataStatisticsTemplate.submitCreateForm(this)" class="btn btn-default btn-sm" role="button">
      <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 保 存
    </a>
    <a href="javascript:modalUtil.close('#createFieldDataStatisticsTemplateModal')" class="btn btn-primary btn-sm" role="button">
      <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关 闭
    </a>
  </div>
</div>