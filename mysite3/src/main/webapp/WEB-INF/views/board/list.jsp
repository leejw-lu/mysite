<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:set var="count" value="${page.totalCount }" />
					<c:forEach items='${list }' var='vo' varStatus="status">				
					<tr>
						<td>${count- (status.index + 6*(page.currentPage-1))  }</td>
						<td style="text-align:left; padding-left:${20* vo.depth }px">
							<c:if test="${vo.depth > 0 }">
								<img src='${pageContext.request.contextPath}/assets/images/reply.png'>
							</c:if>
							
							<a href="${pageContext.request.contextPath}/board/view/${vo.no }">${vo.title }</a>
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<c:if test="${vo.userName eq authUser.name}">
							<td><a href="${pageContext.request.contextPath}/board/delete/${vo.no }" class="del">
								<img src='${pageContext.request.contextPath}/assets/images/recycle.png'>
							</a></td>
						</c:if>
						
					</tr>
					</c:forEach>
					
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose> 
							<c:when test="${page.currentPage eq 1}">
								<li>◀</li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/board?p=${page.currentPage -1}">◀</a></li>
							</c:otherwise> 
						</c:choose> 

						<c:forEach var="i" begin="${page.beginPage }" end="${page.beginPage + 4 }">
							<c:choose> 
								<c:when test="${page.currentPage eq i}">
									<li class="selected">${page.currentPage }</li>
								</c:when>
								<c:when test="${page.endPage < i}">
									<li>${i}</li>
								</c:when> 
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/board?p=${i}">${i}</a></li>
								</c:otherwise> 
							</c:choose> 
						</c:forEach>
						
						<c:choose> 
							<c:when test="${page.currentPage >= page.endPage}">
								<li>▶</li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath}/board?p=${page.currentPage +1}">▶</a></li>
							</c:otherwise> 
						</c:choose> 
					
					</ul>
				</div>					
				<!-- pager 추가 -->
							
				<c:if test="${not empty authUser.no}">
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board/insert" id="new-book">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>