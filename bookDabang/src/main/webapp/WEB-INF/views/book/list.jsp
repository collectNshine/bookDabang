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
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
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
	<div class="content-main">
		<hr size="1" noshade width="100%">
		<div class="start">
		<h2><b>차림표</b></h2>
		<h6 style="color:grey">책다방에서 마음의 양식을 채워보세요</h6>
		</div>
		<!-- 검색창 시작 : get방식 -->
		<form id="search_form" action="list.do" method="get" class="d-flex" role="search">
			<ul class="search">
				<li>
					<select name="keyfield" class="form-select">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>도서명</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자명</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" class="form-control me-2">
				</li>
				<li>
					<input type="submit" value="검색" class="btn btn-outline-success">
				</li>
			</ul>
		</form>
		<!-- 검색창 끝 -->

		<!-- 카테고리 시작 (미완성) -->
		<div class="list-group">
		  <a href="#" class="list-group-item list-group-item-action active" aria-current="true">
		    전체
		  </a>
		  <a href="#" class="list-group-item list-group-item-action">문학</a>
		  <a href="#" class="list-group-item list-group-item-action">경제/경영</a>
		  <a href="#" class="list-group-item list-group-item-action">인문</a>
		  <a href="#" class="list-group-item list-group-item-action">예술/대중문화</a>
		  <a href="#" class="list-group-item list-group-item-action">사회/정치</a>
		  <a href="#" class="list-group-item list-group-item-action">자연과학</a>
		  <a href="#" class="list-group-item list-group-item-action">자기계발</a>
		  <a href="#" class="list-group-item list-group-item-action">IT모바일</a>
		  <a href="#" class="list-group-item list-group-item-action">유아/어린이</a>
		  <a href="#" class="list-group-item list-group-item-action">만화</a>
		</div>
		<!-- 카테고리 끝 -->
		
		<!-- 목록 시작 (미완성) -->
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
		<!-- 목록 끝 -->
		
		
	<%-- 
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 상품이 없습니다.
			</div>		
		</c:if>
		
		<c:if test="${count > 0}">
		<table class="table table-hover align-center">
		<thead>
			<tr>
				<th>도서번호</th>
				<th>도서명</th>
				<th>저자명</th>
				<th>출판사</th>
				<th>재고</th>
				<th>분류</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${list}">
			<tr>
				<td>${book.bk_num}</td>
				<td><a href="updateForm.do?bk_num=${book.bk_num}">${book.title}</a></td>
				<td>${book.author}</td>
				<td>${book.publisher}</td>
				<td><fmt:formatNumber value="${book.stock}"/></td>
				<td>${book.category}</td>
				<td>${book.reg_date}</td>
			</tr>
			</c:forEach>
		</tbody>
		</table>
		<div class="align-center">${page}</div>
		</c:if>
		--%>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>