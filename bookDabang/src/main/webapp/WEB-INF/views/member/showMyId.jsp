<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>
회원님의 아이디는 
${id} 입니다. 
<input type="button" value="로그인페이지" onclick="location.href='loginForm.do'">
<input type="button" value="비밀번호찾기" onclick="location.href='myPasswdSearchForm.do'">
</body>
</html>