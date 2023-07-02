<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>서평 목록</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/post_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
	<hr size="1" noshade width="100%">
	<!-- [1. 서평 모음집] 시작 -->
	<div id="postfeed" class="tab_contents on">
		<div class="content-main container">
		<br>
		<h2 style="margin-left: -310px;"><a href="list.do"><b>서평 모음집</b></a></h2>
		<br>
		<br>
		<c:if test="${count == 0}">
			<div class="result-display">
				서평이 존재하지 않습니다.
			</div>		
		</c:if>
		
		<c:if test="${count > 0}">
		<div class="row row-cols-1 row-cols-lg-4 g-4" style="width: 2000px; margin-left: -220px;">
		<c:forEach var="post" items="${list}">
  			<div id="post-list" class="col">
    			<div class="card h-100">
    			<br>
    				<div>
         			<c:if test="${!empty post.photo}">
        				<div id="profile">
           					&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/upload/${post.photo}" width="50" height="50" class="my-photo">
           					<br>
	        			</div>
	        			<div id="profile2"><b>${post.name}</b><p class="profile3">서평&nbsp;&nbsp;·&nbsp;${post.post_date}</p></div>
	         		</c:if>
	         		<c:if test="${empty post.photo}">
	        		 	<div id="profile">
	            			&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/face.png" width="50" height="50" class="my-photo">
	         				<br>
	         			</div>
	         			<div id="profile2"><b>${post.name}</b><p class="profile3">서평&nbsp;&nbsp;·&nbsp;${post.post_date}</p></div>
	         		</c:if>
	         		</div>
  				<div class="card-body">
  				<div class="post-thumbnail">
  				<ul class="post-thumbnail">
				<li>
					<a href="detail.do?post_num=${post.post_num}">
					<img src="${pageContext.request.contextPath}/upload/${post.thumbnail}" width="150" height="200" class="list-thumbnail">
					</a>
				</li>
				</ul>
				</div>
				<div class="card-detail">
    				<h5 class="card-title"><b><a href="detail.do?post_num=${post.post_num}">${post.post_title}</a></b></h5>
    				<div class="card-text" style="height: 85px; width: 350px;">
    				<p><a href="detail.do?post_num=${post.post_num}">${post.post_content}</a></p>
    				</div>
    			</div>
    			<div class="card-footer">
    				<a href="${pageContext.request.contextPath}/book/detail.do?bk_num=${post.bk_num}" class="btn btn-primary" style="color:white; margin-top: 6px;width: 137px; height: 33px; font-size: 12px; ba">도서 상세 보러가기</a>
    				<small class="footer1"><img class="fav" src="../images/fav02.png" width="40" height="40" style="margin-right: -8px; margin-left: 5px;"> ${post.cnt}</small>
       				<small class="footer2"><a href="detail.do?post_num=${post.post_num}"><img class="reply" src="../images/reply.png" width="20" height="20" style="margin-right: 5px;">${post.rcnt}</a></small>
      			</div>
  				</div>
				</div>
			</div>
		</c:forEach>	
		</div>		
		<div class="align-center">${page}</div>
		</c:if>
		</div>
	</div>
	<!-- [1. 서평 모음집] 끝 -->
	<%--
	<!-- [2. 한줄기록] 시작 -->
	<div id="reviewfeed" class="tab_contents on">
		<div class="content-main container">
		<h2><a href="listReview.do">한 줄 기록 모음집</a></h2>
		<br>
		<c:if test="${reCount == 0}">
			<div class="result-display">
				한 줄 기록이 존재하지 않습니다.
			</div>		
		</c:if>
		
		<c:if test="${reCount > 0}">
		<div class="row row-cols-1 row-cols-md-3 g-4">
		<c:forEach var="review" items="${reList}">
  			<div id="review-list" class="col">
    			<div class="card h-100">
    			<br>
    				<div>
         			<c:if test="${!empty review.photo}">
        				<div id="profile">
           					&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/upload/${review.photo}" width="50" height="50" class="my-photo">
           					<br>
	        			</div>
	        			<div id="profile2"><b>${review.name}</b><p>한 줄 기록&nbsp;&nbsp;·&nbsp;${review.review_date}</div>
	         		</c:if>
	         		<c:if test="${empty review.photo}">
	        		 	<div id="profile">
	            			&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/face.png" width="50" height="50" class="my-photo">
	         				<br>
	         			</div>
	         			<div id="profile2"><b>${review.name}</b><p>서평&nbsp;&nbsp;·&nbsp;${review.review_date}</div>
	         		</c:if>
	         		</div>
  				<div class="card-body">
  				<p class="card-text"><a href="listReview.do?review_num=${review.review__num}">${review.review_content}</a></p>
    			<div class="card-footer">
    				<small class="text-muted"><img class="fav" src="../images/like.png" width="33" height="33"><!-- ${favcount} --></small>
      			</div>
      			<br>
  				</div>
				</div>
			</div>
		</c:forEach>	
		</div>		
		<div class="align-center">${rePage}</div>
		</c:if>
		</div>
	</div>
	<!-- [2. 한줄기록 모음집] 끝 -->
	--%>
		<script type="text/javascript">
			$('.tabWrap li').click(function(){
				const getID = $(this).attr('data-tab')
				console.log(getID)
				$(this).addClass('on').siblings().removeClass('on')
				$('.tab_contents').removeClass('on')
				$("#" + getID).addClass('on')
			})
		</script>
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>