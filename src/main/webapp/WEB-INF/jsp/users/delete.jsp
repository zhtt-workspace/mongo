<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>mongodb</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script>
 function returnPage(){
    var frmObj=document.forms[0];
    frmObj.action="${pageContext.request.contextPath}";
    frmObj.submit();
 }
</script>
  <body>
       <form action="${pageContext.request.contextPath}/users/delete" method="post">
          name: <input type="text" name="name" />  <br />
          <input type="submit" value="提交"/>
          <input type="submit" value="返回" onclick="returnPage();">
    </form>
  </body>
</html>