<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
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
$(function(){
	$('#email_btn').click(function(){
	
	if($('#type_num').css('display')==='none'){
		$('#type_num').show();
		}
	});
});
</script>
</head>
<body>
<h2>myIdSearchForm.jsp</h2>
<h3>아이디 찾기</h3>
	<div>
		<img id="logo" src="../images/임시_로고.png" width="130" onclick="location.href='../main/main.do'">
		<h3>이메일로 본인인증하기</h3>
			<div>
				<ul>
					<li><input type="text" value="이름"></li>
					<li><input type="email" value="이메일"></li>
					<li><input id="email_btn" type="button" value="인증번호 보내기"></li>
				</ul>
			</div>
			<p>
			<div id="type_num">
				<input type="text" value="인증번호">
				<input type="submit" value="인증하기">
			</div>
	</div>
</body>
</html>