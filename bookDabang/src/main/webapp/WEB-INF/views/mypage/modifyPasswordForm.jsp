<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//비밀번호 수정 유효성 체크
		$('#password_form').submit(function(){
			//공백 체크
			let items = document.querySelectorAll(input[type="password"]');
			for(let i=0;i<items.length;i++ㄴ){
				if(items[i].value.trim()==''){
					let label = document.querySelector('label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 항목 필수 입력');
					items[i].value = '';
					items[i].focus();
					return false;
				}
			}//end of for
			
			//새 비밀번호 = 새 비밀번호 확인 일치 여부 체크
			if($('#passwd').val()!=$('#cpasswd').val()){
				alert('새 비밀번호와 새 비밀번호 확인이 불일치합니다.');
				$('#passwd').val('').focus();
				$('#cpasswd').val('');
				return false;
			}
			
		});//end of submit
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<ul>
			<li>
				<label for="origin_passwd">현재 비밀번호</label>
				<input type="password" name="origin_passwd" id="origin_passwd" maxlength="12">
			</li>
			<li>
				<label for="passwd">새 비밀번호</label>
				<input type="password" name="passwd" id="passwd" maxlength="12">
				<span>영문,숫자,특수문자 포함 8~16자로 입력하세요.</span>
			</li>				
			<li>
				<label for="cpasswd">새 비밀번호 확인</label>
				<input type="password" name="cpasswd" id="cpasswd" maxlength="12">
				<span>비밀번호 확인을 위해 비밀번호를 다시 한번  입력해주세요.</span>
			</li>				
		</ul>
		<div class="align-center">
			<input type="submit" value="비밀번호 변경">
		</div>
	</div>
</div>
</body>
</html>