<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("newline", "\n"); %>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(fn:replace(fn:replace(vo.contents, ">", "&gt;"), "<", "&lt;"), newline, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<c:if test="${vo.userNo eq authUser.no}">
						<a href="${pageContext.request.contextPath}/board?a=modifyform&no=${vo.no }">글수정</a>
					</c:if>
					
					<c:if test="${not empty authUser.no}">
						<a href="${pageContext.request.contextPath}/board?a=writeform&no=${vo.no }">답글쓰기</a>
					</c:if>
					<a href="${pageContext.request.contextPath}/board?p=1">글목록</a>
					
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>