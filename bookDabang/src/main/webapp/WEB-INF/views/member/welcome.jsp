<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입을 축하드립니다</title>
<script type="text/javascript">
//뒤로가기 방지
</script>
</head>
<body>
 	<div>
 	<h2>welcome.jsp</h2>
 		<ul>
 			<li>${user_name}님의 회원가입을 축하드립니다.</li>
 			<li><input id="main" type="button" value="메인으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'"></li>
 		</ul>
 	 </div>
</body>
</html>