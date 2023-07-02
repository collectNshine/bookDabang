<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
  
<title>공지사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#list_search_form').submit(function(event){
			if($('#keyword').val().trim()==""){
				$('#guide').text('검색어를 입력해주세요.').css('color','#F00');
			event.preventDefault();
			return;
			}
		});
		
		//전체 체크박스 선택, 전체 체크박스 취소
		let btn_status = false;
		$('#all_btn').click(function(){
			if(btn_status == false){
				$('.checkbox').prop('checked', true);
				$('#all_btn').attr('value','전체선택 취소');
				btn_status = true;
			}else{
				$('.checkbox').prop('checked', false);
				$('#all_btn').attr('value','전체선택');
				btn_status = false;
			}
		});//end of #all_btn
		
		
		//삭제 버튼 클릭	
		$('#del_btn').click(function(){
			let noti_nums = new Array();
			
			let list = $('.checkbox');
				for(let i=0;i<list.length;i++){//선택되어있으면 배열에 값을 저장함.
					if(list[i].checked){
						noti_nums.push(list[i].value);//input 태그의 value...는 아니고 
					}
				}
			
			$.ajax({
				url:'noticeDeleteAjax.do',
				type:'post',
				traditional:true,
				data:{
					'noti_nums':noti_nums
				},
				success:function(){
						alert('삭제되었습니다.');
				},
				error:function(){
					alert('선택 값이 없습니다.');
				}
			});
			 location.reload(); //현재 페이지 새로고침
		});//end of #del_btn
		
		$('input').keydown(function(){
			$('#guide').text('');
		});
	});
</script> 
<style>
body{
margin:auto;
}
#right{
width:80%;
float:right;

}
#search{
padding:2rem;
}
</style>
</head>
<body>
<div class="page-main">
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="content-main">
	<jsp:include page="/WEB-INF/views/notice/noticeSidebar.jsp"/>
	<!-- 검색 시작 -->
	<div id="right">
		<div id="search" style="text-align:center;">
		<p id="guide"></p>
			<form id="list_search_form" action="noticeList.do" method="post" class="d-flex" role="search" style="border:none;">
				<input name="noti_category" type="hidden" value="${noti_category}">
				
					<select name="keyfield" class="form-select" style="width:100px;">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
					</select>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" class="form-control me-2">
					<input type="submit" value="검색" class="btn btn-outline-success">
			</form>
		</div>
	<!-- 검색 끝 -->
	<!-- 테이블 시작 -->
	<c:if test="${count > 0}">
		<table class="table table-hover">
			<tr class="table-light align-center">
				<th></th>
				<th>카테고리</th>
				<th style="width:50%">제목</th>
				<th>작성일</th>
			</tr>
			
			<c:forEach var="noti" items="${list}">
			<tr>
				<td>
				<!-- 관리자 체크박스 시작 -->
				<c:if test="${user_auth == 9}">
				<input class="checkbox" name="checkbox" type="checkbox" value="${noti.noti_num}">
				</c:if>
				<!-- 관리자 체크박스 끝 -->
				${noti.noti_num}
				</td>
				<td>
				<c:if test="${noti.noti_category == 1}">회원</c:if>
				<c:if test="${noti.noti_category == 2}">주문/주문변경</c:if>
				<c:if test="${noti.noti_category == 3}">결제</c:if>
				<c:if test="${noti.noti_category == 4}">증빙서류</c:if>
				<c:if test="${noti.noti_category == 5}">공지사항</c:if>
				</td>
				<td>
				<a href="${pageContext.request.contextPath}/notice/noticeDetailForm.do?noti_num=${noti.noti_num}">
				${noti.noti_title}
				</a>
				</td>
				<td>${noti.noti_date}</td>
			</tr>
			</c:forEach>
			<div style="text-align:right;">
				<input type="button" value="글쓰기" onclick="location.href='noticeWriteForm.do'" class="btn btn-outline-success">
			</div>
		</table>
		<!-- 테이블 끝 -->
		</c:if>
		<c:if test="${count <= 0}">
			<div class="result-display" >
				표시할 게시글이 없습니다.
			</div>
		</c:if>
		<!-- 관리자 글쓰기, 삭제 시작 -->
		<c:if test="${user_auth == 9}">
			<input id="all_btn" type="button" value="전체 선택" class="btn btn-outline-success">
			<input id="del_btn" type="button" value="선택 삭제" class="btn btn-outline-success"> 
		</c:if>
		<!-- 관리자 글쓰기, 삭제 끝 -->
		<div class="align-center">${page}</div>
		</div>
	</div>
	</div>
	<div style="clear:both;">
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
