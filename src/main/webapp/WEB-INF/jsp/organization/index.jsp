<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/taglibs.jsp" %>
<%@ include file="/jsp/meta.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/jsp/bootstrap-top.jsp" %>
    <link rel="stylesheet" href="${ctx}/tool/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
    <link href="${ctx}/tool/bootstrap3/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx}/tool/bootstrap3/js/bootstrap-responsive.min.css" rel="stylesheet">
</head>

<body role="document">
<%@ include file="/jsp/nav.jsp" %>
<div class="container theme-showcase" role="main">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span4" style="height:100%;overflow: auto;">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div id="orgTreeDiv" class="ztree"></div>
                    </div>
                </div>
            </div>
            <div class="span8" style="height:100%;overflow: auto;">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-xs-12 col-md-8">
                                    <span class="glyphicon glyphicon-tree-conifer" aria-hidden="true"></span> 机构信息列表
                                </div>
                                <div class="col-xs-12 col-md-4 text-right">
                                    <a href="${ctx}/organization/create" class="btn btn-success btn-sm" role="button" id="openCreateOrganizationModelBtn"  data-toggle="modal" data-target="#createOrganizationModal">
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增
                                    </a>
                                    <a href="${ctx}/organization/update" style="display: none;" id="openUpdateOrganizationModelBtn"  data-toggle="modal" data-target="#updateOrganizationModal"></a>
                                    <a href="javascript:organization.update()" class="btn btn-warning btn-sm" role="button">
                                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改
                                    </a>
                                    <a href="javascript:organization.delete()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 删除
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline">
                            <div class="form-group">
                                <label for="query-organization-name">机构名称</label>
                                <input type="text" class="form-control" id="query-organization-name" placeholder="机构名称">
                            </div>
                            <a href="${ctx}/organization/findpage" class="btn btn-default btn-sm" role="button">
                                <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查 询
                            </a>
                        </form>
                        <table id="organizationListTable" class="table table-bordered table-hover  table-striped"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> <!-- /container -->

<div class="modal fade" id="createOrganizationModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="updateOrganizationModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/bootstrap3/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/tool/bootstrap3/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/tool/zTree_v3/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${ctx}/js/organization/organization.js"></script>
<script type="text/javascript" src="${ctx}/js/organization/organizationTree.js"></script>
</body>
</html>

