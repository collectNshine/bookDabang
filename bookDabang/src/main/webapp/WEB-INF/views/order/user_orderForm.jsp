<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>도서구매</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".error").hide();
			/* 정규식 */
			let kNe = RegExp(/^[ㄱ-힇a-zA-Z0-9]{1,10}$/);
			let justNumP = RegExp(/^[0-9]{9,11}$/);
			let justNumZ = RegExp(/^[0-9]{4,5}$/);
			let forAddress = RegExp(/^[ㄱ-힇a-zA-Z0-9\,\~\@\#\&\*\(\)\-\_\=\+\s]{1,30}$/);
			
			/* 유효성 체크 */
			$('#cart_order').submit(function() {
				// 이름
				let name = $('#receive_name').val();
				let nameLen = name.length;
				
				if(nameLen < 1) {
					$("#errMsg_01").show();
					$("#errMsg_01").text("받을사람을 입력하세요.");
					return false;
				} else { $("#errMsg_01").hide(); }
				
				if(!kNe.test(name)) {
					$("#errMsg_01").show();
					$("#errMsg_01").text("한글과 영어만 입력가능합니다.");
					return false;
				} else { $("#errMsg_01").hide(); }
				
				// 우편번호
				let zipcode = $('#zipcode').val();
				let zipLen = zipcode.length;
				
				if(zipLen < 1) {
					$("#errMsg_02").show();
					$("#errMsg_02").text("우편번호를 입력하세요.");
					return false;
				} else { $("#errMsg_02").hide(); }
				
				if(!justNumZ.test(zipcode)) {
					$("#errMsg_02").show();
					$("#errMsg_02").text("숫자만 입력가능합니다.");
					return false;
				} else { $("#errMsg_02").hide(); }
				
				// 주소
				let address1 = $('#address1').val();
				let add1Len = address1.length;
				
				if(add1Len < 1) {
					$("#errMsg_03").show();
					$("#errMsg_03").text("주소를 입력하세요.");
					return false;
				} else { $("#errMsg_03").hide(); }
				
				/* if(!forAddress.test(address1)) {
					$("#errMsg_03").show();
					$("#errMsg_03").text("잘못된 형식입니다.");
					return false;
				} else { $("#errMsg_03").hide(); } */
				
				// 상세주소
				let address2 = $('#address2').val();
				let add2Len = address2.length;
				
				if(add2Len < 1) {
					$("#errMsg_04").show();
					$("#errMsg_04").text("주소를 입력하세요.");
					return false;
				} else { $("#errMsg_04").hide(); }
				
				/* if(!forAddress.test(address2)) {
					$("#errMsg_04").show();
					$("#errMsg_04").text("잘못된 형식입니다.");
					return false;
				} else { $("#errMsg_04").hide(); } */
				
				// 전화번호
				let phone = $('#phone').val();
				let phoneLen = phone.length;
				
				if(phoneLen < 1) {
					$("#errMsg_05").show();
					$("#errMsg_05").text("주소를 입력하세요.");
					return false;
				} else { $("#errMsg_05").hide(); }
				
				if(!justNumP.test(phone)) {
					$("#errMsg_05").show();
					$("#errMsg_05").text("숫자만 입력가능합니다.");
					return false;
				} else { $("#errMsg_05").hide(); }
				
				// 이메일
				let email = $('#email').val();
				let emailLen = email.length;
				
				if(emailLen < 1) {
					$("#errMsg_06").show();
					$("#errMsg_06").text("이메일을 입력하세요.");
					return false;
				} else { $("#errMsg_06").hide(); }
				
				// 결제 방식
				if($(':radio[name=payment]:checked').length < 1){
					$("#errMsg_07").show();
					$("#errMsg_07").text("결제방식을 입력하세요.");
					return false;
				} else { $("#errMsg_07").hide(); }
			});
			
			$('#same_address').click(function() {
				$('input[name="receive_name"]').val($('#name_info').val());
				$('input[name="receive_post"]').val($('#post_info').val());
				$('input[name="receive_address1"]').val($('#address1_info').val());
				$('input[name="receive_address2"]').val($('#address1_info').val());
				$('input[name="receive_phone"]').val($('#phone_info').val());
				$('input[name="email"]').val($('#email_info').val());
			});
			
			$('#new_address').click(function() {
				$('input[name="receive_name"]').val('');
				$('input[name="receive_post"]').val('');
				$('input[name="receive_address1"]').val('');
				$('input[name="receive_address2"]').val('');
				$('input[name="receive_phone"]').val('');
				$('input[name="email"]').val('');
			});
			
			numbers();
		});
		
		function numbers() {
			let array = new Array;
			let list = $('.cart-number');
			
			for(let i = 0; i < list.length; i++) { array.push(list[i].value); }
			
			$('input[name="cart_nums"]').attr('value', array);
		}
	</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<!-- 내용 S -->
		<div class="content-main">
			<div class="start">
				<h2><b>도서구매</b></h2>
			</div>
			<hr size="1" noshade width="100%">
			<%-- 구매할 도서 선택 및 삭제 --%>
			<form id="cart_order" action="order.do" method="post">
				<input type="hidden" name="total_price" value="${total_price}">
				<input type="hidden" name="cart_nums" value="">
				<div class="order-table">
					<div class="orderinfo-left">
						<div class="book-info">
							<h4><b>주문도서</b></h4>
								<table class="table table-borderless">
									<tr>
										<th>도서사진</th>
										<th>도서명</th>
										<th>가격</th>
										<th>수량</th>
										<th>도서별 합계</th>
									</tr>
									<c:forEach var="cart" items="${list}">
									<input type="hidden" name="cart_num" value="${cart.cart_num}" class="cart-number">
									<tr>
										<td>
											<a href="${pageContext.request.contextPath}/book/detail.do?bk_num=${cart.bk_num}">
												<img src="${pageContext.request.contextPath}/upload/${cart.bookVO.thumbnail}" width="100" height="100">
											</a>
										</td>
										<td>${cart.bookVO.title}</td>
										<td><fmt:formatNumber value="${cart.bookVO.price}"/>원</td>
										<td>${cart.order_quantity}</td>
										<td><fmt:formatNumber value="${cart.sub_total}"/>원</td>
									</tr>
									</c:forEach>
								</table>
							</div>
						<div class="mem-info">
							<ul>
								<li><h4><b>주문자정보</b></h4></li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주문자</span>
										<input type="text" class="form-control" id="name_info" value="${member.name}" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">우편번호</span>
										<input type="text" class="form-control" id="post_info" value="${member.zipcode}" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주소</span>
										<input type="text" class="form-control" id="address1_info" value="${member.address1}" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">상세주소</span>
										<input type="text" class="form-control" id="address2_info" value="${member.address2}" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">전화번호</span>
										<input type="text" class="form-control" id="phone_info" value="${member.phone}" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">이메일</span>
										<input type="text" class="form-control" id="email_info" value="${member.email}" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="orderinfo-right">
						<div class="delivery-info">
							<ul>
								<li>
									<h4><b>배송정보</b></h4>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">받을사람</span>
										<input type="text" class="form-control info-check" value="" id="receive_name" name="receive_name" maxlength="30" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<span class="error" id="errMsg_01"></span>
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<input type="text" class="form-control info-check" id="zipcode" value="" name="receive_post" maxlength="5" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<button class="btn btn-outline-success rounded" type="button" id="button-addon2" onClick="execDaumPostcode()">우편번호</button>
										<span class="error" id="errMsg_02"></span>
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주소</span>
										<input type="text" class="form-control info-check" id="address1" value="" name="receive_address1" maxlength="90" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<span class="error" id="errMsg_03"></span>
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">상세주소</span>
										<input type="text" class="form-control info-check" id="address2" value="" name="receive_address2" maxlength="90" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<span class="error" id="errMsg_04"></span>
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">전화번호</span>
										<input type="text" class="form-control info-check" id="phone" value="" name="receive_phone" maxlength="15" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<span class="error" id="errMsg_05"></span>
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">이메일</span>
										<input type="email" class="form-control info-check" id="email" value="" name="email" maxlength="50" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
										<span class="error" id="errMsg_06"></span>
									</div>
								</li>
								<li>
									<div class="input-group">
										<span class="input-group-text">요청사항</span>
										<textarea class="form-control info-check" name="notice" id="notice" maxlength="4000" aria-label="With textarea"></textarea>
									</div>
								</li>
								<li class="select-delivery">
									<div class="input-group" id="move_li">
										<div class="input-group-text">
											<input class="form-check-input mt-0" type="radio" id="same_address" name="addressCoice" aria-label="Radio button for following text input">
										</div>
  										<input type="text" class="form-control" aria-label="Text input with radio button" value="기본 배송지">
  										<div class="input-group-text">
									<input class="form-check-input mt-0 payment" type="radio" id="new_address" name="addressCoice" checked="checked" aria-label="Radio button for following text input">
								</div>
	  							<input type="text" class="form-control" aria-label="Text input with radio button" value="신규 배송지">
									</div>
								</li>
							</ul>
						</div>
						<div class="payment-info">
							<h4><b>결제수단</b></h4>
							<div class="input-group">
								<div class="input-group-text">
									<input class="form-check-input mt-0 payment" name="payment" value="1" type="radio" aria-label="Radio button for following text input">
								</div>
	  							<input type="text" class="form-control" aria-label="Text input with radio button" value="무통장입금">
								<div class="input-group-text">
									<input class="form-check-input mt-0 payment" name="payment" value="2" type="radio" aria-label="Radio button for following text input">
								</div>
	  							<input type="text" class="form-control" aria-label="Text input with radio button" value="카드결제">
	  							<span class="error" id="errMsg_07"></span>
							</div>
						</div>
						<div class="end-buy">
							<table class="table table-borderless">
								<tr>
									<td><h4><b>주문금액</b></h4></td>
									<td><h4><b><fmt:formatNumber value="${total_price}"/>원</b></h4></td>
								</tr>
								<tr>
									<td colspan="2"><input type="submit" value="구매" class="endbuy btn btn-success btn-lg"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- 내용 E -->
		<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
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