<%@ include file="/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<META content="IE=11.0000" http-equiv="X-UA-Compatible">
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<TITLE>登录页面</TITLE>
<!-- 弹出框 -->
<link href="${ctx}/tool/Lobibox/Lobibox.min.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/css/login/login.css">
<script src="${ctx}/tool/jquery/jquery-1.10.2.js"></script>
<script src="${ctx}/tool/Lobibox/Lobibox.min.js"></script>
<script src="${ctx}/js/common/LobiboxUtil.js"></script>
<script src="${ctx}/js/login/login.js"></script>
</HEAD>
<BODY onkeyup="login.fnKey(event)">
<DIV class="top_div"></DIV>
<DIV  style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
    <DIV style="width: 165px; height: 96px; position: absolute;">
        <DIV class="tou"></DIV>
        <DIV class="initial_left_hand" id="left_hand"></DIV>
        <DIV class="initial_right_hand" id="right_hand"></DIV>
    </DIV>
    <P style="padding: 30px 0px 10px; position: relative;">
        <SPAN class="u_logo"></SPAN> <INPUT name="username" class="ipt" type="text"   placeholder="请输入用户名或邮箱" value="">
    </P>
    <P style="position: relative;">
        <SPAN class="p_logo"></SPAN> <INPUT name="password" class="ipt" id="password" type="password" placeholder="请输入密码" value="">
    </P>
    <DIV style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
        <P style="margin: 0px 35px 20px 45px;">
			<SPAN style="float: left;"><A  style="color: rgb(204, 204, 204);" href="${ctx}/index">忘记密码?</A></SPAN>
            <SPAN style="float: right;"><A style="color: rgb(204, 204, 204); margin-right: 10px;" href="${ctx}/index">注册</A>
				<A style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;" onclick="login.login()">登录</A>
            </SPAN>
        </P>
    </DIV>
</DIV>
<div style="text-align: center;"></div>
</BODY>
</HTML>
