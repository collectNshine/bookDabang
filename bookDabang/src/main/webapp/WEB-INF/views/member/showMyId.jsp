<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패스워드</title>  
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body> 
<div>
	<div class="card align-middle" style="width:30rem; border-radius:20px;">
		<div class="card-body">
			<img id="logo" src="../images/colorlogo.png" width="150" onclick="location.href='../main/main.do'">
			<div style="text-align:center;">
					찾으시는 아이디는
				<h3>" ${id} "</h3>
					입니다. 
				 <p>
			</div>
			<input type="button" class="btn btn-lg btn-dark btn-block" value="로그인페이지" onclick="location.href='loginForm.do'">
		</div>
	</div>
</div>
</body>
</html>