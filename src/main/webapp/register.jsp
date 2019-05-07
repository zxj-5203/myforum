<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>用户注册</title>
		<script type="text/javascript">
			function mycheck() {
				if (document.all("password").value != document.all("again").value) {
					alert("两次输入的密码不一样，请重新输入！");
					return false;
				} else {
					return true;
				}
			}
		</script>
	</head>

	<body>
		用户注册信息：
		<!-- 当提交表单时，表单中的信息填充到User属性中；要求input的name属性值与domain对象属性名一致； -->
		<form action="<c:url value="/register.html"/>" method="post" onsubmit="return mycheck()">
			<c:if test="${!empty errorMsg}">
				<div style="">${errorMsg }</div>
			</c:if>
	
			<table border="1px" width="60%">
				<tr>
					<td width="20%">用户名</td>
					<td width="80%"><input type="text" name="userName" /></td>
				</tr>
				<tr>
					<td width="20%">密码</td>
					<td width="80%"><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td width="20%">密码确认</td>
					<td width="80%"><input type="password" name="again"></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="保存"> 
						<input type="reset" value="重置">
						<input type="button" value="返回" onclick="window.history.go(-1);">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>





