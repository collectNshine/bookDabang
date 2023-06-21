<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맞춤도서 신청</title>
<style>

	
	h2{
		clear:both;
		float:left;
	}
	.search li{
		float:right;
	}
	.result-display{
		clear:both;
	}
	
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#search_form').submit(function(){
			if($('#keyword').val().trim()==''){
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
<div class="content-main">
	<h2>도서 신청</h2>
	<!-- 검색창 시작 -->
	<form id="search_form" action="list.do" method="get">
		<ul class="search">
			<li>
				<select name="keyfield"> 
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자</option>
					<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="조회">
			</li>
		</ul>
	</form>
	<!-- 검색창 끝 -->
	<!-- 신청목록리스트 시작 -->
		<c:if test="${count == 0 }">
		<div class="result-display">
			신청된 도서가 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			<tr>
				<th>진행상태</th>
				<th>제목</th>
				<th>저자</th>
				<th>출판사</th>
				<th>신청일</th>
				<th>추천수</th>
			</tr>
			<c:forEach var="request" items="${list}">
			<tr>
				<td>진행상태</td>                         <%--추가완료/준비중 이미지 넣어야해!! --%>
				<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a></td>
				<td>${request.req_author}</td>
				<td>${request.req_publisher}</td>
				<td>${request.req_date}</td>
				<td>추천수</td>							<%--추천수이미지랑 추천수count 넣어야해!! --%>
			</tr>
			</c:forEach>
		</table>
			<div class="align-center">${page}</div>
			</c:if>
	</div>
	<!-- 신청목록리스트 끝 -->
	<div class="list-space align-right">
		<input type="button" value="글쓰기" id="wbutton" onclick="location.href='writeForm.do'" 
			<c:if test="${empty user_num}">disabled="disabled"</c:if>>
	</div>
	</div>
</div>
</body>
</html>