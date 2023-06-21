<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서평 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//이벤트 연결
		$('#update_form').submit(function(){
			if($('#title').val().trim() == ''){
				alert('제목을 입력하세요!');
				$('#title').val('').focus();
				return false;
			}
			if($('#content').val().trim() == ''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>서평 쓰기</h2>
		<form id="update_form" action="update.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="post_num" value="${post.post_num}">
			<div class="list-space align-right">
				<input type="submit" value="서평 등록">
			</div>	
			<p>
			<hr>
			<div class="align-center">
				<img src="../images/attachfile.png" width="15" height="15">
				<input type="file" name="post_photo" id="post_photo" accept="image/gif, image/png, image/jpeg">
			</div>
			<hr>
			<ul>
				<li>
					<input type="text" name="post_title" id="post_title" maxlength="50" placeholder="제목을 입력해주세요.">
					<hr>
				</li>
				<li>
					<textarea rows="300" cols="75" name="post_content" id="post_content" maxlength="50" placeholder="내용을 입력해주세요."></textarea>
				</li>
			</ul>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>