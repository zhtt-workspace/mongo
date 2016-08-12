<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Fixed navbar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">数据采集系统</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">首页</a></li>
        <li><a href="#about">数据统计表</a></li>
        <li><a href="#contact">信息调查表</a></li>
        <li><a href="#contact">信息排行榜</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">系统管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${ctx}/template/data-statistics/index">数据统计模板</a></li>
            <li><a href="#">调查表模板</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="${ctx}/user/index">用户管理</a></li>
            <li><a href="${ctx}/organization/index">机构管理</a></li>
            <li><a href="#">角色管理</a></li>
            <li><a href="#">权限管理</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">服务接口</a></li>
            <li><a href="#">机构用户数据接入</a></li>
          </ul>
        </li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
