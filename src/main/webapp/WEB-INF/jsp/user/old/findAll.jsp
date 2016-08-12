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
<c:forEach items="${allUser}" var="node">
  姓名：<c:out value="${node.name}"></c:out>   <br />
 年龄： <c:out value="${node.age}"></c:out>   <br />
</c:forEach>
   <a href="${pageContext.request.contextPath}">返回</a>
</body>
</html>