<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 헤더 불러오는 부분 -->
<%@ include file="../layout/header.jsp"%>

<div class="container">
<!-- form이용하려면 name 값이 필요. -->
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username:</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
		</div>
		<div class="form-group">
			<label for="password">Password:</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=8aa2491b3e3f9c3b26767c823c29dfb2&redirect_uri=http://localhost:8282/auth/kakao/callback&response_type=code">
		<img height="38px" src="/image/kakao_login_button.png"/></a>
	</form>

</div>
<!-- form으로 값을 보내려면 하면 button은 form태그 안에, js파일의 ajax를 이용하려고 한다면 button은 form태그 밖에 있어야 한다. -->
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

