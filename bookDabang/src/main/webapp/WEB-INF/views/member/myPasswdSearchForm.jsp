<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴면계정</title>
</head>
<body>
${user_name}님 돌아오신 것을 환영합니다.
<input id="main" type="button" value="메인으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
</body>
</html> 