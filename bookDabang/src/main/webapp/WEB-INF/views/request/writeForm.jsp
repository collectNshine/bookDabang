<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>도서신청작성</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/request.fav.js"></script>
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
		<form id="write_form" action="write.do" method="post" style="border:none;">
			<ul>
				<li>
					<label for="req_title">책 제목*</label>
					<input class="form-control" type="text" name="req_title" id="req_title" maxlength="40" >
					
				</li>

				<li>
					<label for="req_author">저자*</label>
					<input class="form-control" type="text" name="req_author" id="req_author" maxlength="20">
				
				</li>
				<li>
					<label for="req_publisher">출판사*</label>
					<input class="form-control" type="text" name="req_publisher" id="req_publisher" maxlength="20">
					
				</li>
				<li>
					<label for="req_etc">기타</label>
					<input class="form-control" type="text" name="req_etc" id="req_etc" maxlength="50">
					
				</li>
			</ul>

			<div class="reqformbtn">
				<button type="button" class="btn btn-outline-secondary" onclick="location.href='list.do'">취소</button>
				<button type="submit" class="btn btn-outline-secondary">등록</button>
			</div>
		</form>
		
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	
</div>
</body>
</html>