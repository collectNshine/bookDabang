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
		//이름과 이메일이 일치하면 이메일을 보낸다.
		let rannum = null; //랜덤값 저장
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
		
	//아래 수정할 것.
		
	$('#all_submit').submit(function(event){
		if($('#auth').val()==''){
			event.preventDefault();
			$('#guide2').text('인증번호를 입력해주세요.').css('color','#F00');
			$('#auth').focus();
			return;
		}
		
	});
});
</script>
</head>
<body cellpadding="0" cellspacing="0" marginleft="0" margintop="0" width="100%" height="100%" align="center" >
	<div class="card align-middle" style="width:20rem; border-radius:20px;">
		
		<form id="email_form" class="form-signin" method="post" action="showMyId.do">
			<div class="card-title" style="margin-top:30px;">
				<img src="../images/colorlogo.png" width="150">
				<h4 class="card-title text-center" style="color:#113366;" >비밀번호 찾기</h4>
				<p  class="card-title text-center"> 이메일로 인증하기</p>
			</div>
			<div class="card-body">
				<ul>
					<li><input class="form-control" id="name" name="name"type="text" placeholder="이름"></li>
					<li><input class="form-control" id="email" name="email" type="email" placeholder="이메일"></li>
					<li id="guide1"></li>
					<li><input id="email_btn" class="btn btn-lg btn-dark btn-block" type="button" value="인증번호 보내기"></li>
				</ul>
				<p>
				<div id="type_num">
					<ul>
						<li><input class="form-control" id="auth" type="text" placeholder="인증번호"></li>
						<li id="guide2"></li>
						<li><input id="btn-Yes" class="btn btn-lg btn-dark btn-block" type="submit" value="인증하기" ></li>
					</ul>
				</div>
			</div>
		</form>
	</div>
</body>
</html>