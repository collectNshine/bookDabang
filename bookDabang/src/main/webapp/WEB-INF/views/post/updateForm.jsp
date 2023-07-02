<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서평 수정</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		//이벤트 연결
		$('#update_form').submit(function(){
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
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>서평 쓰기</h2>
		<br><hr><br>
		<form id="update_form" action="update.do" method="post" enctype="multipart/form-data" style="width:1024px; height:1500px;">
		<input type="hidden" name="post_num" value="${post.post_num}">
			<div class="list-space align-right">
				<input type="submit" value="서평 등록" style="width:100px; height:30px; margin-right:20px; background-color:#053B44; color:white;">
			</div>	
			<p>
			<hr>
			<div style="margin-left: 40px;">
				<img src="../images/attachfile.png" width="25" height="25" style="margin-right: 10px; margin-bottom: 3px;">
				<input type="file" name="post_photo" id="post_photo" accept="image/gif, image/png, image/jpeg">
				<c:if test="${!empty post.post_photo}">
					<div id="file_detail">
						(${post.post_photo})파일이 등록되어 있습니다.
						<input type="button" value="파일삭제" id="file_del">
					</div>
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{post_num:${post.post_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'logout'){
												alert('로그인 후 사용 가능합니다.');
											}else if(param.result = 'success'){
												$('#file_detail').hide();
											}else if(param.result = 'wrongAccess'){
												alert('잘못된 접속입니다.');
											}else{
												alert('파일 삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							});
						});
					</script>
					</c:if>
			</div>
			<hr>
			<ul>
				<li>
					<input class="chat-content form-control" type="text" name="post_title" id="post_title" maxlength="50" value="${post.post_title}" style="width:930px; height:40px;">
					<hr>
				</li>
				<li>
					<textarea class="chat-content form-control" rows="300" cols="75" name="post_content" id="summernote" style="width:930px; height:1230px; resize:none;">${post.post_content}</textarea>
				</li>
			</ul>
		</form>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>