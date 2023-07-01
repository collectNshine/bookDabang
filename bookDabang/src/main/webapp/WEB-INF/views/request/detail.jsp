<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>도서신청 상세페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/request.fav.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>	
<div class="page-main">
	

			<h2>도서 신청</h2>
		
		<header>
			<div class="detail_head">
				<span class="detail_head1">${request.req_title} / ${request.req_author}</span>
			</div>
			<div class="detail_head">
				<c:choose>
						<c:when test="${request.req_modifydate!=null}">
							<span class="detail_head2">${request.req_modifydate}</span>
						</c:when>
						<c:otherwise>
							<span class="detail_head2">${request.req_date}</span>
						</c:otherwise>
					</c:choose>
			</div>
		</header>
		<article>
		<ul>
			<li><input type="hidden" value="${request.req_num}"> </li>
			<li><input type="hidden" value="${request.mem_num}"></li>
			<li><input type="hidden" value="${request.clicked}"></li>
			<li><input type="hidden" value="${request.cnt}"></li>

			
		</ul>
		<div class="detail_main">
			<ul>
				<li>작성자</li>
				<li>${request.id}</li>
			</ul>
			<ul>
				<li>책 제목</li>
				<li>${request.req_title}</li>
			</ul>
			<ul>
				<li>저자</li>
				<li>${request.req_author}</li>
			</ul>
			<ul>
				<li>출판사</li>
				<li>${request.req_publisher}</li>
			</ul>
			<ul>
				<li>기타</li>
				<li>${request.req_etc}</li>
			</ul>
		</div>
	 	</article>
		<footer>
	 	<div class="detail_button">
	 		 	<!-- 좋아요 버튼-->
		 	<div class="detail_fav">
			 	<c:forEach var="request" items="${list}">
			 	<c:choose>
				 	<c:when test="${request.clicked == 'clicked'}">
						 <img class="output-fav" data-num="${request.req_num}" src="${pageContext.request.contextPath}/images/fav02.png" width="50"> 	
					</c:when>
					<c:when test = "${empty request.clicked}"> 
						<img class="output-fav" data-num="${request.req_num}" src="${pageContext.request.contextPath}/images/fav01.png" width="50"> 
					</c:when>
				</c:choose>
				<br><span class="output-fcount">${request.cnt}</span>
				</c:forEach>
			</div>
		 	<!-- 좋아요 버튼 끝 -->
		 
		 	<div class="align-center">
		 		<button type="button" class="btn btn-outline-secondary" onclick="location.href='list.do'">목록</button>
		 		<c:if test="${user_num == request.mem_num}">
		 		<button type="button" class="btn btn-outline-secondary" onclick="location.href='modifyForm.do?req_num=${request.req_num}'">수정</button>
		 		<button type="button" class="btn btn-outline-secondary" id="delete_btn">삭제</button>
			 	</c:if>
			 	<script type="text/javascript">
			 		let delete_btn = document.getElementById('delete_btn');
			 		delete_btn.onclick=function(){
			 			let choice = confirm('정말로 삭제하시겠습니까?');
			 			if(choice){
			 				location.replace('delete.do?req_num=${request.req_num}');
			 			}
			 		};
			 	</script>
			 	
		 	</div>
		 </div>
		</footer>
</div>
</html>