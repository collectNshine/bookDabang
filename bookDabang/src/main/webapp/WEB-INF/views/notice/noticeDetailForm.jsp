<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 자세히 보기</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div>
	<ul>
		<li><h1>${noti.noti_title}</h1></li>
		<li>${noti. noti_date}</li>
		<li/>
		<hr size ="1" width="100%" noshade="noshade" />
		<li><p>${noti.noti_content}<p></li>
		<hr size ="1" width="100%" noshade="noshade" />
		<!-- 관리자만 수정,삭제 가능 -->
		<c:if test="${user_auth == 9}">
		<li>
			<input type="button" value="수정" onclick="location.href='${pageContext.request.contextPath}/notice/noticeEditForm.do?noti_num=${noti.noti_num}'">
			<input id="delete_btn" type="button" value="삭제" >
		</li>
		</c:if>
		<!-- 관리자만 수정,삭제 가능 -->
	</ul>
</div>
<script type="text/javascript">
	$(function(){
		$('#delete_btn').click(function(){
			let choice = confirm('삭제하겠습니까?');
			if(choice){
				location.replace('noticeDelete.do?noti_num=${noti.noti_num}');
			}
		});
	});
</script> 
</body>
</html>