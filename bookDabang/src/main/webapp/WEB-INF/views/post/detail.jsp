<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서평 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/post.reply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
	<h2>${post.post_title}</h2>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty post.photo}">
				<img src="${pageContext.request.contextPath}/upload/${post.photo}" 
					 width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty post.photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" 
					 width="40" height="40" class="my-photo">
				</c:if> 
			</li>
			<li>
				${post.name} | ${post.post_date}<br>
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty post.post_photo}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${post.post_photo}" class="detail-img">
		</div>
		</c:if>
		<p>
			${post.post_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				<%-- 좋아요 --%>
				<%-- html은 속성태그 추가X (예외)'data-' 형태로만 추가 가능--%>
				<img id="output_fav" data-num="${post.post_num}" 
					 src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
				좋아요
				<span id="output_fcount"></span>
			</li>
			<li>
				<br>
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정/삭제 가능 --%>
				<c:if test="${user_num == post.mem_num}">
				<input type="button" value="수정" onclick="location.href='updateForm.do?post_num=${post.post_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
				 let delete_btn = document.getElementById('delete_btn');
				 //이벤트 연결
				 delete_btn.onclick=function(){
					 let choice = confirm('삭제하시겠습니까?');
					 if(choice){
						 //히스토리를 지우면서 이동
						 location.replace('delete.do?post_num=${post.post_num}');
					 }
				 };
				</script>
				</c:if>
			</li>
		</ul>
		<br>
		<hr>
		<span>댓글</span>
	</div>
		<!-- 댓글 시작 -->
		<!-- 댓글 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 댓글 목록 출력 끝 -->
		<div id="reply_div">
			<span class="re-title">댓글 달기</span>
			<form id="re_form">
				<input type="hidden" name="post_num" value="${post.post_num}" id="post_num">
				<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
				<c:if test="${empty user_num}">disabled="disabled"</c:if>
				><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}"> <!-- 로그인 여부 체크 -->
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
				</c:if>
			</form>
		</div>		
		<!-- 댓글 끝 -->
	<!-- 내용 끝 -->
</div>
</body>
</html>