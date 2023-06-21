<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맞춤도서 신청 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>
	$(function(){
		$('#write_form').submit(function(){
			if($('#req_title').val().trim()==''){
				alert('제목 입력은 필수입니다.');
				$('#req_title').val('').focus();
				return false;
			}
			if($('#req_author').val().trim()==''){
				alert('저자 입력은 필수입니다.');
				$('#req_author').val('').focus();
				return false;
			}
			if($('#req_publisher').val().trim()==''){
				alert('출판사 입력은 필수입니다.');
				$('#req_publisher').val('').focus();
				return false;
			}
		});
		
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>도서 신청</h2>
		<form id="write_form" action="write.do" method="post" >
			<ul>
				<li>
					<label for="req_title">책 제목</label>
					<input type="text" name="req_title" id="req_title" maxlength="50">
				</li>
				<li>
					<label for="req_author">저자</label>
					<input type="text" name="req_author" id="req_author" maxlength="50">
				</li>
				<li>
					<label for="req_publisher">출판사</label>
					<input type="text" name="req_publisher" id="req_publisher" maxlength="50">
				</li>
				<li>
					<label for="req_etc">기타</label>
					<input type="text" name="req_etc" id="req_etc" maxlength="150">
				</li>
			</ul>
			<div class="align-center">
				<input type="button" value="취소" onclick="location.href='list.do'">
				<input type="submit" value="등록">
			</div>
		</form>
		
	</div>
	
</div>
</body>
</html>