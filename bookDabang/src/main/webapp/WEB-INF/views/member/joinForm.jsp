<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

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
//아이디 중복체크 
//주소 API적용하기
$(function(){
	//입력값 검증하기 오류 펑펑 터진다~
	/*
	$('#join_Form').submit(function(){
		for
		if(check[i].val.trim()==''){
			$('#guide').text("값을 입력해주세요.").css("color","#F00");
			check[i].focus();
			return false;
		}
	});
	*/
	
	//아이디 입력값 검증
	//비밀번호 입력값 검증
	
	//비밀번호 일치 확인 
	$('#passwd_btn').click(function(){
		if($('#passwd').val()!=$('#passwd2').val()){
			$('#guide').text("비밀번호와 비밀번호 확인이 불일치합니다.").css("color","#F00");
			$('#passwd').val('').focus();
			$('#passwd2').val('');
			return false;
		}else{
			$('#guide').text("비밀번호와 비밀번호 확인이 일치합니다.").css("color","#000");
		}
	});
	//체크박스 하나만 선택하게 만들기 
	$('input[type="checkbox"]').click(function(){
		if($(this).prop('checked')){//체크 액션 발생 속성이 checked가 됨
			$('input[type="checkbox"]').prop('checked',false);//나머지는 체크풀고 
			$(this).prop('checked',true);//액션 발생한 체크박스만 체크
		}
	});
		
	//이메일 인증 버튼 클릭시 인증번호 입력창 나오게 만들기
	$('#email_btn').click(function(){
		
		if($('#type_num').css('display')==='none'){
			$('#type_num').show();
			}
		});
	});
		
//이메일 인증 구현하기
</script>
</head>
<body>
<h2>joinForm.jsp</h2>
	<div>
		<img id="logo" src="../images/임시_로고.png" width="130" onclick="location.href='../main/main.do'">
		<form id="join_Form" action="join.do" method="post">
			<div>
				<ul>
					<li>
						<input class="check" id="id" name="id" type="text" placeholder="아이디">
					</li>
					<li>
						<input class="check" id="passwd" name="passwd" type="password" placeholder="비밀번호">
					</li>	
					<li>
						<input id="passwd2" name="passwd2" type="password" placeholder="비밀번호 확인">
						<input id="passwd_btn" type="button" value="확인">
					</li>
					<li>
						<input class="check" id="phone" name="phone" type="text" placeholder="전화번호">
					</li>
					<li id="guide"></li>
					<li>
						<input class="check" id="name" name="name" type="text" placeholder="이름">
					</li>
					<li>
						<label>생일</label>
						<input  id="birthday" name="birthday" type="date">
					</li>
					<li>
						<input class="check" id="zipcode" name="zipcode" type="text" placeholder="우편번호">
					</li>
					<li>
						<input type="button" value="주소찾기">
					</li>
					<li>
						<input class="check" id="address1" name="address1" type="text" placeholder="주소">
					</li>
					<li>
						<input class="check" id="address2" name="address2" type="text" placeholder="상세주소">
					</li>
					<li>
						<label>성별</label>
						<input  id="sex1" name="sex" type="checkbox" value="1" checked>남자
						<input  id="sex2" name="sex" type="checkbox" value="2">여자
					<li>
					<li>
						<input class="check" id="email" name="email" type="email" placeholder="이메일">
					</li>
					<li>아래 버튼을 누르고 가입하기를 눌러주세요.</li>
					<li>
						<input  id="email_btn" type="button" value="이메일주소로 인증번호발송">
					</li>
				</ul>	
			</div>
			<div id="type_num" >
				<ul >
					<li>
						<input id="num" name="num" type="text" placeholder="인증번호">
					</li>
					<li>
						<input type="submit" value="가입하기">
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>