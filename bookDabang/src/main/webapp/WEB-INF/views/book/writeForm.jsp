<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>도서 등록 | 책다방</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
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
			if($('#thumbnail').val()==''){
				alert('썸네일 사진을 첨부하세요!');
				$('#thumbnail').focus();
				return false;
			}
			if($('#content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#content').val('').focus();
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
		<h2 class="align-center"><a href="writeForm.do">도서 등록</a></h2>
		<form action="write.do" method="post" encType="multipart/form-data" class="info_form">
			<div class="thumbnail-info">
				<img src="${pageContext.request.contextPath}/images/bk_no_image.png" width="300" class="db-thumbnail">
				<div id="file_detail">
					등록된 파일이 없습니다.
				</div>			
			</div>
			<div class="book-info">
			<ul>
				<li>
					<label for="title">도서명</label>
					<input type="text" name="title" id="title" class="form-control" maxlength="40">
				</li>
				<li>
					<label for="author">저자명</label>
					<input type="text" name="author" id="author" class="form-control" maxlength="20">
				</li>
				<li>
					<label for="publisher">출판사</label>
					<input type="text" name="publisher" id="publisher" class="form-control" maxlength="20">
				</li>
				<li>
					<label for="price">가격</label>
					<input type="number" name="price" id="price" class="form-control" min="1" max="99999999">
				</li>
				<li>
					<label for="stock">재고</label>
					<input type="number" name="stock" id="stock" class="form-control" min="0" max="9999">
				</li>
				<li>
					<label for="category">분류</label>
					<select name="category" id="category" class="form-select" aria-label="Default select example">
						<option value="문학">문학</option>
						<option value="경제/경영">경제/경영</option>
						<option value="인문">인문</option>
						<option value="예술/대중문화">예술/대중문화</option>	
						<option value="사회/정치">사회/정치</option>
						<option value="자연과학">자연과학</option>
						<option value="자기계발">자기계발</option>
						<option value="IT모바일">IT모바일</option>
						<option value="유아/어린이">유아/어린이</option>
						<option value="만화">만화</option>
					</select>
				</li>
				<li>
					<label for="thumbnail" class="form-label">썸네일</label>
					<input type="file" name="thumbnail" id="thumbnail" class="form-control" accept="image/gif,image/png,image/jpeg">
					<script type="text/javascript">
						$(function(){
							let new_thumbnail;
							$('#thumbnail').change(function(){
								new_thumbnail = this.files[0];
								//새 이미지를 선택 안 했을 경우 (선택하려다 취소)
								if(!new_thumbnail){ 
									$('.db-thumbnail').attr('src','${pageContext.request.contextPath}/images/bk_no_image.png');
									return;
								}
								//파일 용량이 지정한 범위를 넘을 경우
								if(new_thumbnail.size > 1024*1024){
									alert(Math.round(new_thumbnail.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
									$('.db-thumbnail').attr('src','${pageContext.request.contextPath}/images/bk_no_image.png');
									$(this).val(''); //선택한 파일 정보 지우기
									return;
								}
								//이미지 읽기
								let reader = new FileReader();
								reader.readAsDataURL(new_thumbnail);
								reader.onload=function(){
									$('.db-thumbnail').attr('src',reader.result);
								};
							});//end of change
						});
					</script>
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="60" name="content" id="content" class="form-control"></textarea>
				</li>
			</ul>
			</div>
			<div class="button-info">
				<input type="button" value="목록" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/mypage/myPage.do'">
				<input type="submit" value="등록" class="btn btn-primary">
			</div>
		</form>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>