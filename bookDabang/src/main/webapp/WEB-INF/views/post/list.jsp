<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서평 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>서평 모음집</h2>
		<div class="list-space align-right">
			<input type="button" value="서평 작성하기" onclick="location.href='writeForm.do'">
			<!-- <c:if test="${empty user_num}">disabled="disabled"</c:if>> --> 
		</div>
		<hr>
		<div class="post-list">
			<article>
				<img src="../images/face.png"></img>
			</article>
		</div>
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 상품이 없습니다.
			</div>		
		</c:if>
		
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>서평 제목</th>
				<th>서평 내용</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>첨부파일</th>
			</tr>
			<c:forEach var="post" items="${list}">
			<tr>
				<td><a href="detailForm.do?post_num=${post.post_num}">${post.post_title}</a></td>
				<td>${post.post_content}</td>
				<td>${post.mem_num}</td>
				<td>${post.post_date}</td>
				<td>${post.post_photo}</td>
			</tr>
			</c:forEach>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
		</div>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>