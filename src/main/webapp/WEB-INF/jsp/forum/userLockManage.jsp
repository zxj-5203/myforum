<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>用户锁定及解锁</title>
	</head>

	<body>
		 <%@ include file="../includeTop.jsp"%>
		 <form action="<c:url value="/forum/userLockManage.html"/>" method="post">
			<table border="1px" width="100%">
				<tr>
					<td width="20%">用户</td>
					<td width="80%">
					    <select name="userName">
							<option>请选择</option>
							<c:forEach var="user" items="${users}">
								<c:if test="${user.locked == 0}">
									<option value="${user.userName}">${user.userName}(未锁定)</option>
								</c:if>
								<c:if test="${user.locked == 1}">
									<option value="${user.userName}">${user.userName}(已锁定)</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%">锁定/解锁</td>
					<td width="80%">
						<input type="radio" name="locked" value="1" >锁定
						<input type="radio" name="locked" value="0" >解锁
					</td>
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





