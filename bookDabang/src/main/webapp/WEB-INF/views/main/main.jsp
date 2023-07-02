<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>책다방</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<!-- Carousel 시작 -->
	  
	    <div style="margin-top:7%;">
	  	<div class="container">
	  		<div id="carsouselExample" class="carousel slide" data-bs-ride="carousel">
	  			<!-- indicator -->
	  			<div class="carousel-indicators">
	  				<button type="button" class="active" data-bs-target="#carouselExample" data-bs-slide-to="0"></button>
	  				<button type="button" data-bs-target="#carouselExample" data-bs-slide-to="1"></button>
	  				<button type="button" data-bs-target="#carouselExample" data-bs-slide-to="2"></button>
	  			</div>
	
	  			<!-- 이미지 표시 영역 -->
	  			<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="${pageContext.request.contextPath}/upload/carousel1.png" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/upload/carousel2.png" class="d-block w-100">
					</div>
					<%-- <div class="carousel-item">
						<img src="${pageContext.request.contextPath}/upload/carousel2.png" class="d-block w-100">
					</div> --%>
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
	<section id="section">
		<div class="mainmain">
		<nav>
			<div class="recommendpost" >
        	<div class="title">으뜸서평</div>
        	<div id="post_list">
        	<c:forEach var="post" items="${postlist}">
        		<div class="card mb-3" style="max-width: 540px;">
        			<div class="row g-0">
    					<div class="col-md-4">
        					<img class="img-fluid rounded-start" src="${pageContext.request.contextPath}/upload/${post.thumbnail}"  width="100" height="150">
        				</div>
        				<div class="col-md-8">
						      <div class="card-body">
						        <h5 class="card-title">${post.post_title}</h5>
						        <p class="card-text">	${post.post_content}</p>
						      </div>
						 </div>
					 </div>
				</div>
				</c:forEach>
        	</div>
        </div>
        </nav>

        <main>
        	<div class="container2" style="margin-bottom=10;">
	  		<div id="carsouselExample2" class="carousel slide" data-bs-ride="carousel">
	  			<!-- indicator -->
	  			<div class="carousel-indicators">
	  				<button type="button" class="active" data-bs-target="#carouselExample2" data-bs-slide-to="0"></button>
	  				<button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="1"></button>
	  				<button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="2"></button>
	  			</div>
	
	  			<!-- 이미지 표시 영역 -->
	  			<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="${pageContext.request.contextPath}/upload/main.png" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/upload/main2.png" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/upload/main3.png" class="d-block w-100">
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
	        
	        <div class="container2">
	  		<div id="carsouselExample2" class="carousel slide" data-bs-ride="carousel">
	  			<!-- indicator -->
	  			<div class="carousel-indicators">
	  				<button type="button" class="active" data-bs-target="#carouselExample2" data-bs-slide-to="0"></button>
	  				<button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="1"></button>
	  				<button type="button" data-bs-target="#carouselExample2" data-bs-slide-to="2"></button>
	  			</div>
	
	  			<!-- 이미지 표시 영역 -->
	  			<div class="carousel-inner">
					<div class="carousel-item active">
						<img src="${pageContext.request.contextPath}/upload/main.png" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/upload/main2.png" class="d-block w-100">
					</div>
					<div class="carousel-item">
						<img src="${pageContext.request.contextPath}/upload/main3.png" class="d-block w-100">
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
	       </main>	
        
        <aside>
        <div class="recommendbook">
        	<!-- <p><b>추천도서</b></p> -->
        	<div class="title">
        		<%-- <img src="${pageContext.request.contextPath}/upload/green.png" width="100%" height="100"> --%>
        		<div class="title">으뜸도서</div>
        	</div>
        	<div id="book_list">
        	<c:forEach var="book" items="${booklist}">
        	<div class="book-list">
	        	<ul class="list-photo">
	        		<li style="text-align:left">
	        			<c:set var="i" value="${i+1}"/>
	        			<span class="ranking">${i}</span>
	        		</li>
	        		<li>
	        			<a href="${pageContext.request.contextPath}/book/detail.do?bk_num=${book.bk_num}">
	        				<img src="${pageContext.request.contextPath}/upload/${book.thumbnail}" width="100" height="150">
	        			</a>
	        		</li>
	        	</ul>
	        	<ul  class="list-text" style="text-align:center;">
	        		<li >
	        			<div class="list-author-publisher">
	        				<b>${book.author}</b>/${book.publisher}
	        			</div>
	        		</li>
	        	</ul>
	        </div>
        	</c:forEach>
        	</div>
        </div>
       </aside>
        
        
      
       </div> <!-- 메인 세개 정렬 -->
       
       
        	<div class="bestseller">
        	<div class="title"  style="margin-right:5%;">주인장<br> 추천</div>
        	<c:forEach var="book" items="${book_list}">
        			<div id="seller_list" class="card" style="width: 18rem; margin-right:5%;"> 
				        <a href="${pageContext.request.contextPath}/book/detail.do?bk_num=${book.bk_num}">
				        	<img src="${pageContext.request.contextPath}/upload/${book.thumbnail}" class="card-img-top" alt="...">
				        		<div class="card-body">
				        			<p class="card-text">${book.author}/${book.publisher}</p>
				        		</div>
				        </a>
        			</div>
        	</c:forEach>
        	</div>
        
        

    
<%
String[] strArr = new String[10];

strArr[0] = "실수는 충만한삶을 위해 반드시 치러야 할 비용이다.<br> -소피아 로렌";
strArr[1] = "용기는 스스로가 죽을만큼 두려워 떨고 있다는 것을 자신만이 아는 것이다. <br>-해럴드 윌슨";
strArr[2] = "인생이란 누구나 한 번쯤 시도해 볼 만한 것이다.<br> -헨리 J.틸만";
strArr[3] = "불의가 없다면 인간은 정의를 알지 못할 것이다. -헤라클레이토스";
strArr[4] = "우리 모두 살면서 몇 번의 실패를 겪는다. 이것이 바로 우리를 성공할 수 있도록 준비시킨다.<br> -랜디 K.멀홀랜드";
strArr[5] = "명심하라. 성공한 자들은 어떤일이 잘못되면 그 책임과 원인을 자기 자신에서 찾는다. 절대 세상 속에서 핑계를 찾지 않는다는 말이다.<br> -세이노의 가르침 中";
strArr[6] = "인생에는 서두르는 것 말고도 더 많은 것이 있다.<br> -마하트마 간디";
strArr[7] = "사랑하는 것은 천국을 살짝 엿보는 것이다.<br> -카렌 선드";
strArr[8] = "유한한 실망을 받아들임과 동시에 무한한 희망을 잃지 마라.<br> -마틴 루터 킹";
strArr[9] = "인내와 지혜는 떼려야 뗄 수 없다.<br> -성 아우구스티누스";
			double randomValue  = Math.random();
			int intVal = (int)(randomValue *10)+1;
%>   
    <div class="fightingwrap">
	    <img  class="fightingimg" src="${pageContext.request.contextPath}/upload/note.jpg">
	    <div class="fighting" >
	      <div class=fwords style="margin-bottom=1px;">오늘의 한마디</div>
	      	<span class="fightingtext" style="font-size:35px;"><br><%=strArr[intVal]%><br><br></span>
	   	  </div> 
	 	</div>
 	</div>
 </section>
 <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>




