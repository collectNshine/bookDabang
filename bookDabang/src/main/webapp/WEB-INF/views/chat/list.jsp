<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>담소마당</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
	<link href="https://hangeul.pstatic.net/hangeul_static/css/NanumGarMaesGeur.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#search_form').submit(function() {
				if($('keyword').val().trim() == '') {
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
		<!-- 내용 s -->
		<div class="content-main content-margin">
			<div class="chat-left">
				<div class="left-content">
				<!-- 검색창 S -->
				<form id="list_search_form" action="list.do" method="get" class="d-flex" role="search">
					<select name="keyfield" class="form-select">
						<option value="1" <c:if test="${param.keyfield == 1}">SELECTED</c:if>>제목</option>
					</select>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" class="form-control me-2">
					<input type="submit" value="조회" class="btn btn-outline-success">
				</form>
				<!-- 검색창 E -->
				<div class="list-space align-right">
					<input type="button" value="생성" class="btn btn-outline-success" id="make_chat">
				</div>
				<c:if test="${count == 0}">
				<div class="chat-empty">
					<span>표시할 담소가 없습니다.</span>
				</div>
				</c:if>
				<c:if test="${count > 0}">
				<!-- <div class="chatroom"></div> -->
				<ul class="list-group list-group-flush">
					<c:forEach var="chat" items="${list}">
					<li class="list-group-item">
						<div class="chatlist">
							<div class="chatlist-img">
								<!-- 파일 업로드 X : 기본 이미지 -->
								<c:if test="${empty chat.chat_img}">
								<img src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
								</c:if>
								<!-- 파일 업로드!! -->
								<c:if test="${!empty chat.chat_img}">
								<img src="${pageContext.request.contextPath}/upload/${chat.chat_img}" width="200" height="200" class="my-photo">
								</c:if>
							</div>
							<div class="chatlist-title align-center">
								<span><b>${chat.chat_title}</b></span>
							</div>
							<div class="chatlist-btn align-center">
								<input type="button" value="입장" class="btn btn-outline-success into-chat" data-chatnum="${chat.chat_num}" data-title="${chat.chat_title}" data-img="${chat.chat_img}">
								<c:if test="${user_num == chat.mem_num}">
								<input type="button" value="삭제" class="btn btn-outline-delete delete-chat" data-chatNum="${chat.chat_num}">
								</c:if>
							</div>
						</div>
					</li>
					</c:forEach>
				</ul>
				<div class="align-center">${page}</div>
				</c:if>
				</div>
			</div>
			<div class="chat-right">
				<div class="right-content">
				<%-- 채팅생성 div --%>
				<div class="make-chat" style="display:none;">
					<div class="make-chat-content"></div>
				</div>
				<%-- 채팅방 입장 전 보여지는 화면 --%>
				<div class="chatList-img">
					<h4 id="font_web">책에 대한 다양한 담소를 나누는 공간</h4>
					<img src="${pageContext.request.contextPath}/images/chatListImg.png">
				</div>
				<%-- 채팅방 div --%>
				<div class="chatRoom" style="display:none;"> 
					<div class="top-bar">
						<div id="chatTitle"></div>
						<div class="chatAlign-right"><input type="button" value="닫기" id="out_chat2" class="btn btn-outline-success btn-sm"></div>
					</div>
					<div class="bottom">
						<div class="chat-list scrollBar" style="overflow-y:scroll; height:500px;"></div>
						<div id="loading" style="display:none;">
							<img src = "${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">;
						</div>
						<div class="chat-input">
							<form class="write-chat">
								<textarea rows="2" cols="75" name="chat_content" class="chat-content form-control" placeholder="내용을 입력하세요" style="width:80%"></textarea>
								<input type="submit" value="보내기" class="chat-submit btn btn-success" style="width:15%">
							</form>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		<!-- 내용 e -->
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</div>
</body>
</html>