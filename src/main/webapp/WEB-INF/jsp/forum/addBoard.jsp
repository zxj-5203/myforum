<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 新增论坛板块的表单页面； -->
<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>添加论坛版块</title>
		<script type="text/javascript">
			function mySubmit(){  
					var boardName = document.all("boardName");
					if(boardName.value == "" || boardName.value.length == 0) { // 版块名为空
						alert("版块名称不能为空，请填上.");
						boardName.focus();
						return false;
					}else if(boardName.value.length > 50){ // 板块名超过指定长度
 						alert("版块名称最大字符不能超过50字符，请调整.");
						boardName.focus(); 
						return false;
					}
					var boardDesc = document.all("boardDesc");
					if(boardDesc.value != null && boardDesc.value.length > 255){
						alert("版块长度最大长度不能超过255个字符，请调整.");
						boardDesc.focus();
						return false;
					} 
					return true;
				} 
		</script>
	</head>

	<body>
		 <%@ include file="../includeTop.jsp" %>>
		 <form action="<c:url value='/forum/addBoard.html'/>" method="post" onsubmit="return mySubmit()">
		 	<table border="1px" width="100%">
		 		<tr>
		 			<td width="20%">版块名称</td>
		 			<td width="80%"><input type="text" name="boardName" style="width: 60%"></td>
		 		</tr>
		 		<tr>
		 			<td width="20%">版块简介</td>
		 			<td width="80%"><textarea style="width: 80%; height: 120px" name="boardDesc"></textarea></td>
		 		</tr>
		 		<tr>
		 			<td colspan="2">
						<input type="submit" value="保存">
						<input type="reset" value="重置">
						<input type="button" value="返回" onclick="window.history.go(-1);">
						<input type="hidden" name="_method" value="PUT">
					</td>
		 		</tr>
		 	</table>
		 </form>
	</body>
</html>





