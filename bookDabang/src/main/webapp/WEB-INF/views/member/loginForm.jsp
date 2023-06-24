<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
#logo{
	cursor: pointer;
}

</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script type="text/javascript">
//입력값 검증
	$(document).ready(function(){
		
		$('#login_Form').submit(function(event){
	
			if($('#id').val().trim()==""){
				$('#guide').text("아이디를 입력해주세요.").css("color","#F00");
				$('#id').focus();
				event.preventDefault();
				return;
			}
			if($('#passwd').val().trim()==""){
				$('#guide').text("비밀번호를 입력해주세요.").css("color","#F00");
				$('#passwd').focus();
				event.preventDefault();
				return;
			}
		});	
	});
	//로그인 실패 
</script>
</head>
<body>
	<div>
		<img id="logo" src="../images/임시_로고.png" width="130" onclick="location.href='../main/main.do'">
		<form id="login_Form" action="login.do" method="post">
			<ul>
				<li>
					<input id="id" name="id" type="text"  placeholder="아이디" >
				</li>
				<li>
					<input id="passwd" name="passwd" type="password" placeholder="비밀번호" >
				</li>
				<li id="guide"></li>
				<li>
					<input id="submit" type="submit" value="로그인" >
				</li>
			</ul>	
		</form>
	</div>
	<div>
		<a href="myPasswdSearchForm.do">패스워드 찾기</a>
		<a href="myIdSearchForm.do">아이디 찾기</a>
		<a href="joinForm.do">회원가입</a>
	</div>
	</body>
</html>