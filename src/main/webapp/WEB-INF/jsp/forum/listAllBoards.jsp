<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>论坛首页</title>
	</head>

	<body>
		 <%@ include file="../includeTop.jsp"%>
		 <table border="1px" width="100%">
		 	<tr><td colspan="3" bgcolor="#EEEEEE">所有论坛版块</td></tr>
		 	<tr>
		 	<tr>
		 		<c:if test="${USER_CONTEXT.userType == 2}">
		 			<td>
		 				<script type="text/javascript">
		 					function switchSelectBox(){
		 						var selectBoxs = document.all("boardIds");
		 						if(!selectBoxs) return ;
		 						if(typeof(selectBoxs.length) == "undefined") { // only one checkbox
		 							selectBoxs.checked = event.srcElement.checked;
		 						}else{
		 							for(var i=0; i<selectBoxs.length; i++){
		 								selectBoxs[i].checked = event.srcElement.checked;
		 							}
		 						}
		 						
		 					}
		 				</script>
		 				<input type="checkbox" onclick="switchSelectBox()" />
		 			</td>
		 		</c:if>
		 		<td width="20%">版块名称</td>
		 		<td width="70%">版块简介</td>
		 		<td width="10%">主题帖数</td>
		 	</tr>
		 	
		 	<c:forEach var="board" items="${boards}">
		 		<tr>
		 			<c:if test="${USER_CONTEXT.userType == 2}">
		 				<td><input type="checkbox" name="boardIds" value="${board.boardId}"/></td>
		 			</c:if>
		 			<td><a href="<c:url value="/board/listBoardTopics-${board.boardId}.html"/>">${board.boardName}</a></td>
		 			<td>${board.boardDesc}</td>
		 			<td>${board.topicNum}</td>
		 		</tr>
		 	</c:forEach>
		 </table>
		 
		 <c:if test="${USER_CONTEXT.userType == 2 || isBoardManager}">
		 	<script type="text/javascript">
		 		function getSelectedBoardIds(){
		 			var selectBoxs = document.all("boardIds");
		 			if(!selectBoxs) return null;
		 			if(typeof (selectBoxs.length) == "undefined" && selectBoxs.checked){
		 				return selectBoxs.value;
		 			}else{
		 				var ids = "";
		 				var split = "";
		 				for(var i=0; i<selectBoxs.length; i++){
		 					if(selectBoxs[i].checked){
		 						ids += split + selectBoxs[i].value;
		 						split = ","
		 					}
		 				}
		 				return ids;
		 			}
		 		}
		 		function deleteBoards(){
		 			var ids = getSelectedBoardIds();
		 			if(ids){
		 				var url = "<c:url value="/board/removeBoard.html"/>?boardIds=" + ids + "";
		 				location.href = url;
		 			}
		 		}
		 	</script>
		 	<input type="button" value="删除" onclick="deleteBoards()" >
		 </c:if>
	</body>
</html>





