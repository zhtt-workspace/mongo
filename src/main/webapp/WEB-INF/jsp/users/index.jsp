<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mongodb</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/users/addpage">新增</a> <br/>
    <a href="${pageContext.request.contextPath}/users/findpage">查找</a><br/>
    <a href="${pageContext.request.contextPath}/users/deletepage">删除</a><br/>
    <a href="${pageContext.request.contextPath}/users/modfigPage">修改</a><br/>
    <a href="${pageContext.request.contextPath}/users/findAll">列出所有用户</a>
</body>
</html>