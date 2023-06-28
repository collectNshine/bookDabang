<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1 게시판 글 보기</title>
<style type="text/css">
#add_reply{
display:none;
}
#tablebox{
overflow:auto;
height:500px;
}
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
	$('#add_btn').click(function(){
		$('#add_reply').show();
	});
});
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div>
	<!-- 부모글 보여주기 시작 -->
	<ul>
		<li><h1>${dto.qna_title}</h1></li>
		<li>${dto.reg_date}</li>
		<li/>
		<hr size ="1" width="100%" noshade="noshade" />
		<li><p>${dto.qna_content}<p></li>
		<hr size ="1" width="100%" noshade="noshade" />
		
		<li>
			<c:if test="${user_num == dto.mem_num or user_auth == 9}">
			<input type="button" value="수정" onclick="location.href='qnaEditForm.do?qna_num=${dto.qna_num}'">
			<c:if test="${user_auth == 9}">
			<input id="delete_btn" type="button" value="삭제" >
			</c:if>
			<input id="add_btn" type="button" value="답글 추가하기" >
			</c:if>
			<hr size ="1" width="100%" noshade="noshade" />
		</li>
	</ul>
	<!-- 부모글 보여주기 끝 -->
	<!-- 자식글 입력창 시작 -->
	<div id="add_reply">
		<form id="write_Form" action="qnaWriteReply.do" method="post">
			<ul>
				<li><h2>답글추가</h2></li>
				<li>
				<input id="qna_num" name="qna_num" type="hidden" value="${dto.qna_num}">
				<input id="qna_title" name="qna_title" type="text" maxlength="50" placeholder="제목을 입력해주세요.">
				</li>
				<li id="guide"></li>
				<li><textarea id="summernote" name="qna_content" placeholder="내용을 입력하세요."></textarea></li>
				<li><input type="submit" value="저장하기"></li>
			</ul>
		</form>
	</div>
	<!-- 자식글 입력창 끝 -->
</div>

<script type="text/javascript">
	$(function(){
		$('#delete_btn').click(function(){
			let choice = confirm('삭제하겠습니까?');
			if(choice){
				location.replace('qnaDelete.do?qna_num=${dto.qna_num}');
			}
		});
	});
</script> 
</body>
</html>