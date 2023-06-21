<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_style.css">
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
		<div id="admin_book" class="tab_contents on">
			<table>
				<tr>
					<th>도서번호</th>
					<th>도서명</th>
					<th>아이디</th>
					<th>주문상태</th>
					<th>총 주문 금액</th>
					<th>주문날짜</th>
				</tr>
				 <c:forEach var="admin_book" items="${book_mark}"> 
				<tr>
					<td>${bookmark.bk_num}</td>
					<td>${bookmark.title}</td>
					<td>${bookmark.author}</td>
					<td>${bookmark.reg_date}</td>
					<td>${bookmark.reg_date}</td>
					<td>${bookmark.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
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
