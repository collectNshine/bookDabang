<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<div id="main_logo">
	<div id="logo">
		<a href="${pageContext.request.contextPath}/main/main.do">
		<img src="${pageContext.request.contextPath}/images/logo.PNG" width="150"></a>
	</div>
   <div id="main_menu">
      <ul class="menu-category">
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/book/list.do">차림표</a></li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/post/list.do">모음집</a></li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="">담소마당</a></li>
         <li><b>|</b></li>
         <li class="menu-detail"><a href="${pageContext.request.contextPath}/request/list.do">맞춤도서 신청</a></li>
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
	            <a href="${pageContext.request.contextPath}/mypage/myPage.do"><img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo"></a>
	         </li>
	         </c:if>
	     <li>
	         <a href=""><img src="${pageContext.request.contextPath}/images/new_cart.png" width="25" height="25"></a>
	     </li>
	     <li>
	         <input type="button" value="Logout" onclick="location.href='${pageContext.request.contextPath}/member/logout.do'">
	     </li>
	     </c:if>

         <c:if test="${empty user_num}"> <%--로그아웃 상태 --%>
         <li>
            <input type="button" value="Login" onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">
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