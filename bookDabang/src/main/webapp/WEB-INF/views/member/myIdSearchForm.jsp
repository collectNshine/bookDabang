<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책다방 : 아이디 찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script type="text/javascript">
$(document).ready(function(){
	
	var rannum = "";
	var input = "";
	var name = "";
	var email ="";
	
	$('#email_btn').click(function(){
		
		if($('#name').val().trim()==''){
			$('#guide1').text('이름을 입력해주세요.').css('color','#F00');
			$('#name').focus();
			return;
		}
		
		if($('#email').val().trim()==''){
			$('#guide1').text('이메일을 입력해주세요.').css('color','#F00');
			$('#email').focus();
			return;
		}
		if($('#guide1').val()!=null){
			$('#guide1').text('');
		}
		
		$('#type_num').show();
		
		
		$.ajax({
			url:'checkAuthNum.do',
			type:'post',
			dataType:'json',
			data:{name : $('#name').val(),
				  email : $('#email').val()},
			success:function(data){
				rannum = data.rannum;
				//처음 입력한 이름과 이메일 값을 변수에 저장한다. 
				name = $('#name').val();
				email = $('#email').val();
			},
			error:function(){
				alert('에러가 발생했습니다.');
			}
		});
	});//end of email_btn
	
	$('#email_form').submit(function(){
		input = $('#auth').val().trim();

		if(input == ""){
			$('#guide2').text('인증번호를 입력해주세요.').css('color','#F00');
			return false ;
		}
		if(rannum !== input || rannum == ''){
			$('#guide2').text('다시 입력 바랍니다.').css('color','#F00');
			return false ;
		}
		if(name != $('#name').val().trim() || email != $('#email').val().trim()){
			$('#guide2').text('잘못된 접근입니다.').css('color','#F00');
			return false ;
		}
	});
	
	$('input').keydown(function(){
		$('#guide1').text('');
		$('#guide2').text('');
	});
});
</script>
</head>
<body cellpadding="0" cellspacing="0" marginleft="0" margintop="0" width="100%" height="100%" align="center" >
	<div class="card align-middle" style="width:30rem; border-radius:20px;">
		<div class="card-body" style="padding: 1rem 5rem 7rem 5rem;">
			<form id="email_form" class="form-signin" method="post" action="showMyId.do">
				<div class="card-title" style="margin-top:30px;">
					<img src="../images/colorlogo.png" width="150" onclick="location.href='../main/main.do'">
					<h4 class="card-title text-center" style="color:#113366;" >아이디 찾기</h4>
					<p  class="card-title text-center"> 이메일로 인증하기</p>
				</div>
				<div class="card-body">
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