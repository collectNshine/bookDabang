<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>책다방</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<!-- Carousel 시작 -->
	  	<div class="container">
	  		<div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
	  			<!-- indicator -->
	  			<div class="carousel-indicators">
	  				<button type="button" class="active" data-bs-target="#carouselExample" data-bs-slide-to="0"></button>
	  				<button type="button" data-bs-target="#carouselExample" data-bs-slide-to="1"></button>
	  				<button type="button" data-bs-target="#carouselExample" data-bs-slide-to="2"></button>
	  			</div>
	
	  			<!-- 이미지 표시 영역 -->
	  			<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="${pageContext.request.contextPath}/images/main_01.PNG" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/images/main_02.PNG" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/images/main_03.PNG" class="d-block w-100">
					</div>
	  			</div>
	  			
	  			<!-- 이미지 선택 버튼  -->
	  			<button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
	  				<span class="carousel-control-prev-icon"></span>
	  				<span class="visually-hidden">Previous</span>
	  			</button>
	  			<button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
	  				<span class="carousel-control-next-icon"></span>
	  				<span class="visually-hidden">Next</span>
	  			</button>
	  		</div>	
	  	</div>	
	</div>
	<!-- Carousel 끝 -->
	
	
	
	
	
	
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>




