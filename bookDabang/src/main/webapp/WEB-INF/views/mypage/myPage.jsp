<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

</script> 
</head>
<body> 
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr size="1" noshade="noshade" width="100%">
		<h2>마이페이지</h2>
	<hr size="1" noshade="noshade" width="100%">
	<div class="mypage-div">
	<!-- 프로필 사진 시작 -->
		<ul>
			<li>
				<c:if test="${empty member.photo}">
					<img src="${pageContext.request.contextPath}/images/face.png" 
							width="200" height="200" class="my-photo">
					<input type="button" value="정보 수정" onclick="location.href='modifyUserForm.do'">
				</c:if>
				<c:if test="${!empty member.photo}">
					<img src="${pageContext.request.contextPath}/upload/${member.photo}" 
						 width="200" height="200" class="my-photo">
					 <input type="button" value="정보 수정" onclick="location.href='modifyUserForm.do'">
					 <input type="button" value="비밀번호 변경" onclick="location.href='modifyPasswordForm.do'">
				</c:if>
			</li>		
		</ul>
		<!-- 프로필 사진 끝 -->

				
		<!-- 사용자 마이페이지 메뉴 시작 -->
		<c:if test="${!empty user_num && user_auth == 1}">
		<ul class="tabWrap">
			<li data-tab="book_mark" style="cursor: pointer;" class="on">책갈피</li>
			<li data-tab="post" style="cursor: pointer;">작성글</li>
			<li data-tab="order" style="cursor: pointer;">주문목록</li>
		</ul>
		<div id="book_mark" class="tab_contents on">
			<table>
				<tr>
					<th>NO.</th>
					<th>도서명</th>
					<th>저자</th>
					<th>등록일</th>
				</tr>
				 <c:forEach var="book_mark" items="${book_mark}"> 
				<tr>
					<td>${bookmark.bk_num}</td>
					<td>${bookmark.title}</td>
					<td>${bookmark.author}</td>
					<td>${bookmark.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div id="post" class="tab_contents">
			<table>
				<tr>
					<th>NO.</th>
					<th>제목</th>
					<th>내용</th>
					<th>등록일</th>
				</tr>
				<c:forEach var="post" items="${post}"> 
				<tr>
					<td>${post.post_num}</td>
					<td>${post.post_title}</td>
					<td>${post.post_content}</td>
					<td>${post.post_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div id="order" class="tab_contents">
			<table>
				<tr>
					<th>NO.</th>
					<th>주문명</th>
					<th>가격</th>
					<th>주문일</th>
				</tr>
				<c:forEach var="order" items="${order}"> 
				<tr>
					<td>${order.order_num}</td>
					<td>${order.book_title}</td>
					<td>${order.order_total}</td>
					<td>${order.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		
		<script type="text/javascript">
			$('.tabWrap li').click(function(){
				const getID = $(this).attr('data-tab')
				console.log(getID)
				$(this).addClass('on').siblings().removeClass('on')
				$('.tab_contents').removeClass('on')
				$("#" + getID).addClass('on')
			})
		</script>	
		</c:if>
		
		
		
		<!-- 관리자 마이페이지 메뉴 시작 -->
		<c:if test="${!empty user_num && user_auth == 9}">
		<ul class="tabWrap">
			<li data-tab="admin_book" style="cursor: pointer;" class="on">도서관리</li>
			<li data-tab="admin_order" style="cursor: pointer;">주문관리</li>
			<li data-tab="admin_member" style="cursor: pointer;">회원관리</li>
			<li data-tab="admin_report" style="cursor: pointer;">신고내역</li>
		</ul>
		<!-- [1. 도서 관리] 시작 -->
		<div id="admin_book" class="tab_contents on">
			<div class="content-main container">
			<h2><a href="myPage.do">도서 관리</a></h2>
			<!-- 검색창 시작 : get방식 -->
			<form id="search_form" action="myPage.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>도서명</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자명</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
			</form>
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
			<!-- 검색창 끝 -->
		<div class="list-space align-right">
			<input type="button" value="도서 등록" onclick="location.href='${pageContext.request.contextPath}/book/writeForm.do'">
		</div>
		
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
					<td><a href="${pageContext.request.contextPath}/book/updateForm.do?bk_num=${book.bk_num}">${book.title}</a></td>
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
		</div>
		<!-- [1. 도서 관리] 끝 -->
	
	
		</div>
		<div id="admin_order" class="tab_contents">
			<table>
				<tr>
					<th>주문번호</th>
					<th>도서명</th>
					<th>아이디</th>
					<th>주문 상태</th>
					<th>총 주문 금액</th>
					<th>주문 날짜</th>
				</tr>
				<c:forEach var="admin_order" items="${post}"> 
				<tr>
					<td>${post.post_num}</td>
					<td>${post.post_title}</td>
					<td>${post.post_content}</td>
					<td>${post.post_date}</td>
					<td>${post.post_date}</td>
					<td>${post.post_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div id="admin_member" class="tab_contents">
			<table>
				<tr>
					<th>이름(닉네임)</th>
					<th>성별</th>
					<th>생년월일</th>
					<th>이메일</th>
					<th>계정 생성일</th>
					<th>최근 로그인 날짜</th>
				</tr>
				<c:forEach var="admin_member" items="${order}"> 
				<tr>
					<td>${order.order_num}</td>
					<td>${order.book_title}</td>
					<td>${order.order_total}</td>
					<td>${order.reg_date}</td>
					<td>${order.reg_date}</td>
					<td>${order.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div id="admin_report" class="tab_contents">
			<table>
				<tr>
					<th>신고 번호</th>
					<th>회원 번호</th>
					<th>신고 유형</th>
					<th>신고 내용</th>
					<th>신고 날짜</th>
				</tr>
				<c:forEach var="admin_report" items="${order}"> 
				<tr>
					<td>${order.order_num}</td>
					<td>${order.book_title}</td>
					<td>${order.order_total}</td>
					<td>${order.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		
		<script type="text/javascript">
			$('.tabWrap li').click(function(){
				const getID = $(this).attr('data-tab')
				console.log(getID)
				$(this).addClass('on').siblings().removeClass('on')
				$('.tab_contents').removeClass('on')
				$("#" + getID).addClass('on')
			})
		</script>
		</c:if>
		
		
	</div>
</div>
</body>
</html>
