<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맞춤도서 신청</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/request.fav.js"></script>
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
				<select class="req_search1" name="keyfield"> 
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자</option>
					<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
				</select>
			</li>
			<li>
				<input class="req_search2" type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input class="req_search3" type="submit" value="조회">
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
				<c:if test="${request.req_state == 0}">
					<td class="align-center"><button>준비중</button></td>
				</c:if>
				<c:if test="${request.req_state == 1}"> 
					<td class="align-center"><button>추가완료</button></td>
				</c:if>	                  
				<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a></td>
				<td>${request.req_author}</td>
				<td>${request.req_publisher}</td>
					<c:if test="${!empty request.req_modifydate}">
						<td>${request.req_modifydate}</td>
					</c:if>
					<c:if test="${empty request.req_modifydate}">
						<td>${request.req_date}</td>
					</c:if>
				<td>
					<c:if test="${request.clicked != 'clicked'}">
						<img class="output-fav" data-num="${request.req_num}" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
					</c:if>
					<c:if test="${request.clicked == 'clicked'}">
						<img class="output-fav" data-num="${request.req_num}" src="${pageContext.request.contextPath}/images/fav02.gif" width="50">
					</c:if>
					<span class="output-fcount">${request.cnt}</span>	
				</td>							
			</tr>
			</c:forEach>
		</table>
			<div class="align-center">${page}</div>
			</c:if>
	</div>
	<!-- 신청목록리스트 끝 -->
	<div class="list-space align-right">
		<!-- <input class="req_writebtn" type="button" onclick="location.href='writeForm.do'"> -->
		<input type="button" value="글쓰기" id="wbutton" onclick="location.href='writeForm.do'"> 
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
	</div>
	</div>
</div>
</body>
</html>