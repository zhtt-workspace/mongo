<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/taglibs.jsp" %>
<%@ include file="/jsp/meta.jsp" %>
<!DOCTYPE html>
<HTML>
<HEAD>
<%@ include file="/jsp/bootstrap-top.jsp" %>
<link rel="stylesheet" href="${ctx}/tool/zTree_v3/css/metroStyle/metroStyle.css" type="text/css">
<link rel="stylesheet" href="${ctx}/js/common/zhttlayout/zhttlayout.css" type="text/css">
<style>
</style>
</HEAD>
<body role="document">
<%@ include file="/jsp/nav.jsp" %>
<input name="orgId" id="dataStatisticsTreeInput"  readonly type="text" style="width:215px;">
<input name="orgId1" id="dataStatisticsTreeInput1"  readonly type="text" style="width:215px;">



<!--
<input id="Button1" type="button" value="addMenu" onclick="addMenu()" />
<div id="rMenu">
    <ul>
        <li id="m_add" onclick="addTreeNode();">增加节点</li>
        <li id="m_del" onclick="removeTreeNode();">删除节点</li>
        <li id="m_check" onclick="checkTreeNode(true);">Check节点</li>
        <li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li>
        <li id="m_reset" onclick="resetTree();">恢复zTree</li>
    </ul>
</div>
-->
</BODY>
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/zTree_v3/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/js/template/data-statistics/dataStatisticsTree.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/common/ztreeComboUtil.js"></script>
<script type="text/javascript">
    function addMenu() {
        yzTreeUtils.addMenu('dataStatisticsTreeDiv', 'rMenu');
    }
</script>
</HTML>