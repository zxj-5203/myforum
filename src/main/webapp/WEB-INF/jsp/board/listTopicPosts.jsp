<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 引入自定义的Tag，该Tag用于生成分页导航的代码； -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="zxj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>${topic.topicTitle}</title>
	</head>

	<body>
		 <%@ include file="../includeTop.jsp"%>
		 <div>
		 	<a href="#replyZone">回复</a>  
		 </div>
		 <table border="1px" width="100%">
		 	<c:forEach var="post" items="${pagedPost.result}">
		 		<tr><td colspan="2"><font color="red">${post.postTitle}</font></td></tr>
		 		<tr>
		 			<td width="20%">
		 				用户：${post.user.userName}<br>
		 				积分：${post.user.credit}<br>
		 				时间：<fmt:formatDate value="${post.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
		 			</td>
		 			<td>${post.postText}</td>
		 		</tr>
		 	</c:forEach>
		 </table>
		 
		 <zxj:PageBar pageUrl="/board/listTopicPosts-${topic.topicId}.html" pageAttrKey="pagedPost"></zxj:PageBar>
		 
		 <script type="text/javascript">
		 	function mySubmit(){
		 			var postTitle = document.all("post.postTitle");
		 			if(postTitle.value != null || postTitle.value.length > 50){
		 				alert("帖子标题最大长度不能超过50字符，请调整！");
		 				postTitle.focus();
		 				return false;
		 			}
		 			var postText = document.all("post.postText");
		 			if(postText.value == null || postText.value.length < 10){
		 				alert("回复帖子能容不能小于10个字符！");
		 				postText.focus();
		 				return false;
		 			}
		 			return true;
		 	}
		 </script>
		 <form action="<c:url value="/board/addPost.html"/>" method="post" onsubmit="return mySubmit()">
		 	<hr>
		 	[- 回复 -]<hr>
		 	<a name="replyZone" />
		 	<table border="1px" width="100%">
		 		<tr>
		 			<td width="20%">标题：</td>
		 			<td width="80%"><input type="text" name="postTitle" style="width: 100%"/></td>
		 		</tr>
		 		<tr>
		 			<td width="20%">内容</td>
		 			<td width="80%">
		 				<textarea style="width: 100%; height: 100px" name="postText"></textarea>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td colspan="2" align="left">
		 				<input type="submit" value="保存">
		 				<input type="reset" value="重置">
		 				<input type="button" value="返回" onclick="window.history.go(-1);">
		 				<input type="hidden" value="${topic.boardId}" name="boardId">
		 				<input type="hidden" value="${topic.topicId}" name="topicId">
		 			</td>
		 		</tr>
		 	</table>
		 </form>
	</body>
</html>





