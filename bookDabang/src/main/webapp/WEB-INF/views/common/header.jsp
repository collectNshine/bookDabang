<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>북다방</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header_style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#info').click(function(){
		$('#menu1').css({ display:'block'});
		$('#menu2').css({ display:'none'});
	});
	$('#menu1').mouseleave(function(){
		$('#menu1').slideUp(); 
	});
	
	$('#review').click(function(){
		$('#menu2').css({ display:'block'});
		$('#menu1').css({ display:'none'});
	});
	$('#menu2').mouseleave(function(){
		$('#menu2').slideUp(); 
	});

});
</script>
</head>
<body>
    <header class="header">
        <div class="header__logo">
            <a href="${pageContext.request.contextPath}/main/main.do">
               <img class="img" src="${pageContext.request.contextPath}/images/colorlogo.png"  width="200" > 
            </a>
        </div> 
        <div class="header__login">
            <div>
              <c:if test="${empty user_num}"><!-- 로그인을 안 한 사용자 -->
	               <img src="${pageContext.request.contextPath}/images/face.png" id="user_face">
	               <a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
	               <b>|</b>
	               <a href="${pageContext.request.contextPath}/member/joinForm.do">회원가입하기</a> 
              </c:if>
              <c:if test="${!empty user_num}"><!-- 로그인을 한 사용자 -->
	               <c:if test="${!empty user_photo}"><!-- 프로필 사진 있음 -->
	               		<a href="${pageContext.request.contextPath}/mypage/myPage.do">
	               		<img src="${pageContext.request.contextPath}/upload/${user_photo}" id="user_face">
	               		</a>
	               		<a href="${pageContext.request.contextPath}/mypage/myPage.do">${user_nickname}님</a>
	               		<b>|</b>
	               		<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a> 
	               	</c:if>
	               	<c:if test="${empty user_photo}"><!-- 프로필 사진 없음 -->
	               		 <a href="${pageContext.request.contextPath}/mypage/myPage.do">
	               		 <img src="${pageContext.request.contextPath}/images/face.png" id="user_face">
	               		 </a>
	               		 <a href="${pageContext.request.contextPath}/mypage/myPage.do">${user_nickname}님</a>
	               		<b>|</b>
	               		<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a> 
	               	</c:if>
	              </c:if>
            	</div>
       		 </div>
	        <div class="cart"><!-- 카트 단에서 로그인 접속자/ 비로그인 접속자 차단 -->
			       <a href="${pageContext.request.contextPath}/cart/list.do">
			          <img id="cart" src="${pageContext.request.contextPath}/images/cart_main.png" width="40px">
		          </a>
	        </div>
        <div class="header__gnb">
            <ul>
                <li id="info"> 책다방</li>
                <li><a href="${pageContext.request.contextPath}/book/list.do">차림표</a></li>
                <li id="review">모음집</li>
                <li><a href="${pageContext.request.contextPath}/chat/list.do">담소마당</a></li>
                <li><a href="${pageContext.request.contextPath}/request/list.do">도서신청</a></li>
            </ul>
        </div>
        <div id="menu1">
        	<div></div>
            <a href="${pageContext.request.contextPath}/introduction/intro.do">다방 소개</a><br>
           <a href="${pageContext.request.contextPath}/notice/noticeList.do">다방 안내</a>
        </div>
        <div id="menu2">
       	 <div></div>
                <a href="${pageContext.request.contextPath}/post/list.do">서평</a><br>
                <a href="${pageContext.request.contextPath}/book/listFeed.do">한 줄 기록</a>
         </div>
    </header>
</body>
</html>