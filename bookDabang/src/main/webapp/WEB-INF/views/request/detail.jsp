<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서신청 상세페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<style type="text/css">
	h2{
		position: relative;
		right: 750px;
		top:70px;
		background-color:white;
	}
	
	#list-title{
		
		border:1px solid gray;
		background-color:gray;
		padding-top:80px;
	}

</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>도서 신청</h2>
		
		<div id="list-title">
			<ul>
			<li><h3>${request.req_title} / ${request.req_author}</h3></li>
			<li>${request.req_date}</li>
			</ul>
		</div>
		<ul>
			<li>책 제목</li>
			<li>${request.req_title}</li>
		</ul>
		<ul>
			<li>저자</li>
			<li>${request.req_author}</li>
		</ul>
		<ul>
			<li>출판사</li>
			<li>${request.req_publisher}</li>
		</ul>
		<ul>
			<li>작성자</li>
			<li>${member.id}</li>
		</ul>
		<ul>
			<li>기타</li>
			<li>${request.req_etc}</li>
		</ul>
	 	
	 	<!-- 좋아요 버튼 넣어야함!! -->
	 	
	 	<div class="align-center">
		 	<input type="button" value="수정" onclick="location.href='modify.do'">
		 	<input type="button" value="목록" onclick="location.href='list.do'">
	 	</div>	 	
	</div>
</div>

</body>
</html>