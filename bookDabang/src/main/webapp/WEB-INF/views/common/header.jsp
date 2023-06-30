<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> --%>
<!-- header 시작 -->
<div id="main_logo">
	<div id="logo">
		<a href="${pageContext.request.contextPath}/main/main.do">
		<img src="${pageContext.request.contextPath}/images/reallogo.png" width="220"></a>
	</div>
   <div id="main_menu">
      <ul class="menu-category">
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/introduction/intro.do">다방소개</a></li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/book/list.do">차림표</a></li>
         <li><b>|</b></li>
         <li class="menu-detail">
        	 <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" data-bs-toggle="dropdown">
         		모음집
        	 </a>
        	 <ul class="dropdown-menu" style="width:220px;">
  				<li><a class="dropdown-item" href="${pageContext.request.contextPath}/post/list.do">서평 모음집</a></li>
  				<li><a class="dropdown-item" href="${pageContext.request.contextPath}/book/listFeed.do">한 줄 기록 모음집</a></li>
  			</ul>
  		 </li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/chat/list.do">담소마당</a></li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/request/list.do">도서신청</a></li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/notice/noticeList.do">다방안내</a></li>
         <c:if test="${!empty user_num}">   <%--로그인 상태 --%>
         	<c:if test="${!empty user_photo}">
        	<li>
           		 <a href="${pageContext.request.contextPath}/mypage/myPage.do"><img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo"></a>
	        </li> 
	         </c:if>
	         <c:if test="${empty user_photo}">
	         <li>
	            <a href="${pageContext.request.contextPath}/mypage/myPage.do"><img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo" style="margin-right:20px;"></a>
	         </li>
	         </c:if>
	     <li>
	         <a href="${pageContext.request.contextPath}/cart/list.do"><img src="${pageContext.request.contextPath}/images/new_cart.png" width="25" height="25" style="margin-right:20px;"></a>
	     </li>
	     <li>
	         <input class="loginbtn" type="button" value="Logout" onclick="location.href='${pageContext.request.contextPath}/member/logout.do'" style="margin-right:20px;">
	     </li>
	     </c:if>

         <c:if test="${empty user_num}"> <%--로그아웃 상태 --%>
         <li>
            <input class="loginbtn" type="button" value="Login" onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'" style="margin-left:50px;">
         </li>
         </c:if>
      </ul>
   </div>

<!-- 
   <ul>
      <li>
         <a href="${pageContext.request.contextPath}/board/list.do">게시판</a>
      </li>
      <c:if test="${!empty user_num && user_auth == 9}">
      <li>
         <a href="${pageContext.request.contextPath}/member/memberList.do">회원관리</a>
      </li>

      <li>
         <a href="${pageContext.request.contextPath}/item/list.do">상품관리</a>
      </li>
      <li>
         <a href="${pageContext.request.contextPath}/order/list.do">주문관리</a>
      </li>
      </c:if>
      <c:if test="${!empty user_num && user_auth == 1}">
      <li>
         <a href="${pageContext.request.contextPath}/cart/list.do">장바구니</a>
      </li>
      <li>
         <a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
      </li>
      </c:if>
      <c:if test="${!empty user_num && !empty user_photo}">
      <li class="menu-profile">
         <img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
      </li>
      </c:if>
      <c:if test="${!empty user_num && empty user_photo}">
      <li class="menu-profile">
         <img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
      </li>
      </c:if>
      <c:if test="${!empty user_num}">
      <li class="menu-logout">
         [<span>${user_id}</span>]
         <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
      </li>
      </c:if>
      <c:if test="${empty user_num}">
      <li>
         <a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
      </li>
      </c:if>
   </ul>
 -->
</div>
<!-- header 끝 -->