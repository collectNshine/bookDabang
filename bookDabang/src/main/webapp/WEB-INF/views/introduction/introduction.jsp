<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>brand introduction</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<!-- 내용시작 -->
<!-- carousel시작 -->	
<div id="intro_car" class="carousel slide" data-bs-ride="carousel">
	<!-- indicator 시작 -->
	<div class="carousel-indicators">
		<button type="button" class="active" data-bs-target="#intro_car" data-bs-slide-to="0"></button>
		<button type="button" class="active" data-bs-target="#intro_car" data-bs-slide-to="1"></button>
		<!-- <button type="button" class="active" data-bs-target="#intro_car" data-bs-slide-to="2"></button> -->
	</div>
	<!-- indicator 끝 -->
	<!-- 이미지 표시 영역 시작 -->
	<div class="carousel-inner">
		<!-- item시작 -->
			<div class="carousel-item active">
				<img src="${pageContext.request.contextPath}/images/fav01.gif" class="d-block w-100">
				<div class="carousel-caption"> 
					<h5>책다방, 도란도란</h5>
					<p>편안하게 마치 친구들과 함께 커피를 마시듯 소통하는 작은 공간</p>
				</div>
			</div>
		<!-- item끝 -->
		<!-- item시작 -->
			<div class="carousel-item active">
				<img src="${pageContext.request.contextPath}/images/fav02.gif" class="d-block w-100">
				<div class="carousel-caption"> 
					<h5>책다방, 한 조각</h5>
					<p>책들에 대한 이야기 조각들이 모인 소중한 공간</p>
				</div>
			</div>
		<!-- item끝 -->
		<%-- <!-- item시작 -->
			<div class="carousel-item active">
				<img src="${pageContext.request.contextPath}/images/fav01.gif" class="d-block w-100">
				<div class="carousel-caption"> 
					<h5>책다방, 도란도란</h5>
					<p>편안하게 마치 친구들과 함께 커피를 마시듯 소통하는 작은 공간</p>
				</div>
			</div>
		<!-- item끝 --> --%>
	</div>
	<!-- 이미지 표시 영역 끝 -->
	<!-- 이미지 선택 버튼 시작 -->
	<button class="carousel-control-prev" type="button" data-bs-target="#intro_car" data-bs-slide="prev">
		<span class="carousel-control-prev-icon"></span>
		<span class="visually-hidden">Previous</span>
	</button>
	<button class="carousel-control-next" type="button" data-bs-target="#intro_car" data-bs-slide="next">
		<span class="carousel-control-next-icon"></span>
		<span class="visually-hidden">Next</span>
	</button>
	<!-- 이미지 선택 버튼 끝 -->
</div>

<!-- carousel끝 -->

<!-- 메인 소개글 시작-->
<div>	
	<img><!-- 로고 -->
	<p>책다방은</p>
</div>
<!-- 메인 소개글 끝 -->

<!-- 이미지버튼 1.도서둘러보기 2.모음집 3.담소마당 -->

<!-- 이미지버튼 끝 -->
<!-- 내용끝 -->
</div>
</body>
</html>