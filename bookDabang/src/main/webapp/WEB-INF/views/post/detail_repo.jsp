<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신고 상세</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<h2>신고 상세</h2>
	<div class="content-main">
	<input type="hidden" name="post_num" value="${repo.post_num}">
	<div>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty post.photo}">
				<img src="${pageContext.request.contextPath}/upload/${post.photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty post.photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if> 
			</li>
			<li>
				&nbsp;&nbsp;${repo.mem_num} | ${repo.repo_date}<br>
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<div class="align-center">
		<p>
			${repo.repo_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<input type="button" class="btn btn-outline-secondary" value="신고글 이동" onclick="location.href='${pageContext.request.contextPath}/post/detail.do?post_num=${repo.post_num}'">
		<input type="button" class="btn btn-outline-secondary"value="삭제" id="delete_btn">
		<script type="text/javascript">
			let delete_btn = document.getElementById('delete_btn');
			//이벤트 연결
			delete_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					//히스토리를 지우면서 이동
					location.replace('deleteReport.do?repo_num=${repo.repo_num}');
				}
			};
		</script>
		</div>
	</div>
	</div>
</div>
</body>
</html>