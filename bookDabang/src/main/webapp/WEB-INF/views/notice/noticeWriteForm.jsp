<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">

<title>도서신청</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<script type="text/javascript">
$(document).ready(function(){
	$('#summernote').summernote({
        placeholder: '글을 입력해주세요.',
        tabsize: 2,
        height: 300,
        width:700,
        codeviewFilter: false,
        codeviewIframeFilter: true
      });
	 
	//submit할 때 이벤트
	$('#write_Form').submit(function(event){
		let title = $('#noti_title').val().trim();
		let content = $('#summernote').val().trim();
		let keyfield = $('#noti_category').val().trim();
		
		if(title==""){
			$('#guide').text("제목을 입력해주세요.").css("color","#F00");
			$('#noti_title').focus();
			event.preventDefault();
			return;
		}
		if(content==""){
			$('#guide').text("내용을 입력해주세요.").css("color","#F00");
			$('#summernote').focus();
			event.preventDefault();
			return;
		}
		if(keyfield==""){
			$('#guide').text("카테고리를 선택해주세요.").css("color","#F00");
			$('#noti_category').focus();
			event.preventDefault();
			return;
		}
	});
	
});
</script>
<style type="text/css">
#noti_category{
width:200px
}
#noti_title{
width:500px
}
</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="content-main">
	<c:if test="${user_auth == 9 }">
		<form id="write_Form" action="noticeWrite.do" method="post" style="width:1000px; border:none;" >
			<ul>	
				<li>
					<div style="display:inline;">
						<select name="noti_category" id="noti_category">
							<option value="">카테고리</option>
							<option value="1">회원</option>
							<option value="2">주문/주문변경</option>
							<option value="3">결제</option>
							<option value="4">증빙서류</option>
							<option value="5">공지사항</option>
						</select>
						<input id="noti_title" name="noti_title" type="text" placeholder="제목" maxlength="50">
					</div>
				</li>
				<li id="guide"></li>
				<li><textarea id="summernote" name="noti_content" placeholder="내용" ></textarea></li>
				<li><input type="submit" class="btn btn-outline-secondary" value="글쓰기"></li>
			</ul>
		</form>
	</c:if>
	</div>
</div>
</body>
</html>
