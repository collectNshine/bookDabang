<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	var rannum = null; //랜덤값 저장
	$('#email_btn').click(function(){
		
		if($('#name').val()==''){
			$('#guide1').text('이름을 입력해주세요.').css('color','#F00');
			$('#name').focus();
			return;
		}
		if($('#email').val()==''){
			$('#guide1').text('이메일을 입력해주세요.').css('color','#F00');
			$('#email').focus();
			return;
		}
		
		$('#type_num').show();
		//이름과 이메일이 일치하면 이메일을 보낸다
		$.ajax({
			url:'checkAuthNum.do',
			type:'post',
			dataType:'json',
			data:{name : $('#name').val(),
				  email : $('#email').val()},
			success:function(data){
				rannum = data.rannum;
			},
			error:function(){
				alert('에러가 발생했습니다. ');
			}
			});
		});//end of email_btn
		
	$('#email_form').submit(function(event){
		if($('#auth').val().trim()==''){
			event.preventDefault();
			$('#guide2').text('인증번호를 입력해주세요.').css('color','#F00');
			$('#auth').focus();
			return false;
		}
		if($('#auth').val().trim()!=rannum){
			return false; //임시 비밀번호 전송 작업을 하지 않는다. 
		}
	});
		//자동 엔터 금지
	$('input[type="text"]').keydown(function() {
		  if (event.keyCode === 13) {
		    event.preventDefault();
		 };
	});
});
</script>
</head>
<body cellpadding="0" cellspacing="0" marginleft="0" margintop="0" width="100%" height="100%" align="center" >
	<div class="card align-middle" style="width:30rem; border-radius:20px;">
		<div class="card-body" style="padding: 1rem 5rem 7rem 5rem;">
			<form id="email_form" class="form-signin" method="post" action="showMyPw.do">
				<div class="card-title" style="margin-top:30px;">
					<img src="../images/colorlogo.png" width="150" onclick="location.href='../main/main.do'">
					<h4 class="card-title text-center" style="color:#113366;">비밀번호 찾기</h4>
					<p  class="card-title text-center"> 이메일로 인증하기</p>
				</div>
				<div class="card-body" style="padding:20px;">
					<ul>
						<li>
							<div class="padding">
								<input class="form-control" id="name" name="name"type="text" placeholder="이름">
							</div>
						</li>
						<li>
							<div class="padding">
								<input class="form-control" id="email" name="email" type="email" placeholder="이메일">
							</div>
						</li>
						<li id="guide1"></li>
						<li>
							<div class="padding">
								<input id="email_btn" class="btn btn-lg btn-dark btn-block" type="button" value="인증번호 보내기">
							</div>	
						</li>
					</ul>
					<p>
					<div id="type_num">
						<ul>
							<li><input type="hidden"/></li>
							<li><input class="form-control" id="auth" type="text" placeholder="인증번호"></li>
							<li id="guide2"></li>
							<li><input id="btn-Yes" class="btn btn-lg btn-dark btn-block" type="submit" value="인증하기" ></li>
						</ul>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>