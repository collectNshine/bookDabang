<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서평 쓰기</title>
<%-- 
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
--%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	<%--
	$('#summernote').summernote({
        placeholder: '내용을 입력해주세요.',
        tabsize: 2,
        height: 1200,
        width: 900,
        codeviewFilter: false,
        codeviewIframeFilter: true
      });
      --%>
	//이벤트 연결       
	$('#write_form').submit(function(){
		if($('#post_title').val().trim() == ''){
			alert('제목을 입력하세요!');
			$('#post_title').val('').focus();
			return false;
		}
		if($('#summernote').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#summernote').val('').focus();
			return false;
		}
	});    
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>서평 쓰기</h2>
		<br><hr><br>
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data" style="width:1024px; height:1500px;">
		<input type="hidden" name="bk_num" id="bk_num" value="${book.bk_num}">
			<div class="list-space align-right">
				<input type="submit" value="서평 등록" style="width:100px; height:30px; margin-right:20px; background-color:#053B44; color:white;">
			</div>	
			<p>
			<hr>
			<div style="margin-left: 40px;">
				<img src="../images/attachfile.png" width="25" height="25" style="margin-right: 10px; margin-bottom: 3px;">
				<input type="file" name="post_photo" id="post_photo" accept="image/gif, image/png, image/jpeg">
			</div>
			<hr>
			<ul>
				<li>
					<input class="chat-content form-control" type="text" name="post_title" id="post_title" maxlength="50" placeholder="제목을 입력해주세요." style="width:930px; height:40px;">
					<hr>
				</li>
				<li>
					<textarea class="chat-content form-control" rows="300" cols="75" name="post_content" id="summernote" placeholder="내용을 입력해주세요." style="width:930px; height:1250px; resize:none;"></textarea>
				</li>
			</ul>
		</form>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>