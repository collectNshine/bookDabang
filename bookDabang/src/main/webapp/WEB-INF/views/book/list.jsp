<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>차림표 | 책다방</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script> 
<script type="text/javascript">
	$(function(){
		$('#list_search_form').submit(function(){
			if($('#keyword').val().trim() == ''){
				alert('검색어를 입력하세요');
				$('#keyword').val('').focus();
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
	<div class="list-main">
		<div class="start">
		<h2><b>차림표</b></h2>
		<h6 style="color:grey">책다방에서 마음의 양식을 채워보세요</h6>
		</div>
		<!-- 검색창 시작 : get방식 -->
		<form id="list_search_form" action="list.do" method="get" class="d-flex" role="search">
		<input type="hidden" name="category" value="${param.category}">
		<input type="hidden" name="bk_num" value="${book.bk_num}">
			<select name="keyfield" class="form-select">
				<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>도서명</option>
				<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자명</option>
			</select>
			<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" class="form-control me-2">
			<input type="submit" value="검색" class="btn btn-outline-success">
		</form>
		<!-- 검색창 끝 -->

		<!-- 카테고리 시작 -->
		<div class="list-group">
		  <a href="list.do" class="list-group-item list-group-item-action <c:if test="${empty param.category}">active</c:if>"><b>전체</b></a>
		  <a href="list.do?category=문학" class="list-group-item list-group-item-action <c:if test="${param.category == '문학'}">active</c:if>">문학</a>
		  <a href="list.do?category=경제/경영" class="list-group-item list-group-item-action <c:if test="${param.category == '경제/경영'}">active</c:if>">경제/경영</a>
		  <a href="list.do?category=인문" class="list-group-item list-group-item-action <c:if test="${param.category == '인문'}">active</c:if>">인문</a>
		  <a href="list.do?category=예술/대중문화" class="list-group-item list-group-item-action <c:if test="${param.category == '예술/대중문화'}">active</c:if>">예술/대중문화</a>
		  <a href="list.do?category=사회/정치" class="list-group-item list-group-item-action <c:if test="${param.category == '사회/정치'}">active</c:if>">사회/정치</a>
		  <a href="list.do?category=자연과학" class="list-group-item list-group-item-action <c:if test="${param.category == '자연과학'}">active</c:if>">자연과학</a>
		  <a href="list.do?category=자기계발" class="list-group-item list-group-item-action <c:if test="${param.category == '자기계발'}">active</c:if>">자기계발</a>
		  <a href="list.do?category=IT모바일" class="list-group-item list-group-item-action <c:if test="${param.category == 'IT모바일'}">active</c:if>">IT모바일</a>
		  <a href="list.do?category=유아/어린이" class="list-group-item list-group-item-action <c:if test="${param.category == '유아/어린이'}">active</c:if>">유아/어린이</a>
		  <a href="list.do?category=만화" class="list-group-item list-group-item-action <c:if test="${param.category == '만화'}">active</c:if>">만화</a>
		</div>
		<!-- 카테고리 끝 -->
		
		<!-- 목록 시작 -->
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 도서가 없습니다.
		</div>
		</c:if>
		
		<c:if test="${count > 0}">
		<c:forEach var="book" items="${list}">
		<div class="book-list">
			<ul class="list-photo">
				<li>
					<a href="detail.do?bk_num=${book.bk_num}">
					<img src="${pageContext.request.contextPath}/upload/${book.thumbnail}" width="150" class="list-thumbnail">
					</a>
				</li>
			</ul> 
			<ul class="list-text">
				<li>
					<div class="list-title">
						<a href="detail.do?bk_num=${book.bk_num}"><b>${book.title}</b></a>
					</div>
				</li>
				<li>
					<div class="list-author-publisher">
						${book.author} | ${book.publisher}
					</div>
				</li>
				<li>
					<div class="list-price">
					<fmt:formatNumber value="${book.price}"/>원
					</div>
				</li>
				<li>
					<div class="list-content">
						${book.content}
					</div>
				</li>
			</ul>
			<hr size="1" noshade width="80%" align="right">
		</div>
		</c:forEach>
		<div class="page-button">${page}</div>
		</c:if>
		<!-- 목록 끝 -->
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>