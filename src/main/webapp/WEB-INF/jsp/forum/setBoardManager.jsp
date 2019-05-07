<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>指定论坛版块管理员</title>
	</head>

	<body>
		<%@ include file="../includeTop.jsp"%>
		<form action="<c:url value="/forum/setBoardManager.html"/>" method="post">
			<table border="1px" width="60%">
				<tr>
					<td width="20%">论坛模块</td>
					<td width="80%">
						<select name="boardId">
							<option>请选择</option>
							<c:forEach var="board" items="${boards}">
								<option value="${board.boardId}">${board.boardName}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%">用户</td>
					<td width="80%">
						<select name="userName">
							<option>请选择</option>
							<c:forEach var="user" items="${users}">
								<option value="${user.userName}">${user.userName}</option>
							</c:forEach>
						</select>
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





