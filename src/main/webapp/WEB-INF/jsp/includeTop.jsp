<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<a href="<c:url value="/index.jsp" />">首页</a>&nbsp;&nbsp;

<c:if test="${!empty USER_CONTEXT.userName}"> <!-- 若用户名不为空，注销 -->
	${USER_CONTEXT.userName}(${USER_CONTEXT.credit})，欢迎您的到来，<a href="<c:url value="/login/doLogout.html" />">注销</a>
</c:if>
&nbsp;&nbsp;
<c:if test="${empty USER_CONTEXT.userName}"> <!-- 若用户名为空，进行登录/注册 -->
	<a href="<c:url value="/login.jsp"/>">登录</a>&nbsp;&nbsp;
	<a href="<c:url value="/register.jsp"/>">注册</a> 
</c:if>

<c:if test="${USER_CONTEXT.userType == 2}"> <!-- 若当前用户是管理员 -->
	<a href="<c:url value="/forum/addBoardPage.html" />">新建论坛版块</a>&nbsp;&nbsp;
	<a href="<c:url value="/forum/setBoardManagerPage.html" />">论坛版块管理员</a>&nbsp;&nbsp;
	<a href="<c:url value="/forum/userLockManagePage.html" />">用户锁定/解锁</a>
</c:if>

