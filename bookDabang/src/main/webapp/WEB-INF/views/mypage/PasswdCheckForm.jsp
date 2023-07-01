<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 수정 전 비번 확인</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<style>
input[type="password"], input[type="email"] {
    width: 500px;
    height: 30px;
};
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<hr size="1" noshade="noshade" width="100%">
	<div class="content-main" style="margin-top:100px;">
		<p class="align-center">
			<img src="${pageContext.request.contextPath}/images/lock.png" 
			width="100" height="100" class="align-center lock" >
		</p>
		<div style="font-size:17px;">
		<h3 class="align-center">비밀번호 확인</h3>
		<hr size="1" noshade="noshade" width="100%">
		<p class="align-center">개인정보 변경을 위해 비밀번호를 입력해주세요.</p>
		</div>
		<form id="passwdcheck_form" action="PasswdCheck.do" method="post" style="border:none">
			<div class="result-display" style="border:none; height:50px">
				<div class="align-center" style="display:flex;"> 
					<input type="password" name="passwd" id="passwd" maxlength="12" placeholder="비밀번호를 입력해주세요." style="margin-right: 10px; height:40px;" class="form-control">
					<input type="submit" value="확인" class="btn btn-outline-secondary"> 
				</div>
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
	
</div>

<script type="text/javascript">
	$(function(){
		$('#passwdcheck_form').submit(function(){
			if($('#passwd').val().trim()==''){
				alert('비밀번호를 입력하세요');
				$('#passwd').val('').focus();
				return false;
			}
		});
	});
</script>
</body>
</html>