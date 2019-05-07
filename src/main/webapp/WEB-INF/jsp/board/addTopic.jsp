<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>发表新话题</title>
		<script type="text/javascript">
			function mySubmit(){ 
					var topicTitle = document.all("topicTitle");
					if(topicTitle.value == null || topicTitle.value.length == 0){ // 话题标题为空
						alert("话题标题不能为空，请填写！");
						topicTitle.focus();
						return false;
					}else if(topicTitle.value.length > 100){
						alert("话题标题最大长度不能超过100个字符，请调整！");
						topicTitle.focus();
						return false;
					}
					var postText = document.all("mainPost.postText");
					if(postText.value == null || postText.value.length < 10){
						alert("话题的内容必须大于10个字符！");
						postText.focus();
						return false;
					}
					return true;
			}
		</script>
	</head>

	<body>
		 <%@ include file="../includeTop.jsp" %>
		 <form action="<c:url value="/board/addTopic.html"/>" method="post" onsubmit="return mySubmit()">
		 	<table border="1px" width="100%">
		 		<tr>
		 			<td width="20%">标题</td>
		 			<td width="80%"><input type="text" style="width: 80%" name="topicTitle" value="${topic.topicTitle}"></td>
		 		</tr>
		 		<tr>
		 			<td width="20%">内容</td>
		 			<td width="80%">
		 				<textarea style="width: 100%; height: 400px" name="mainPost.postText"></textarea>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td colspan="2" align="right">
		 				<input type="submit" value="保存">
		 				<input type="reset" value="重置">
		 				<input type="button" value="返回" onclick="window.history.go(-1);">
		 				<input type="hidden" name="boardId" value="${boardId}">
		 			</td>
		 		</tr>
		 	</table>
		 </form>
	</body>
</html>





