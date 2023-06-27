<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	$('#summernote').summernote({
        placeholder: '글을 입력해주세요.',
        tabsize: 2,
        height: 300,
        width:1000,
        codeviewFilter: false,
        codeviewIframeFilter: true
      });
	//submit할 때 이벤트
	$('#edit_Form').submit(function(event){
		let title = $('#qna_title').val().trim();
		let content = $('#summernote').val().trim();
		let keyfield = $('#noti_category').val().trim();
		
		if(title==""){
			$('#guide').text("제목을 입력해주세요.").css("color","#F00");
			$('#qna_title').focus();
			event.preventDefault();
			return;
		}
		if(content==""){
			$('#guide').text("내용을 입력해주세요.").css("color","#F00");
			$('#summernote').focus();
			event.preventDefault();
			return;
		}
	});
});

</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<form id="edit_Form" action="qnaEdit.do" method="post">
		<ul>
			<li>
			<input id="qna_num" name="qna_num" type="hidden" value="${dto.qna_num}">
			<input id="qna_title" name="qna_title" type="text" value="${dto.qna_title}" maxlength="50">
			</li>
			<li id="guide"></li>
			<li><textarea id="summernote" name="qna_content" placeholder="내용">${dto.qna_content}</textarea></li>
			<c:if test="${user_num == dto.mem_num or user_auth == 9}">
			<li><input type="submit" value="수정하기"></li>
			</c:if>
		</ul>
	</form>
</body>
</html>