<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">

<title>공지사항</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<style type="text/css">
#nleft{
width:80%;
float:left;
padding:1rem;
}
#nright{
width:20%;
float:right;
padding:2rem;
}
#right{
width:80%;
float:right;

}
</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="content-main">
	<jsp:include page="/WEB-INF/views/notice/noticeSidebar.jsp"/>
		<div id="right">
			<ul>
				<li>
					<div id="nleft">
							<h3>${noti.noti_title}</h3>${noti. noti_date}
					</div>
				</li>
				<li/>
				<hr size ="1" width="100%" noshade="noshade" />
				<li><p>${noti.noti_content}<p></li>
				<hr size ="1" width="100%" noshade="noshade" />
				<!-- 관리자만 수정,삭제 가능 -->
				<c:if test="${user_auth == 9}">
				<li>
					<input type="button" class="btn btn-outline-success" value="수정" onclick="location.href='${pageContext.request.contextPath}/notice/noticeEditForm.do?noti_num=${noti.noti_num}'">
					<input id="delete_btn" class="btn btn-outline-delete" type="button" value="삭제" >
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
						location.replace('noticeDelete.do?noti_nums=${noti.noti_num}');
					}
				});
			});
		</script> 
	</div>
</div>
</body>
</html>
