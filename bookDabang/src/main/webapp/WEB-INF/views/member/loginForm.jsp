<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>책다방 : 로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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
<body cellpadding="0" cellspacing="0" marginleft="0" margintop="0" width="100%" height="100%" align="center" >
	<div class="card align-middle" style="width:30rem; border-radius:20px;">
		<div class="card-title" style="margin-top:30px;">
			<img src="../images/colorlogo.png" width="150" onclick="location.href='../main/main.do'">
			<h2 class="card-title text-center" style="color:#113366;" >로그인</h2>
		</div>
		<div class="card-body" style="padding: 1rem 5rem 7rem 5rem;">
		<b id="guide"></b>
		<form id="login_Form" class="form-signin" action="login.do" method="post">
			<ul>
				<li>
					<div class="padding">
						<input class="form-control" id="id" name="id" type="text"  placeholder="아이디" >
					</div>
				</li>
				<li>
					<div class="padding">
						<input class="form-control" id="passwd" name="passwd" type="password" placeholder="비밀번호" >
					</div>
				</li>
				<li>
					<div  class="padding2">
						<input id="btn-Yes" class="btn btn-lg btn-dark btn-block" type="submit" value="로그인" >
					</div>
				</li>
			</ul>	
		</form>
		<div class="align-middle" style="display:block; text-align: center;">
			<a href="myPasswdSearchForm.do"> 패스워드 찾기 </a><b>|</b>
			<a href="myIdSearchForm.do"> 아이디 찾기 </a><b>|</b>
			<a href="joinForm.do"> 회원가입 </a>
		</div>
		</div>
		</div>
		
	</div>
	</body>
</html>