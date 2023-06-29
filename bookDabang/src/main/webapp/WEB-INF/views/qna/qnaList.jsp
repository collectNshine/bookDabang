<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1 문의 게시판</title>
<style type="text/css">
#deleted{
color:red;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/request_style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div>
<h1>1:1 문의</h1>
<input type="button" value="글쓰기" class="btn btn-outline-secondary" onclick="location.href='qnaWriteForm.do'">
	<table>
		<tr>
			<th>순서</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="qna" items="${list}">
		<tr>
			<td>${qna.qna_num}</td>
			<c:choose>
	 			<c:when test="${qna.delflag eq 0}">
					<td style="text-align:left;">
						<c:forEach begin="0" end="${qna.depth}" var="nbsp" step="1">
							&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
						<c:if test="${qna.depth != 0 }">
						ㄴ
						</c:if>
					<a href="qnaDetail.do?qna_num=${qna.qna_num}">${qna.qna_title}</a>
					</td>
				</c:when>
			<c:otherwise>
			<td id="deleted" style="text-align:left;">삭제된 게시물입니다.</td>
			</c:otherwise>
			</c:choose>
			<td>${qna.name}</td>
			<td>${qna.reg_date}</td>
		</tr>
		</c:forEach>
	</table>
	<div style="text-align:center;" >
	 ${page}
	 </div>
</div>
</body>
</html>