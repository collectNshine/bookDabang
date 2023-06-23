<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서신청 상세페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<style type="text/css">
	/* h2{
		position: relative;
		right: 750px;
		top:70px;
	} */
	
	
/* 	#list-title {
		
		border:1px solid gray;
		background-color:gray;
		block:inli
		padding-top:80px;
		display:flex; 
		width:100%;
	}
	.content-main{
		display:flex;	
		flex-direction:column;
		position:relative;
		right:800px;
		top:50px;
	}
	 */

</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>도서 신청</h2>
		
		<div id="list-title">
			<ul>
				<li><h3>${request.req_title} / ${request.req_author}</h3></li>
				<c:choose>
					<c:when test="${request.req_modifydate!=null}">
						<li>${request.req_modifydate}</li>
					</c:when>
					<c:otherwise>
						<li>${request.req_date}</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<ul>
			<li><input type="hidden" value="${request.req_num}"> </li>
		</ul>
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
	 	
	 	<!-- 좋아요 버튼 넣어야함!! -->
	 	<!-- 좋아요 버튼 끝 -->
	 	<c:if test="${user_num == request.mem_num}">
	 	<div class="align-center">
		 	<input type="button" value="수정" onclick="location.href='modifyForm.do?req_num=${request.req_num}'">
		 	<input type="button" value="삭제" id="delete_btn">
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
	 	</c:if>
	 	
	</div>
</div>

</body>
</html>