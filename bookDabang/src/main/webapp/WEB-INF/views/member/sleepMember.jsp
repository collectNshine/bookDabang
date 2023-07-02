<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책다방 : 휴면계정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script type="text/javascript">
window.onload = function() {
	let today = new Date();
	let date = today.getFullYear() + "-"+ (("00"+(today.getMonth()+1).toString()).slice(-2)) +"-"+(("00"+(today.getDate()).toString()).slice(-2));
	document.getElementById('day').innerHTML = date;	
};

</script>
</head>
<body>
<div>
	<div class="card align-middle" style="width:30rem; border-radius:20px;">
		<div class="card-body">
			<img id="logo" src="../images/colorlogo.png" width="150" onclick="location.href='../main/main.do'">
			<div style="text-align:center;">
				<h3>" ${user_name}님 "</h3>
					돌아오신 것을 환영합니다.
			</div>
			<div>
				<p>
				 <table>
				 	<tr>
				 		<td>휴면계정 처리일</td>
				 		<td>${sleep_date}</td>
				 	<tr>
				 	<tr>
				 		<td>복구일</td>
				 		<td id="day"></td>
				 	<tr>
				 </table>
				<p>
			</div>
			<input id="main"  class="btn btn-lg btn-dark btn-block" type="button" value="메인으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</div>
</div>
</body>
</html> 