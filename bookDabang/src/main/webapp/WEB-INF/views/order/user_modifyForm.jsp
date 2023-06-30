<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>주문 상세정보</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<!-- 내용 S -->
		<div class="content-main">
			<hr size="1" noshade width="100%">
			<h3><b>주문 상세정보</b></h3>
			<hr size="1" noshade width="100%">
			<div class="wrap-detail">
			<div class="book-detail">
				<h4><b>도서정보</b></h4>
				<c:forEach var="detail" items="${detailList}">
				<table class="table table-hover align-center">
					<tr>
						<td class="table-left"><h5><b>${detail.book_title}</b></h5></td>
						<td class="table-right"><b>${detail.bk_num}</b></td>
					</tr>
					<tr>
						<td rowspan="3"><img src="${pageContext.request.contextPath}/upload/${detail.thumbnail}" width="25%"></td>
						<td><h5><b>${detail.book_author}</b></h5></td>
					</tr>
					<tr>
						<td><h5><b>${detail.book_publisher}</b></h5></td>
					</tr>
					<tr>
						<td><h5><b>${order.order_date}</b></h5></td>
					</tr>
				</table>
				</c:forEach>
				
				<div class="detail-margin">
				<h4><b>결제정보</b></h4>
				<table class="table table-hover align-center">
					<tr>
						<td class="table-left">
							<c:if test="${order.payment == 1}"><b>무통장입금</b></c:if>
							<c:if test="${order.payment == 2}"><b>카드결제</b></c:if>
						</td>
						<td class="table-right"><b>${order.order_total}</b></td>
					</tr>
				</table>
				</div>
			</div>
			
			<div class="order-detail">
				<form id="order_modify" action="orderModify.do" method="post">
					<div class="delivery-info">
							<ul>
								<li>
									<h4><b>배송정보</b></h4>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주문자</span>
										<input type="text" class="form-control info-check" value="${order.receive_name}" name="receive_name" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<input type="text" class="form-control info-check" id="zipcode" value="${order.receive_post}" name="receive_post" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<button class="btn btn-outline-secondary" type="button" id="button-addon2" onClick="execDaumPostcode()">우편번호</button>
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주소</span>
										<input type="text" class="form-control info-check" id="address1" value="${order.receive_address1}" name="receive_address1" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">상세주소</span>
										<input type="text" class="form-control info-check" id="address2" value="${order.receive_address2}" name="receive_address2" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">전화번호</span>
										<input type="text" class="form-control info-check" id="phone" value="${order.receive_phone}" name="receive_phone" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">이메일</span>
										<input type="text" class="form-control info-check" id="email" value="${order.email}" name="email" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group">
										<span class="input-group-text">요청사항</span>
										<textarea class="form-control info-check" name="notice" id="notice" aria-label="With textarea">${order.notice}</textarea>
									</div>
								</li>
								<li id="move_li">
									<div class="buttons">
										<c:if test="${order.status < 2}">
										<input type="submit" value="수정" class="btn btn-outline-secondary btn-sm">
										<input type="button" value="주문취소" id="order_cancel" class="btn btn-outline-secondary btn-sm">
										<script type="text/javascript">
											let order_cancel = document.getElementById('order_cancel');
											order_cancel.onclick = function() {
												let choice = confirm('주문을 취소하시겠습니까??');
												if(choice) { location.replace('orderCancel.do?order_num=${order.order_num}'); }
											};
										</script>
										</c:if>
										<input type="button" value="주문목록" onclick="location.href='orderList.do'" class="btn btn-outline-secondary btn-sm">
									</div>
								</li>
							</ul>
						</div>
				</form>
			</div>
			</div>
		</div>
		<!-- 내용 E -->
		<!-- 우편번호 검색 시작 -->
		<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top, left값 조정 필요 -->
		<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
			<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
		</div>

		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
			// 우편번호 찾기 화면을 넣을 element
			var element_layer = document.getElementById('layer');
			
			// iframe을 넣은 element를 안보이게 한다.
			function closeDaumPostcode() { element_layer.style.display = 'none'; }
			
			function execDaumPostcode() {
				new daum.Postcode({ oncomplete: function(data) {	// 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
					// 각 주소의 노출 규칙에 따라 주소를 조합한다.
					// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
					var addr = ''; // 주소 변수
					var extraAddr = ''; // 참고항목 변수
					
					//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
					if (data.userSelectedType === 'R') { addr = data.roadAddress; }	// 사용자가 도로명 주소를 선택했을 경우
					else { addr = data.jibunAddress; }	// 사용자가 지번 주소를 선택했을 경우(J)
					
					// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
					if(data.userSelectedType === 'R') {
						// 법정동명이 있을 경우 추가한다. (법정리는 제외)
						// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
						if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)) { extraAddr += data.bname; }
						
						// 건물명이 있고, 공동주택일 경우 추가한다.
						if(data.buildingName !== '' && data.apartment === 'Y') {
							extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
						}
						
						// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
						if(extraAddr !== '') { extraAddr = ' (' + extraAddr + ')'; }
						//(주의)address1에 참고항목이 보여지도록 수정
						// 조합된 참고항목을 해당 필드에 넣는다.
						//(수정) document.getElementById("address2").value = extraAddr;
					}
					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('zipcode').value = data.zonecode;
					//(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
					document.getElementById("address1").value = addr + extraAddr;
					// 커서를 상세주소 필드로 이동한다.
					document.getElementById("address2").focus();
					
					// iframe을 넣은 element를 안보이게 한다.
					// (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
					element_layer.style.display = 'none'; },
					width : '100%',
					height : '100%',
					maxSuggestItems : 5
				}).embed(element_layer);
				
				// iframe을 넣은 element를 보이게 한다.
				element_layer.style.display = 'block';
				
				// iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
				initLayerPosition();
			}
			
			function initLayerPosition(){
				var width = 300;		//우편번호서비스가 들어갈 element의 width
				var height = 400;		//우편번호서비스가 들어갈 element의 height
				var borderWidth = 5;	//샘플에서 사용하는 border의 두께
				
				// 위에서 선언한 값들을 실제 element에 넣는다.
				element_layer.style.width = width + 'px';
				element_layer.style.height = height + 'px';
				element_layer.style.border = borderWidth + 'px solid';
				
				// 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
				element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
				element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
			}
		</script>
		<!-- 우편번호 검색 끝 -->
	</div>
</body>
</html>