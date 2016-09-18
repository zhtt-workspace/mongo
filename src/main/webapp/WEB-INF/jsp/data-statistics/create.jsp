<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/taglibs.jsp" %>
<%@ include file="/jsp/meta.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/jsp/bootstrap-top.jsp" %>
<link rel="stylesheet" href="${ctx}/tool/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
<link href="${ctx}/tool/bootstrap3/css/bootstrap-table.min.css" rel="stylesheet">
</head>

<body role="document">
<%@ include file="/jsp/nav.jsp" %>
<div class="container theme-showcase" role="main">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-md-8">
                        <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> 数据采集管理
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <form class="form-inline">
                <div class="form-group">
                    <label for="query-user-startdate">数据日期</label>
                    <input type="date" class="form-control" id="query-user-startdate" placeholder="日期">
                </div>
                <a href="javascript:user.query();" class="btn btn-default btn-sm" role="button">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查 看
                </a>
                <a href="" class="btn btn-success btn-sm" role="button">
                    <span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 上 报
                </a>
            </form>
            <table id="userListTable" class="table table-bordered table-hover  table-striped"></table>
        </div>
    </div>
</div> <!-- /container -->

<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/zTree_v3/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeComboUtil.js"></script>
</body>
</html>

