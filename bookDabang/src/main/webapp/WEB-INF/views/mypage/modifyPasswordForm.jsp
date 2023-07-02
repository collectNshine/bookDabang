<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
	ul.modifyPasswordForm li{margin-top : 10px}
	ul.modifyPasswordForm li input{width: 370px;}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//비밀번호 수정 유효성 체크
		$('#password_form').submit(function(){
			//공백 체크
			let items = document.querySelectorAll('input[type="text"],input[type="password"]');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					let label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 항목 필수 입력');
					items[i].value = '';
					items[i].focus();
					return false;
				}
			}//end of for
			
			if(!/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test($("#passwd").val())){
				alert("비밀번호는 8~16자 영문 대소문자, 숫자와 특수문자로 구성되어야 합니다."); 
				$('#passwd').val('').focus();
				$('#passwd').val('');
				return false;
			
			//새 비밀번호 = 새 비밀번호 확인 일치 여부 체크
			} else if($('#passwd').val()!=$('#cpasswd').val()){
				alert('새 비밀번호와 새 비밀번호 확인이 불일치합니다.');
				$('#passwd').val('').focus();
				$('#cpasswd').val('');
				return false;
			}else{
				return true;
			}
			
		});//end of submit
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr size="1" noshade="noshade" width="100%">
	<div class="content-main" style="margin-top: 55px;">
		<p class="align-center">
			<img src="${pageContext.request.contextPath}/images/lock.png" 
			width="100" height="100" class="align-center lock" >
		</p>
		<div style="font-size:17px;">
			<h3 class="align-center">비밀번호 변경</h3>
			<hr size="1" noshade="noshade" width="100%">
			<p class="align-center">개인정보 변경을 위해 비밀번호를 입력해주세요.</p>
		</div>
		<form id="password_form" action="modifyPassword.do" method="post" style="border:none">
			<ul class="modifyPasswordForm">
				<li style="margin-bottom: 25px;">
					<label for="id">아이디</label>
					<input type="text" name="id" id="id" maxlength="12" autocomplete="off" placeholder="현재 아이디를 입력해주세요." class="form-control">
				</li>
				<li>
					<label for="origin_passwd">현재 비밀번호</label>
					<input type="password" name="origin_passwd" id="origin_passwd" maxlength="12" placeholder="현재 비밀번호를 입력해주세요." class="form-control"><br>
				</li>
				<li>
					<label for="passwd">새 비밀번호</label>
					<input type="password" name="passwd" id="passwd" maxlength="12" placeholder="영문,숫자,특수문자 포함 8~16자로 입력해주세요." class="form-control"><br>
				</li>				
				<li>
					<label for="cpasswd">새 비밀번호 확인</label>
					<input type="password" name="cpasswd" id="cpasswd" maxlength="12" placeholder="비밀번호를 다시 한번 입력해주세요." class="form-control"><br>
				</li>				
			</ul>
		<div class="align-center">
			<input type="submit" value="비밀번호 변경" class="btn btn-outline-secondary">
			<input type="button" value="MY페이지" 
					   onclick="location.href='myPage.do'" class="btn btn-outline-secondary">
		</div>
		</form>
	</div>
</div>
</body>
</html>