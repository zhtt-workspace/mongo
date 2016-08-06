<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/taglibs.jsp" %>
<%@ include file="/jsp/meta.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="/jsp/bootstrap-top.jsp" %>
    <link href="${ctx}/tool/bootstrap3/css/bootstrap-table.min.css" rel="stylesheet">
</head>

<body role="document">
<%@ include file="/jsp/nav.jsp" %>
<div class="container theme-showcase" role="main">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">
            用户信息列表
            <a href="${ctx}/user/addpage" class="btn btn-info btn-xs" role="button">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增
            </a>
            <a href="${ctx}/user/deletepage" class="btn btn-info btn-sm" role="button">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
            </a>
            <a href="${ctx}/user/modfigPage" class="btn btn-info btn-sm" role="button">
                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>修改
            </a>
        </div>
        <div class="panel-body">
            <p>
                <a href="${ctx}/user/findpage" class="btn btn-info" role="button">查找</a>
                <a href="${ctx}/user/findAll" class="btn btn-info" role="button">列出所有用户</a>
            </p>
            <div class="user-table-list"></div>
        </div>
        <!-- Table -->
        <table class="table table-bordered table-hover  table-striped">
            <thead>
            <tr>
                <th><input type="checkbox"></th></td><th>用户名</th><th>姓名</th><th>所属机构</th><th>录入时间</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
            </tbody>
        </table>
        <div class="panel-footer">
            <nav>
                <ul class="pagination">
                    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                    <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li>
                        <a href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div> <!-- /container -->
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/bootstrap3/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/tool/bootstrap3/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/js/user/index.js"></script>
</body>
</html>
