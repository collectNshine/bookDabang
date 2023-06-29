<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>다방소개</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<!-- Carousel 시작 -->
	  	<div class="container">
	  		<div id="intro_car" class="carousel slide" data-bs-ride="carousel">
	  			<!-- indicator -->
	  			<div class="carousel-indicators">
	  				<button type="button" class="active" data-bs-target="#intro_car" data-bs-slide-to="0"></button>
	  				<button type="button" data-bs-target="#intro_car" data-bs-slide-to="1"></button>
	  			</div>
	
	  			<!-- 이미지 표시 영역 -->
	  			<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="${pageContext.request.contextPath}/images/introimage1.jpg" class="d-block w-100">
						<div class="carousel-caption1"> 
							<h5><b>책다방, 도란도란</b></h5>
							<p>편안하게 마치 친구들과 함께 커피를 마시듯 소통하는 작은 공간</p>
						</div>
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/images/introimage4.jpg" class="d-block w-100">
						<div class="carousel-caption2"> 
							<h5><b>책다방, 두런두런</b></h5>
							<p>책이야기를 두런두런 나눌 수 있는 공감의 공간</p>
						</div>
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
	<!-- 메인 소개글 시작-->
<div class="intro_logo">	
	<img src="${pageContext.request.contextPath}/images/reallogo.png" class="logo">
	<h4><b>" 당신을 위한 아늑한 책다방의 세계로 초대합니다. " </b></h4>
	<br><br><br><p> 
	따스한 조명이 비추고, 향긋한 커피 향기가 퍼지며, 책장 위에는 수많은 책들이 아름답게 정리되어있는 곳.<br><br>
	
	그런 공간을 사랑하는 저희가 조금 특별한 공간을 만들었습니다. <br><br>
	
	책다방에서는, 독서와 함께하는 여정을 떠날 수 있고 독서를 사랑하는 사람들과 함께 자유롭게 소통하고 영감을 주고받을 수 있는 공간입니다.<br><br>
	
	가까운 지인들과 선뜻 나누기 낯부끄러웠던 독서에 관한 깊은 성찰과 토론을 책다방과 함께 공유해보세요.<br><br>

	펼쳐지는 페이지마다 다른 세계로 빠져들며, 여러분은 그 세계에서 새로운 모험을 경험할 수 있습니다.<br><br>

	가장 좋아하는 다방에서 햇살이 잘 드는 창가 바로 옆 부드러운 의자에 앉아 창 밖으로 흐르는 풍경을 바라보며, 향긋한 커피를 마시는 기분을 느낄 수 있을지도 몰라요.<br><br>
	
	스트레스와 갑갑한 현실에서 벗어나 책다방과 함께 마음 속의 여유를 찾아보세요.<br><br>

	책다방이 당신의 일상에 작고 소중한 휴식처가 되었으면 좋겠습니다. <br><br>
	
	이곳에 놀러온 여러분 모두가 마음의 여유를 찾고, 새로운 지식을 탐험하며, 공감과 위로를 얻어가셨으면 좋겠다는 마음으로 준비해봤습니다.<br><br>
	
	<br>
	<h5>아무리 시간이 지나도 변하지 않는 소중한 순간들이 쌓이기를,<br><br>

	그럼 이제 주문하시겠습니까?</h5>
	</p>
</div>
<!-- 메인 소개글 끝 -->

<!-- 이미지버튼 1.도서둘러보기 2.모음집 3.담소마당 -->
<div class="intro_btn">
	<button class="intro_btn_inner" type="button">
		<div>
			<img src="${pageContext.request.contextPath}/images/intro_btn_img1.jpg" onclick="location.href='${pageContext.request.contextPath}/book/list.do'">
			<span>책, 한조각</span><br>
			<span>둘러보기</span>
		</div>	
	</button>
	<button class="intro_btn_inner" type="button" >
		<div>
			<img src="${pageContext.request.contextPath}/images/intro_btn_img2.jpg" onclick="location.href='${pageContext.request.contextPath}/post/list.do'">
			<span>기록, 두조각</span><br>
			<span>둘러보기</span>
		</div>
	</button>
	<button class="intro_btn_inner" type="button" >
		<div>
			<img src="${pageContext.request.contextPath}/images/intro_btn_img3.jpg" onclick="location.href='${pageContext.request.contextPath}/chat/list.do'">
			<span>이야기, 세조각</span><br>
			<span>둘러보기</span>
		</div>	
	</button>
</div>
<!-- 이미지버튼 끝 -->
	
	
	
	
	
	<!-- 내용 끝 -->
</div>
</body>
</html>




