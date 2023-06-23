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
<body>
<h2>myPasswdSearchForm.jsp</h2>
<h3>비밀번호 찾기</h3>
	<div>
		<img id="logo" src="../images/임시_로고.png" width="130" onclick="location.href='../main/main.do'">
		<h3>이메일로 본인인증하기</h3>
		<script type="text/javascript">
		//중복 클릭 방지 
		//엔터 자동 submit 방지 
		
		</script>
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
					<li><input id="all_submit" type="submit" value="인증하기" ></li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>