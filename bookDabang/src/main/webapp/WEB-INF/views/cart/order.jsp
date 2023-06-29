<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.float-clear{
	clear:both;
}
.float-left{
	float:left;
}
#overflow{
overflow:scroll;
width:100%;
height:400px;
}
.middle{
margin:auto;
}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
			<div>
				<h2>주문/결제</h2>
					<div>
						<!--책 리스트가 보이도록 만든다. -->
							<div class="cart-table">
								<ul class="list-group">
									<li class="list-group-item">
									<ul>
										<li><div class="cart-bookimg">도서사진</div></li>
										<li><div class="cart-booktitle">도서명</div></li>
										<li><div class="cart-bookprice">가격</div></li>
										<li><div class="cart-orderquantity input-group mb-3">수량	</div></li>
									</ul>
								</li>
								<div id="overflow">
									<c:forEach var="cart" items="${list}">
										<li class="list-group-item">
											<ul class="cart-booklist">
												<li><div class="cart-bookimg"><img src="${pageContext.request.contextPath}/upload/${cart.bookVO.thumbnail}" width="100" height="100"></div></li>
												<li><div class="cart-booktitle">${cart.bookVO.title}</div></li>
												<li><div id="cart-bookprice" class="cart-bookprice">${cart.bookVO.price}</div></li>
												<li><div class="cart-orderquantity input-group mb-3">${cart.order_quantity}</div></li>
											</ul>
										</li>
									</c:forEach>
								</div>
							</ul>
						</div>
					</div>
					<div>
						<!-- 금액정보 div -->
						<div class="book-buy">  
							<table class="book-buy-list" >
							<tr>
								<td class="width_td1">선택금액</td>
								<td class="width_td2"><span id="selected">0</span>원</td>
							</tr>
							<tr>
								<td>배송비</td>
								<td><span id="ship">0</span>원</td>
							</tr>
							<tr>
								<td>주문금액</td>
								<td><span id="total">0</span>원</td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" value="구매" class="btn btn-outline-secondary"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="float-clear">
				<div>
					<div class="float-left">
						<ul>
							<h2>주문정보</h2>
							<li><input class="order" type="text" placeholder="주문하시는 분" value="${member.name}"></li>
							<li><input class="order" type="text" placeholder="우편번호" value="${member.zipcode}"></li>
							<li><input class="order" type="text" placeholder="주소" value="${member.address1}"></li>
							<li><input class="order" type="text" placeholder="상세주소" value="${member.address2}"></li>
							<li><input class="order" type="text" placeholder="전화번호" value="${member.phone}"></li>
							<li><input class="order" type="email" placeholder="이메일" value="${member.email}"></li>
						</ul>
					</div>	
					<div>	
						<ul>
							<h2>배송정보</h2>
							<li><input class="order2" type="text" id="order" placeholder="주문하시는 분"></li>
							<li><input class="order2" type="text" id="zipcode" name="zipcode" placeholder="우편번호"><input type="button" value="우편번호" onClick="execDaumPostcode()"></li>
							<li><input class="order2" type="text" id="address1" name="address1" placeholder="주소"></li>
							<li><input class="order2" type="text" id="address2" name="address2" placeholder="상세주소"></li>
							<li><input class="order2" type="text" id="phone" placeholder="전화번호"></li>
							<li><input class="order2" type="email" id="email" placeholder="이메일"></li>
						</ul>
					</div>	
					<div>
						<input type="radio" name="info" id="same"><label>주문자와 정보 동일</label>
						<input type="radio" name="info" id="new"><label>새로운 배송지</label>
					</div>		
				</div><!-- 주문지 주소 끝-->
				<div>
					<h2>결제수단</h2>
					<div class="middle">
						<input type="radio" name="payment">
						<label>무통장 입금</label>
						<input type="radio" name="payment">
						<label>카드</label>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- 우편번호 검색 시작 -->
<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

// 우편번호 찾기 화면을 넣을 element
var element_layer = document.getElementById('layer');

function closeDaumPostcode() {
    // iframe을 넣은 element를 안보이게 한다.
    element_layer.style.display = 'none';
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                //(주의)address1에 참고항목이 보여지도록 수정
                // 조합된 참고항목을 해당 필드에 넣는다.
                //(수정) document.getElementById("address2").value = extraAddr;
         
            } 
            //(수정) else {
            //(수정)    document.getElementById("address2").value = '';
            //(수정) }
			
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
         	document.getElementById('zipcode').value = data.zonecode;
          
            //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
           	document.getElementById("address1").value = addr + extraAddr;
           
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("address2").focus();

            // iframe을 넣은 element를 안보이게 한다.
            // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
            element_layer.style.display = 'none';
        },
        width : '100%',
        height : '100%',
        maxSuggestItems : 5
    }).embed(element_layer);

    // iframe을 넣은 element를 보이게 한다.
    element_layer.style.display = 'block';

    // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
    initLayerPosition();
}

// 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
// resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
// 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
function initLayerPosition(){
    var width = 300; //우편번호서비스가 들어갈 element의 width
    var height = 400; //우편번호서비스가 들어갈 element의 height
    var borderWidth = 5; //샘플에서 사용하는 border의 두께

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
</html>