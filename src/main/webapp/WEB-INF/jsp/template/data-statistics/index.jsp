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
            <div class="span3" style="overflow: auto;">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div id="dataStatisticsTemplateTreeDiv" class="ztree"></div>
                    </div>
                </div>
            </div>
            <div class="span9" style="overflow: auto;">
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-xs-12 col-md-2">
                                    <span class="glyphicon glyphicon-tree-conifer" aria-hidden="true"></span> 模板预览
                                </div>
                                <div class="col-xs-12 col-md-10 text-right">
                                    <a href="${ctx}/template/data-statistics/create-root" style="display: none;" id="openCreateRootDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#createRootDataStatisticsTemplateModal"></a>
                                    <a href="${ctx}/template/data-statistics/create-group" style="display: none;" id="openCreateGroupDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#createGroupDataStatisticsTemplateModal"></a>
                                    <a href="${ctx}/template/data-statistics/create-field" style="display: none;" id="openCreateFieldDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#createFieldDataStatisticsTemplateModal"></a>
                                    <a href="javascript:dataStatisticsTemplate.openForm('createGroupModalId')" class="btn btn-success btn-sm" role="button">
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增分组
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.openForm('createFieldModalId')" class="btn btn-success btn-sm" role="button">
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增字段
                                    </a>
                                    <a href="${ctx}/template/data-statistics/create-root" style="display: none;" id="openUpdateRootDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#updateRootDataStatisticsTemplateModal"></a>
                                    <a href="${ctx}/template/data-statistics/create-field" style="display: none;" id="openUpdateFieldDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#updateFieldDataStatisticsTemplateModal"></a>
                                    <a href="${ctx}/template/data-statistics/create-group" style="display: none;" id="openUpdateGroupDataStatisticsTemplateModelBtn"  data-toggle="modal" data-target="#updateGroupDataStatisticsTemplateModal"></a>
                                    <a href="javascript:dataStatisticsTemplate.openUpdateForm()" class="btn btn-warning btn-sm" role="button">
                                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.delete()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 删除
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.showNode()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> 显示
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.hideNode()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span> 隐藏
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.moveUp()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span> 上移
                                    </a>
                                    <a href="javascript:dataStatisticsTemplate.moveDown()" class="btn btn-danger btn-sm" role="button">
                                        <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span> 下移
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
                        <div id="dataStatisticsTemplateTableBox">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> <!-- /container -->
<!-- 新建根节点 -->
<div class="modal fade" id="createRootDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 新建分组 -->
<div class="modal fade" id="createGroupDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 新建字段 -->
<div class="modal fade" id="createFieldDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 更新分组 -->
<div class="modal fade" id="updateGroupDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 更新字段 -->
<div class="modal fade" id="updateFieldDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 更新根节点 -->
<div class="modal fade" id="updateRootDataStatisticsTemplateModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</BODY>
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/zTree_v3/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/timeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeComboUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/template/data-statistics/dataStatisticsTemplate.js"></script>
<script type="text/javascript" src="${ctx}/js/template/data-statistics/dataStatisticsTemplateTree.js"></script>
<script type="text/javascript" src="${ctx}/js/template/data-statistics/dataStatisticsTemplateTable.js"></script>
</HTML>