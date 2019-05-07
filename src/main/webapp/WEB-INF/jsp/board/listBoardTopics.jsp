<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 引入自定义的Tag，该Tag用于生成分页导航的代码； -->
<%@ taglib tagdir="/WEB-INF/tags" prefix="zxj" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- 分页显示论坛版块的所有主题帖子； -->
<html>
	<head>
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<title>论坛板块页面</title>
	</head>

	<body>
		<!-- file属性值 不支持表达式、不允许通过'?'挂参传递参数； -->
		<!-- 先包含，后编译：把file文件代码与本文件组合后编译，两文件中不能有同名变量； -->
		<%@ include file="../includeTop.jsp" %>
		
		<div>
			<!-- 判断用户是否是该板块的管理员； -->
			<c:set var="isBoardManager" value="${false }"></c:set>        <!-- 设置默认不是管理员 -->
			<c:forEach items="${USER_CONTEXT.manBoards }" var="manBoard">
				<c:if test="${manBoard.boardId == board.boardId }">       <!-- 若当前登录用户是当前版块的管理员 -->
					<c:set var="isBoardManager" value="${true }"></c:set> <!-- 修改变量：当前用户是管理员 -->
				</c:if>
			</c:forEach>
			
			<table border="1px" width="100%">
				<tr>
					<!-- BaseController.setSessionUser方法把user对象添加到session域中，保存的属性名是USER_CONTEXT； -->
					<c:if test="${USER_CONTEXT.userType==2 || isBosrdManager }"><td></td></c:if>
					<td bgcolor="#EEEEEE">${board.boardName }</td>	
					<td colspan="4" bgcolor="#EEEEEE" align="left">
						<a href="<c:url value="/board/addTopicPage-${board.boardId}.html"/>">发表新话题</a>
					</td>
				</tr>
				
				<tr>
					<c:if test="${USER_CONTEXT.userType == 2 || isBoardManager }">
						<td>
							<script type="text/javascript">
								function switchSelectBox(){
									var selectBoxs = document.all("topicIds");
									if(!selectBoxs) return ;
									if(typeof(selectBoxs.length) == 'undefined') // 判断selectBoxs是否是数组；
										selectBoxs.checked = event.srcElement.checked;
									else 
										for(var i=0; i<selectBoxs.length; i++){
											selectBoxs[i].checked = event.srcElement.checked;
										}
								}
							</script>
							<input type="checkbox" onclick="switchSelectBox()" />
						</td>
					</c:if>
					<td width="50%">标题</td>
					<td width="10%">发表人</td>
					<td width="10%">回复数</td>
					<td width="15%">发表时间</td>
					<td width="15%">最后回复时间</td>
				</tr>
				
				<!-- 对保存在Page对象中的分页数据进行渲染，以显示一页数据； -->
				<c:forEach items="${pagedTopic.result}" var="topic">
					<tr>
						<!-- 若是论坛版块管理员或论坛管理员，则显示批量操作的复选框； -->
						<c:if test="${USER_CONTEXT.userType == 2 || isBoardManager }">
							<td><input type="checkbox" name="topicIds" value="${topic.topicId }" /></td>
						</c:if>
						<td>
							<a href='<c:url value="/board/listTopicPosts-${topic.topicId }.html" />'>
								<c:if test="${topic.digest > 0 }"> <!-- 0:不是精华话题 1:是精华话题 -->
									<font color="red">★</font>    <!-- 精华帖子附加上星号； -->
								</c:if>
								${topic.topicTitle }
							</a>
						</td>
						<td>${topic.user.userName}<br><br></td>
						<td>${topic.topicReplies}<br></td>
						<td><fmt:formatDate value="${topic.createTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
						<td><fmt:formatDate value="${topic.lastPost }" pattern="yyyy-MM-dd hh:mm:ss"/></td>					
					</tr>
				</c:forEach>
			</table>
		</div>	
		
		<!-- 分页显示导航栏 -->
		<zxj:PageBar pageUrl="/board/listBoardTopics-${board.boardId}.html" pageAttrKey="pagedTopic" />
		
		<c:if test="${USER_CONTEXT.userType == 2 || isBoardManager}">
			<script type="text/javascript">
				function getSelectTopicIds(){
					var selectBoxs = document.all("topicIds");
					if(!selectBoxs) return null;
					if(typeof(selectBoxs.length) == "undefined" && selectBoxs.checked){
						return selectBoxs.value;
					}else{ // checkbox是一个数组；
						var ids = "";
						var split = "";
						for(var i=0; i<selectBoxs.length; i++){
							if(selectBoxs[i].checked){
								ids += split+selectBoxs[i].value
								split = ",";
							}
						}
						return ids;
					}
				}
				function deleteTopics(){
					var ids = getSelectTopicIds();
					if(ids){
						var url = "<c:url value="/board/removeTopic.html"/>?topicIds="+ids+"&boardId=${board.boardId}";
						//alert(url)
						location.href = url;  // 在当前页面中打开URL页面
					}
				}
				function setDefinedTopics(){
					var ids = getSelectTopicIds();
					if(ids){
						var url = "<c:url value="/board/makeDigestTopic.html"/>?topicIds="+ids+"&boardId=${board.boardId}";
						location.href = url;
					}
				}
			</script>
			<input type="button" value="删除" onclick="deleteTopics()">
			<input type="button" value="置精华帖" onclick="setDefinedTopics()">
			<input type="button" value="返回" onclick="window.history.go(-1);">
		</c:if>
	</body>
</html>