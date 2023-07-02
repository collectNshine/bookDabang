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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//수정 버튼 이벤트 연결
		$('#photo_btn').click(function(){
			$('#photo_choice').show();
			$(this).hide(); //수정버튼 감추기
		}); //end of click
		
		
		//이미지 미리 보기
		//처음 화면에 보여지는 이미지 저장
		let photo_path = $('.my-photo').attr('src');
		//선택한 이미지 저장
		let my_photo;
		$('#photo').change(function(){
			my_photo = this.files[0];
			
			if(!my_photo){ 
				$('.my-photo').attr('src',photo_path);
				return;
			}
			//파일 용량 체크
			if(my_photo.size > 1024*1024){
				alert(Math.round(my_photo.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
				$('.my-photo').attr('src',photo_path);
				$(this).val(''); //선택한 파일 정보 지우기
				return;
			}
			
			let reader = new FileReader();
			reader.readAsDataURL(my_photo);
			reader.onload=function(){
				$('.my-photo').attr('src',reader.result)
			};
		});//end of change
		
		
		//전송 버튼 이벤트 연결
		$('#photo_submit').click(function(){
			if($('#photo').val()==''){
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}
			//서버에 파일 전송
			let form_data = new FormData();
			form_data.append('photo',my_photo);
			$.ajax({
				url:'updateMyPhoto.do',
				type:'post',
				data:form_data,
				dataType:'json',
				contentType:false,//데이터 객체를 문자열로 바꿀지에 대한 설정
				processData:false,//해당 타입을 true로 하면 일반text로 구분
				enctype:'multipart/form-data',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
					 	alert('프로필 사진이 수정되었습니다.');
					 	//업로드한 이미지로 초기 이미지를 대체
					 	photo_path = $('.my-photo').attr('src');
					 	$('#photo').val('');
					 	$('#photo_choice').hide();
					 	$('#photo_btn').show();
					}else{
						alert('파일 전송 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		}); //end of submit
		
		
		//취소 버튼 이벤트 연결
		$('#photo_reset').click(function(){
			//초기 이미지 표시
			$('.my-photo').attr('src',photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show(); //수정버튼 다시 보이게
		});
	}); //end of click
	
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<h2>회원정보</h2>
		<div class="mypage-div">
			<h3>프로필 사진</h3>
			<!-- 프로필 사진 시작 -->
			<ul>
				<li>
					<c:if test="${empty member.photo}">
						<img src="${pageContext.request.contextPath}/images/face.png" 
							 width="200" height="200" class="my-photo">
					</c:if>
					<c:if test="${!empty member.photo}">
						<img src="${pageContext.request.contextPath}/upload/${member.photo}" 
							 width="200" height="200" class="my-photo">
					</c:if>
				</li>
				<li>
					<div class="align-center">
						<input type="button" value="수정" id="photo_btn">
					</div>
					<div id="photo_choice" style="display:none;">
						<input type="file" id="photo" accept="image/gif,image/png,image/jpeg">
						<br>
						<input type="button" value="전송" id="photo_submit">
						<input type="button" value="취소" id="photo_reset">
					</div>
				</li>
			</ul>
			<!-- 프로필 사진 끝 -->
			<h3>
				연락처
				<input type="button" value="연락처 수정" onclick="location.href='modifyUserForm.do'">
			</h3>
			<ul>
				<li>이름 : ${member.name}</li>
				<li>전화번호 : ${member.phone}</li>
				<li>이메일 : ${member.email}</li>
				<li>우편번호 : ${member.zipcode}</li>
				<li>주소 : ${member.address1} ${member.address2}</li>
				<li>가입일 : ${member.reg_date}</li>
				<c:if test="${!empty member.modify_date}">
				<li>최근 정보 수정일 : ${member.modify_date}</li>
				</c:if>
			</ul>
		</div> <%-- end of .mypage-div --%>
		
		<div class="mypage-div">
			<h3>
				비밀번호 수정
				<input type="button" value="비밀번호 수정" onclick="location.href='modifyPasswordForm.do'">
			</h3>
			<h3>
				회원 탈퇴
				<input type="button" value="회원 탈퇴" onclick="location.href='deleteUserForm.do'">
			</h3>
		</div> <%-- end of .mypage-div --%>
		<div class="mypage-div">
			<h3>관심 게시물 목록</h3>
			<table>
				<tr>
					<th>제목</th>
					<th>작성자</th>
					<th>등록일</th>
				</tr>
				<c:forEach var="board" items="${boardList}">
				<tr>
					<%-- target="_blank" : 새탭으로 열기 --%>
					<%-- fn:substring(board.title,0,12) : 12자까지만 출력해라 --%>
					<td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}" target="_blank">${fn:substring(board.title,0,12)}</a></td>
					<td>${board.id}</td>
					<td>${board.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
			
			<h3>상품구매목록
				<input type="button" value="구매목록보기" onclick="location.href='${pageContext.request.contextPath}/order/orderList.do'">
			</h3>
			<ul>
				<li>
					<c:forEach var="order" items="${orderList}">
					<div>
						<a href="${pageContext.request.contextPath}/order/orderModifyForm.do?order_num=${order.order_num}">
							${order.order_num} ${order.item_name} (${order.reg_date})
							<c:if test="${order.status == 1}">배송대기</c:if>
							<c:if test="${order.status == 2}">배송준비중</c:if>
							<c:if test="${order.status == 3}">배송중</c:if>
							<c:if test="${order.status == 4}">배송완료</c:if>
							<c:if test="${order.status == 5}">주문취소</c:if>
						</a>
					</div>
					</c:forEach>
				</li>
			</ul>
		</div> <%-- end of .mypage-div --%>
		<%-- float 해제 --%>
		<div class="mypage-end"></div>
	</div>
	<!-- 내용 끝 -->
</div>
</body>
</html>