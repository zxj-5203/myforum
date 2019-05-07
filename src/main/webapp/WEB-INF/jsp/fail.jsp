<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>操作失败</title>
	</head>
	<body>
		操作失败！ 错误信息：${errorMsg}<br>
		<input type="button" value="返回" onclick="window.history.go(-1);">
	</body>
</html>