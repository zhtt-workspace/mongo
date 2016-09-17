<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page language="java" import="zhtt.entity.user.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="loginUser" value="${sessionScope.loginUser}" target="${user}" scope="session" />
<c:set var="loginRootOrganization" value="${sessionScope.loginRootOrganization}" target="${loginRootOrganization}" scope="session" />
<script type="text/javascript">
    var ctx="${ctx}";
    var loginUser={"uuid":"${loginUser.uuid}","name":"${loginUser.name}","orgId":"${loginUser.orgId}"};
    var loginRootOrganization={"uuid":"${loginRootOrganization.uuid}","name":"${loginRootOrganization.name}"};
</script>