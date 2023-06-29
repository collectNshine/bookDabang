<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">

<title>도서신청</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
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
	<hr size="1" noshade width="100%">
	<h2><a href="list.do">도서 신청</a></h2>
	<!-- 검색창 시작 -->
	<form id="search_form" action="list.do" method="get" class="d-flex" role="search">
			<select class="form-select" name="keyfield"> 
				<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
				<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자</option>
				<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
			</select>
			<input class="form-control me-2" type="search"  size="16" name="keyword" id="keyword" value="${param.keyword}">
			<input class="btn btn-outline-success" type="submit" value="검색">
	</form>
	<!-- 검색창 끝 -->
	<!-- 신청목록리스트 시작 -->
		<c:if test="${count == 0 }">
		<div class="result-display">
			신청된 도서가 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table  class="table table-hover">
			<colgroup>
		    	<col width="15%" />
		        <col width="25%" />
		        <col width="15%" />
		    	<col width="15%" />
		        <col width="15%" />
		        <col width="15%" />
		    </colgroup>
			<thead class="table-light align-center">
				<tr>
					<th></th>
					<th>제목</th>
					<th>저자</th>
					<th>출판사</th>
					<th>신청일</th>
					<th>추천수</th>
				</tr>
			</thead>
			<tbody class="align-center">
				<c:forEach var="request" items="${list}">
				<tr>
					<c:if test="${request.req_state == 0}">
						<td class="align-center"><button>준비중</button></td>
					</c:if>
					<c:if test="${request.req_state == 1}"> 
						<td class="align-center"><button>추가완료</button></td>
					</c:if>	                  
					<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a></td>
					<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a>${request.req_author}</td>
					<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a>${request.req_publisher}</td>
						<c:if test="${!empty request.req_modifydate}">
							<td>${request.req_modifydate}</td>
						</c:if>
						<c:if test="${empty request.req_modifydate}">
							<td>${request.req_date}</td>
						</c:if>
					<td>
						<c:if test="${request.clicked != 'clicked'}">
							<img class="output-fav" data-num="${request.req_num}" src="${pageContext.request.contextPath}/images/fav01.png" width="50"> 
						</c:if>
						<c:if test="${request.clicked == 'clicked'}">
							<img class="output-fav" data-num="${request.req_num}" src="${pageContext.request.contextPath}/images/fav02.png" width="50">
						</c:if>
						<span class="output-fcount">${request.cnt}</span>	
					</td>							
				</tr>
				</c:forEach>
			</tbody>
		</table>
			<div class="align-center">${page}</div>
			</c:if>
	</div>
	<!-- 신청목록리스트 끝 -->
	<div class="list-space align-right">
		<input type="button" value="글쓰기" class="btn btn-secondary" onclick="location.href='writeForm.do'"> 
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
	</div>
	</div>
</div>
</body>
</html>