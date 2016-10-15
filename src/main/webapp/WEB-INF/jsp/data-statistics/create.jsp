<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/taglibs.jsp" %>
<%@ include file="/jsp/meta.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="/jsp/bootstrap-top.jsp" %>
<script type="text/javascript" src="${ctx}/tool/jquery/jquery.fixedtable.js"></script>
<link href="${ctx}/tool/bootstrap3/css/bootstrap-table.min.css" rel="stylesheet">
<style type="text/css">
    .noReportOrgList{
        color:red;
    }
    #dataStatisticsFormBox{
        width: 100%;overflow: auto;
    }
    #dataStatisticsFormBox td,#dataStatisticsFormBox th{
        white-space: nowrap;padding:2px 8px;
    }
    .formTable td{
        padding:8px 5px;
    }
    .formTable input{
        width:300px;
    }
    .formTable textarea{
        width:300px;
    }
</style>
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
                    <label for="query-data-date">数据日期</label>
                    <input type="date" class="form-control" id="query-data-date" placeholder="日期">
                </div>
                <a href="javascript:dataStatisticsCreate.query();" class="btn btn-default btn-sm" role="button">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查 看
                </a>
                <a href="javascript:dataStatisticsCreate.saveStatisticsData();" class="btn btn-success btn-sm" role="button">
                    <span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 保存本次统计数据
                </a>
                <a href="javascript:dataStatisticsCreate.reportStatisticsData();" class="btn btn-success btn-sm" role="button">
                    <span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> 上报本次统计数据
                </a>
            </form>
            <div id="dataStatisticsFormBox">

            </div>
        </div>
    </div>
</div> <!-- /container -->

<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/zTree_v3/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeComboUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/LobiboxUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/timeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/data-statistics/dataStatistics.js"></script>
<script type="text/javascript" src="${ctx}/js/data-statistics/dataStatisticsBuildTable.js"></script>
<script type="text/javascript" src="${ctx}/js/data-statistics/dataStatisticsCreate.js"></script>
</body>
</html>

