<!-- 自定义标签：《zxj:PageBar》 -->
<!-- 本页是标签页面文件； -->
<!-- 该标签接收两个参数：
	 pageUrl：是转到其他页面的URL；
	 pageAttrKey: 是分页对象在Request属性列表中的键名称，自定义标签需要根据这个键名称从Request对象中获取Page对象；  -->
	 
<%@ tag pageEncoding="UTF-8" %>
<!-- 生命JSTL标签，以便在本标签中使用； -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 定义两个标签属性； 
	 rtexprvalue 的全称是 Run-time Expression Value， 它用于表示是否能够利用JSP表达式动态指定数据； -->
<%@ attribute name="pageUrl" required="true" rtexprvalue="true" description="分页页面对应的URl" %>
<%@ attribute name="pageAttrKey" required="true" rtexprvalue="true" description="Page对象在Request域中的键名称" %>
<c:set var="pageUrl" value="${pageUrl}" />

<!-- 将一些标签中需要引用的对象放置到PageContext属性列表中，以便后面可以直接通过EL表达式引用； -->
<%
   String separator = pageUrl.indexOf("?") > -1?"&":"?"; 
   jspContext.setAttribute("separator", separator);
   jspContext.setAttribute("pageUrl", pageUrl);
   jspContext.setAttribute("pageResult", request.getAttribute(pageAttrKey)); // page对象；
%>

<!-- 构造分页导航栏； -->
<div style="font:12px;background-color:#DDDDDD">
	共${pageResult.totalPageCount}页，第${pageResult.currentPageNo}页
	<c:if test="${pageResult.currentPageNo <=1 }">
		首页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo >1 }">
		<a href="<c:url value="${pageUrl}"/>${separator}pageNo=1">首页</a>&nbsp;&nbsp;
	</c:if>
	
	<c:if test="${!pageResult.hasPreviousPage}">
		上一页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.hasPreviousPage}">
		<a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.currentPageNo - 1}">上一页</a>&nbsp;&nbsp;
	</c:if>
	
	<c:if test="${!pageResult.hasNextPage}">
		下一页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.hasNextPage}">
		<a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.currentPageNo + 1}">下一页</a>&nbsp;&nbsp;
	</c:if>
	
	<c:if test="${pageResult.currentPageNo >= pageResult.totalPageCount}">
		末页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo < pageResult.totalPageCount}">
		<a href="<c:url value="${pageUrl}"/>${separator}pageNo=${pageResult.totalPageCount}">末页</a>&nbsp;&nbsp;
	</c:if>
</div>











