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
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-md-8">
                        <span class="glyphicon glyphicon-user" aria-hidden="true"></span> 用户信息列表
                    </div>
                    <div class="col-xs-12 col-md-4 text-right">
                        <a href="${ctx}/user/create" class="btn btn-success btn-sm" role="button"  data-toggle="modal" data-target="#createUserModal">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 新增
                        </a>
                        <a href="${ctx}/user/modfigPage" class="btn btn-warning btn-sm" role="button">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改
                        </a>
                        <a href="${ctx}/user/deletepage" class="btn btn-danger btn-sm" role="button">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 删除
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <form class="form-inline">
                <div class="form-group">
                    <label for="query-user-name">姓名</label>
                    <input type="text" class="form-control" id="query-user-name" placeholder="姓名">
                </div>
                <div class="form-group">
                    <label for="query-user-username">用户名</label>
                    <input type="text" class="form-control" id="query-user-username" placeholder="用户名">
                </div>
                <div class="form-group">
                    <label for="query-user-startdate">录入时间</label>
                    <input type="date" class="form-control" id="query-user-startdate" placeholder="开始时间">
                    <input type="date" class="form-control" id="query-user-endDate" placeholder="结束时间">
                </div>
                <a href="${ctx}/user/findpage" class="btn btn-default btn-sm" role="button">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查 询
                </a>
                <a href="${ctx}/user/findAll" class="btn btn-info btn-sm" role="button">列出所有用户</a>
            </form>
            <div class="user-table-list"></div>
        </div>
        <!-- Table -->
        <table class="table table-bordered table-hover  table-striped">
            <tr>
                <th><input type="checkbox"></th></td><th>用户名</th><th>姓名</th><th>所属机构</th><th>录入时间</th>
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
            <tr>
                <td><input type="checkbox"></td><td>Toom</td><td>Toom</td><td>Toom</td><td>Toom</td>
            </tr>
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
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#">10</a></li>
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

<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<%@ include file="/jsp/bootstrap-bottom.jsp" %>
<script type="text/javascript" src="${ctx}/tool/bootstrap3/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${ctx}/tool/bootstrap3/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${ctx}/js/user/index.js"></script>
</body>
</html>

