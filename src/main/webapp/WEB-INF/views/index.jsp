<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 헤더 불러오는 부분 -->
<%@ include file="layout/header.jsp"%>
<div class="container">
	<c:forEach var="board" items="${boards.content}">

		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>
	
	<!-- 페이징 처리. 'c:choose, c:when, c:otherwise'를 이용해 if문 활용 -->
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled">
				<a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item">
				<a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${boards.last}">
					<li class="page-item disabled">
					<a class="page-link" href="?page=${boards.number+1} ">Next</a></li>
			</c:when>
			<c:otherwise>
					<li class="page-item">
					<a class="page-link" href="?page=${boards.number+1} ">Next</a></li>		
			</c:otherwise>
		</c:choose>
	</ul>
</div>

<%@ include file="layout/footer.jsp"%>

