<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.io.File" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#update_form').submit(function(){
			if($('#title').val().trim()==''){
				alert('도서명을 입력하세요!');
				$('#name').val('').focus();
				return false;
			}
			if($('#author').val().trim()==''){
				alert('저자명을 입력하세요!');
				$('#author').val('').focus();
				return false;
			}
			if($('#publisher').val().trim()==''){
				alert('출판사를 입력하세요!');
				$('#publisher').val('').focus();
				return false;
			}
			if($('#price').val()==''){ 
				alert('가격을 입력하세요!');
				$('#price').focus();
				return false;
			}
			if($('#stock').val()==''){
				alert('재고를 입력하세요!');
				$('#stock').focus();
				return false;
			}
			/*
			if($('#thumbnail').val()==''){
				alert('썸네일 사진을 첨부하세요!');
				$('#thumbnail').focus();
				return false;
			}
			*/
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
				return false;
			}

			 let choice = confirm('수정하시겠습니까?');
			 if(choice){
				 //히스토리를 지우면서 이동
				 location.replace('${pageContext.request.contextPath}/mypage/myPage.do');
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
		<h2>도서 정보 수정</h2>
		<form action="update.do" method="post" encType="multipart/form-data" id="update_form">
			<input type="hidden" name="bk_num" value="${book.bk_num}">
			<ul>
				<li>
					<label for="title">도서명</label>
					<input type="text" name="title" id="title" maxlength="40" value="${book.title}">
				</li>
				<li>
					<label for="author">저자명</label>
					<input type="text" name="author" id="author" maxlength="20" value="${book.author}">
				</li>
				<li>
					<label for="publisher">출판사</label>
					<input type="text" name="publisher" id="publisher" maxlength="20" value="${book.publisher}">
				</li>
				<li>
					<label for="price">가격</label>
					<input type="number" name="price" id="price" min="1" max="99999999" value="${book.price}">
				</li>
				<li>
					<label for="stock">재고</label>
					<input type="number" name="stock" id="stock" min="0" max="9999" value="${book.stock}">
				</li>
				<li>
					분류
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="category" id="category">
						<option value="문학" <c:if test="${book.category=='문학'}">selected</c:if>>문학</option>
						<option value="경제/경영" <c:if test="${book.category=='경제/경영'}">selected</c:if>>경제/경영</option>
						<option value="인문" <c:if test="${book.category=='인문'}">selected</c:if>>인문</option>
						<option value="예술/대중문화" <c:if test="${book.category=='예술/대중문화'}">selected</c:if>>예술/대중문화</option>	
						<option value="사회/정치" <c:if test="${book.category=='사회/정치'}">selected</c:if>>사회/정치</option>
						<option value="자연과학" <c:if test="${book.category=='자연과학'}">selected</c:if>>자연과학</option>
						<option value="자기계발" <c:if test="${book.category=='자기계발'}">selected</c:if>>자기계발</option>
						<option value="IT모바일" <c:if test="${book.category=='IT모바일'}">selected</c:if>>IT모바일</option>
						<option value="유아/어린이" <c:if test="${book.category=='유아/어린이'}">selected</c:if>>유아/어린이</option>
						<option value="만화" <c:if test="${book.category=='만화'}">selected</c:if>>만화</option>
					</select>
				</li>
				<li>
					<label for="thumbnail">썸네일</label>
					<input type="file" name="thumbnail" id="thumbnail" accept="image/gif,image/png,image/jpeg">
					<img src="${pageContext.request.contextPath}/upload/${book.thumbnail}" width="200" id="db_thumbnail">
						<div id="file_detail">
							(${book.thumbnail})파일이 등록되어 있습니다.
						</div>
					<script type="text/javascript">
						$(function(){
							//이미지 미리 보기
							//1.처음 화면에 보여지는 기존 DB 이미지 저장
							let photo_path = $('#db_thumbnail').attr('src');
							//2.새로 선택한 이미지 저장
							let new_thumbnail;
							$('#thumbnail').change(function(){
								new_thumbnail = this.files[0];
								//새 이미지를 선택 안 했을 경우 (선택하려다 취소)
								if(!new_thumbnail){ 
									$('#db_thumbnail').attr('src',photo_path);
									return;
								}
								//파일 용량이 지정한 범위를 넘을 경우
								if(new_thumbnail.size > 1024*1024){
									alert(Math.round(new_thumbnail.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
									$('#db_thumbnail').attr('src',photo_path);
									$(this).val(''); //선택한 파일 정보 지우기
									return;
								}
								
								let reader = new FileReader();
								reader.readAsDataURL(new_thumbnail);
								reader.onload=function(){
									$('#db_thumbnail').attr('src',reader.result);
									$('#file_detail').empty();
								};
								
								
							});//end of change
							
							<%--  파일 삭제 처리 
							$('#update_form').submit(function(){
								let choice = confirm('수정하시겠습니까?');
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{bk_num:${book.bk_num}},
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
								
							});//end of click --%>
						});
					</script>
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content">${book.content}</textarea>
				</li>
			</ul>
			<div class="align-center">
				<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/mypage/myPage.do'">
				<input type="submit" value="수정">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
				 let delete_btn = document.getElementById('delete_btn');
				 //이벤트 연결
				 delete_btn.onclick=function(){
					 let choice = confirm('삭제하겠습니까?');
					 if(choice){
						 //히스토리를 지우면서 이동
						 location.replace('delete.do?bk_num=${book.bk_num}');
					 }
				 };
				</script>
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>