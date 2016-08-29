<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/taglibs.jsp" %>
<%@ include file="/jsp/meta.jsp" %>
<!DOCTYPE html>
<HTML>
<HEAD>
<%@ include file="/jsp/bootstrap-top.jsp" %>
<link rel="stylesheet" href="${ctx}/tool/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
<link href="${ctx}/tool/bootstrap3/js/bootstrap-responsive.min.css" rel="stylesheet">
</HEAD>
<BODY role="document">
<%@ include file="/jsp/nav.jsp" %>
<div class="container theme-showcase" role="main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span3" style="height:100%;overflow: auto;">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div id="dataStatisticsTemplateTreeDiv" class="ztree"></div>
                    </div>
                </div>
            </div>
            <div class="span9" style="height:100%;overflow: auto;">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-xs-12 col-md-8">
                                    <span class="glyphicon glyphicon-tree-conifer" aria-hidden="true"></span> 模板预览
                                </div>
                                <div class="col-xs-12 col-md-4 text-right">
                                    <a href="${ctx}/template/data-statistics/create" style="display: none;" id="openCreateDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#createDataStatisticsTemplateModal"></a>
                                    <a href="javascript:dataStatisticsTemplate.openCreateForm()" class="btn btn-success btn-sm" role="button">
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增
                                    </a>
                                    <a href="${ctx}/template/data-statistics/update" style="display: none;" id="openUpdateDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#updateDataStatisticsTemplateModal"></a>
                                    <a href="javascript:dataStatisticsTemplate.openUpdateForm()" class="btn btn-warning btn-sm" role="button">
                                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.delete()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 删除
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline">
                            <div class="form-group">
                                <label for="query-name">名称</label>
                                <input type="text" class="form-control" id="query-name" placeholder="名称">
                            </div>
                            <a href="javascript:dataStatisticsTemplate.query()" class="btn btn-default btn-sm" role="button">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查 询
                            </a>
                        </form>
                        <table id="dataStatisticsTemplateListTable" class="table table-bordered table-hover  table-striped"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> <!-- /container -->

<div class="modal fade" id="createDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="updateDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</BODY>
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/zTree_v3/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeComboUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/template/data-statistics/dataStatisticsTemplate.js"></script>
<script type="text/javascript" src="${ctx}/js/template/data-statistics/dataStatisticsTemplateTree.js"></script>
</HTML>