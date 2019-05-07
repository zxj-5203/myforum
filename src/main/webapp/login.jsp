<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<% /* java代码：获取绝对路径，等价于 ${pageContext.request.contextPath}   */
	String context = request.getContextPath();
	request.setAttribute("context", context);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>用户登录</title>
	</head>
	
	<body>
		<!-- 若有错误信息，则红色字体显示； -->
		<c:if test="${!empty errorMsg}">
			<div style="color: red">${errorMsg}</div>
		</c:if>
		
		<form action="${context}/login/doLogin.html" method="post"> 
			<table border="1px">
				<tr>
					<td width="20%">用户名</td>
					<td width="80%"><input type="text" name="userName" /></td>
				</tr>
				<tr>
					<td width="20%">密码</td>
					<td width="80%"><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="登录" />
						<input type="reset" value="重置" />
						<input type="button" value="返回" onclick="window.history.go(-1);">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>





