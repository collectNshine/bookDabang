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
	<script type="text/javascript">
		$(function() {
			// 주문 유효성 체크
			$('#order_modify').submit(function() {
				if($('input[type=radio]:checked').val() == 1) {
					let items = document.querySelectorAll('input[type="text"]');
					for(let i = 0; i < items.length; i++) {
						if(items[i].value.trim() == '') {
							let label = document.querySelector('label[for="'+items[i].id+'"]');
							alert(label.textContent + ' 항목 필수 입력!!');
							items[i].value = '';
							items[i].focus();
							return false;
						}
					}
				}
			});
	</script>
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
						<th>결제수단</th>
						<th>주문금액</th>
						<th>배송상태</th>
					</tr>
					<tr>
						<td>
							<c:if test="${order.payment == 1}"><b>무통장입금</b></c:if>
							<c:if test="${order.payment == 2}"><b>카드결제</b></c:if>
						</td>
						<td><b>${order.order_total}</b></td>
						<td>
							<c:if test="${order.status == 1}"><b>배송대기</b></c:if>
							<c:if test="${order.status == 2}"><b>배송준비중</b></c:if>
							<c:if test="${order.status == 3}"><b>배송중</b></c:if>
							<c:if test="${order.status == 4}"><b>배송완료</b></c:if>
							<c:if test="${order.status == 5}"><b>주문취소</b></c:if>
						</td>
					</tr>
				</table>
				</div>
			</div>
			
			<div class="order-detail">
				<form id="order_modify" action="adminModify.do" method="post">
					<input type="hidden" name="order_num" value="${order.order_num}">
					<input type="hidden" name="status" value="${order.status}">
					<div class="delivery-info">
							<ul>
								<li>
									<h4><b>배송정보</b></h4>
								</li>
								<li>
									<div class="input-group" id="delivery_info">
										<c:if test="${order.status != 5}">
										<div class="input-group-text">
											<input class="form-check-input mt-0" type="radio" name="status" value="1" aria-label="Radio button for following text input" <c:if test="${order.status == 1}">checked</c:if>>
										</div>
										<input type="text" class="form-control" aria-label="Text input with radio button" value="배송대기">
										
										<div class="input-group-text">
											<input class="form-check-input mt-0" type="radio" name="status" value="2" aria-label="Radio button for following text input" <c:if test="${order.status == 2}">checked</c:if>>
										</div>
										<input type="text" class="form-control" aria-label="Text input with radio button" value="배송준비중">
										
										<div class="input-group-text">
											<input class="form-check-input mt-0" type="radio" name="status" value="3" aria-label="Radio button for following text input" <c:if test="${order.status == 3}">checked</c:if>>
										</div>
										<input type="text" class="form-control" aria-label="Text input with radio button" value="배송대기">
										
										<div class="input-group-text">
											<input class="form-check-input mt-0" type="radio" name="status" value="4" aria-label="Radio button for following text input" <c:if test="${order.status == 4}">checked</c:if>>
										</div>
										<input type="text" class="form-control" aria-label="Text input with radio button" value="배송준비중">
										</c:if>
										<div class="input-group-text">
											<input class="form-check-input mt-0" type="radio" name="status" value="5" aria-label="Radio button for following text input" <c:if test="${order.status == 5}">checked</c:if>>
										</div>
										<input type="text" class="form-control" aria-label="Text input with radio button" value="주문취소">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주문자</span>
										<input type="text" class="form-control info-check" value="${order.receive_name}" name="receive_name" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">우편번호</span>
										<input type="text" class="form-control info-check" id="zipcode" value="${order.receive_post}" name="receive_post" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">주소</span>
										<input type="text" class="form-control info-check" id="address1" value="${order.receive_address1}" name="receive_address1" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">상세주소</span>
										<input type="text" class="form-control info-check" id="address2" value="${order.receive_address2}" name="receive_address2" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">전화번호</span>
										<input type="text" class="form-control info-check" id="phone" value="${order.receive_phone}" name="receive_phone" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group mb-3">
										<span class="input-group-text" id="inputGroup-sizing-default">이메일</span>
										<input type="text" class="form-control info-check" id="email" value="${order.email}" name="email" readonly aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
									</div>
								</li>
								<li>
									<div class="input-group">
										<span class="input-group-text">요청사항</span>
										<textarea class="form-control info-check" name="notice" readonly id="notice" aria-label="With textarea">${order.notice}</textarea>
									</div>
								</li>
								<li id="move_li">
									<div class="buttons">
										<c:if test="${order.status != 5}">
										<input type="submit" value="수정" class="btn btn-outline-secondary btn-sm">
										</c:if>
										<c:if test="${order.status == 4 or order.status == 5}">
										<input type="button" value="삭제" onclick="location.href='deleteOrder.do?order_num=${order.order_num}'" id="order_cancel" class="btn btn-outline-secondary btn-sm">
										</c:if>
										<input type="button" value="주문목록" onclick="location.href='${pageContext.request.contextPath}/mypage/myPage.do#admin_order'" class="btn btn-outline-secondary btn-sm">
									</div>
								</li>
							</ul>
						</div>
				</form>
			</div>
			</div>
		</div>
		<!-- 내용 E -->
	</div>
</body>
</html>