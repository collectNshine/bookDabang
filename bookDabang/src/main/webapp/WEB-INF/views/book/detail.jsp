<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${book.title} | 책다방</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_detail_style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<!--  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/book_mark.js"></script>

<script type="text/javascript">
	$(function(){ 
		//공백을 두면 2개의 이벤트 동시 연결 (mouseup : 증가/감소 아이콘 누를때)
		$('#order_quantity').on('keyup mouseup',function(){
			if($('#order_quantity').val() == ''){
				$('#item_total_txt').text('총 주문 금액 : 0원');
				return;
			}
			if($('#order_quantity').val() <= 0){
				$('#order_quantity').val('');
				return;
			}
			//둘 중 하나만 Number로 형변환
			if(Number($('#stock').val()) < $('#order_quantity').val()){
				alert('수량이 부족합니다.');
				$('#order_quantity').val('');
				$('#item_total_txt').text('총 주문 금액 : 0원');
				return;
			}
			
			//총 주문 금액
			let total = $('#item_price').val() * $('#order_quantity').val();
													  //toLocaleString()으로 숫자 세자리 쉼표 처리
			$('#item_total_txt').text('총 주문 금액 : ' + total.toLocaleString() + '원');
		});
		
		//장바구니에 상품을 담기 위해 이벤트 처리
		$('#item_cart').submit(function(event){
			//기본 이벤트 제거
			event.preventDefault();
			
			if($('#order_quantity').val() == ''){
				alert('수량을 입력하세요!');
				$('#order_quantity').focus();
				return false;
			}
			
			//.serialize():데이터를 한꺼번에 읽어옴
			let form_data = $(this).serialize();
			
			$.ajax({
				url:'../cart/write.do',
				type:'post',
				data:form_data,
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요');
					}else if(param.result == 'success'){
						alert('장바구니에 담았습니다.');
						//location.href='../cart/list.do';
					}else if(param.result == 'over_quantity'){
						alert('기존에 주문한 상품입니다. 개수를 추가하면 재고가 부족합니다.');
					}else{
						alert('장바구니 담기 오류');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		});
	});
</script>
 -->
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<!-- 내용 시작 -->
	<div class="content-main">
		<div class="item-image">
			<img src="${pageContext.request.contextPath}/upload/${book.thumbnail}" width="400">
		</div>
		<div class="item-detail">
			<form id="item_cart">
				<input type="hidden" name="bk_num" value="${book.bk_num}" id="bk_num">
				<input type="hidden" name="stock" value="${book.stock}" id="stock">
				<ul>
					<li>
						<div class="detail-title">
							<h3>${book.title}</h3>
						</div>
					</li>
					<li>
						<div class="detail-author-publisher">
							${book.author} | ${book.publisher}
						</div>
					</li>
					<li>
						<%-- 좋아요 --%>
						<%-- html은 속성태그 추가X (예외)'data-' 형태로만 추가 가능--%>
						<img id="output_mark" data-num="${book.bk_num}" 
							 src="${pageContext.request.contextPath}/images/no_mark.png" width="50">
						책갈피
						<span id="output_mcount"></span>
					</li>
				</ul>
				<hr size="3" width="100%">
				<ul>
					<li>
						<div class="detail-price">
						<b>가격</b> <fmt:formatNumber value="${book.price}"/>원
						</div>
					</li>
					
					<c:if test="${book.stock > 0}">
						<li>
							<b>수량</b> <input type="number" name="order_quantity" min="1" max="${book.stock}" 
								   autocomplete="off" id="order_quantity" class="quantity-width" placeholder="1">
						</li>
					</c:if>
					<c:if test="${book.stock <= 0}">
					<li class="align-center">
						<span class="sold-out">품절</span>
					</li>
					</c:if>
				</ul>
				<hr size="1" width="100%">
				<ul>
						<li>
							<span id="item_total_txt">총 주문 금액 : 0원</span>
						</li>
						<li>
							<input type="submit" value="장바구니에 담기">
						</li>
				</ul>
			</form>
		</div>
		<hr size="1" noshade="noshade" width="100%">
		<div class="detail-content">
			<h3>책 소개</h3>
			${book.content}
		</div>
	</div>
	<!-- 내용 끝 -->
</div>

</body>
</html>