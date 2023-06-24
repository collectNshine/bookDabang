<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>

<style>
#type_num{
	display:none;
}
#logo{
	cursor: pointer;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
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
		alert(input);
		alert(rannum);
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
});
</script>
</head>
<body>
	<div>
		<img id="logo" src="../images/임시_로고.png" width="130" onclick="location.href='../main/main.do'">
		<h3>비밀번호 찾기:이메일로 본인인증하기</h3>
		<form id="email_form" method="post" action="showMyPw.do">
			<div>
				<ul>
					<li><input id="name" name="name"type="text" placeholder="이름"></li>
					<li><input id="email" name="email" type="email" placeholder="이메일"></li>
					<li id="guide1"></li>
					<li><input id="email_btn" type="button" value="인증번호 보내기"></li>
				</ul>
			</div>
			<p>
			<div id="type_num">
				
				<ul>
					<li><input id="auth" type="text" placeholder="인증번호"></li>
					<li id="guide2"></li>
					<li><input id="all_btn" type="submit" value="인증하기" ></li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>