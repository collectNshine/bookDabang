<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책다방:공지사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		$('#list_Form').submit(function(event){
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
	});
</script> 

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div>
<!-- QnA 박스 시작-->
	<ul>
		<li><b>QnA</b></li>   
		<li><a href="noticeList.do" >전체질문</a></li>
		<c:forEach var="noti2" items="${list2}">
			<li><a href="noticeList.do?noti_category=${noti2.noti_category}">${noti2.noti_category_name}(${noti2.noti_category_count})</a></li>
		</c:forEach>
		<p>
		<li><a href="${pageContext.request.contextPath}/qna/qnaList.do"><b>1:1문의</b></a></li>  
	</ul>
<!--QnA 박스 끝-->
</div>
<div>
<!-- 검색상자 시작-->
	<form id="list_Form" action="noticeList.do" method="post">
		<ul>
			<li>
				<input name="noti_category" type="hidden" value="${noti_category}">
				<select name="keyfield">
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
				</select>
				<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">

				<input type="submit" value="검색">
			</li>
			<li id="guide"></li>
		</ul>	
	</form>
<!-- 검색상자 끝-->
</div>
<div>
<c:if test="${count > 0}">
	<table>
		<tr>
			<th></th>
			<th>카테고리</th>
			<th>제목</th>
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
	</table>
	</c:if>
	<c:if test="${count <= 0}">
		<div>
			oops! 검색 결과가 없습니다. 
		</div>
	</c:if>
	<!-- 관리자 글쓰기, 삭제 시작 -->
	<c:if test="${user_auth == 9}">
		<input id="all_btn" type="button" value="전체 선택" >
		<input id="del_btn" type="button" value="선택 삭제"> 
		<input type="button" value="글쓰기" onclick="location.href='noticeWriteForm.do'">
	</c:if>
	<!-- 관리자 글쓰기, 삭제 끝 -->
	<div class="align-center">${page}</div>
</div>
</body>
</html>