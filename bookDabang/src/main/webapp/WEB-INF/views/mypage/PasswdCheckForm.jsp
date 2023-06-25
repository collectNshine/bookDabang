<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 수정 전 비번 확인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
input[type="password"], input[type="email"] {
    width: 500px;
    height: 30px;
};
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<hr size="1" noshade="noshade" width="100%">
	<div class="content-main" style="margin-top:100px;">
		<p class="align-center">
			<img src="${pageContext.request.contextPath}/images/lock.png" 
			width="100" height="100" class="align-center lock" >
		</p>
		<div style="font-size:17px;">
		<p class="align-center">비밀번호 확인</p>
		<hr size="1" noshade="noshade" width="700px;">
		<p class="align-center">개인정보 변경을 위해 비밀번호를 입력해주세요.</p>
		</div>
		<form id="passwdcheck_form" action="PasswdCheck.do" method="post" style="border:none">
			<div class="result-display" style="border:none;">
				<div class="align-center" style="display:flex;"> 
					<input type="password" name="passwd" id="passwd" maxlength="12" placeholder="비밀번호를 입력해주세요." style="margin-right: 10px;">
					<%-- <input type="button" value="확인" onclick="location.href='${pageContext.request.contextPath}/mypage/modifyUserForm.do'"> --%>
					<input type="button" value="확인" onclick="chkPW()"> 
				</div>
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
	
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

function chkPW(){

	 var pw = $("#passwd").val();
	 var num = pw.search(/[0-9]/g);
	 var eng = pw.search(/[a-z]/ig);
	 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

	 if(pw.length < 3 || pw.length > 20){

	  alert("3자리 ~ 20자리 이내로 입력해주세요.");
	  return false;
	 }else if(pw.search(/\s/) != -1){
	  alert("비밀번호는 공백 없이 입력해주세요.");
	  return false;
	 }else if(pw !=$(passwd).val()){
	  	alert("비밀번호 확인이 불일치합니다.");
	  	$('#passwd').val('').focus();
		$('#passwd').val('');
		return false;
	 }else {
		alert("통과"); 
		window.location.replace('${pageContext.request.contextPath}/mypage/modifyUserForm.do')
	    return true;
	 }

	}

	/* $(function(){
				$('#passwdcheck_form').submit(function(){
					if($('#passwd').val().trim()==''){
						alert('비밀번호를 입력하세요');
						$('#passwd').val('').focus();
						return false;
					}
					
					//비밀번호 확인 일치 여부 체크
					if($('#passwd').val()!=$(passwd).val()){
						alert('비밀번호 확인이 불일치합니다.');
						$('#passwd').val('').focus();
						$('#passwd').val('');
						return false;
					}
					
				});//end of submit
			}); 
		*/	
</script> 
</body>
</html>