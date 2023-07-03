<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${book.title} | 책다방</title> 
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/book_style.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/book.mark.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/book.review.js"></script>
<script type="text/javascript">
	$(function(){ 
		//수량 이벤트 연결
		$('#order_quantity').on('keyup mouseup',function(){
			//총 주문 금액 처리
			let total = $('#price').val() * $('#order_quantity').val();
			if(total > 0 && total < 30000){
				total = total + 3000;
				$('#ship_price').text('3,000원');
				$('#item_total_txt').text(total.toLocaleString()+"원");
			}else if(total >= 30000){
				$('#ship_price').text('무료');
				$('#item_total_txt').text(total.toLocaleString()+"원");
			}
			
			//숫자만 허용
			if(!/^[0-9]+$/.test($('#order_quantity').val())){
				alert('1 이상의 숫자만 입력 가능합니다.');
				$('#order_quantity').val('').focus();
				$('#ship_price').text('3,000원');
				total = $('#price').val() * 1 + 3000;
				$('#item_total_txt').text(total.toLocaleString()+"원");
				return;
			}
			
			//(둘 중 하나만 Number로 형변환)
			if(Number($('#stock').val()) < $('#order_quantity').val()){
				alert('최대 허용 주문 수량을 초과하였습니다.');
				$('#order_quantity').val('').focus();
				$('#ship_price').text('3,000원');
				total = $('#price').val() * 1 + 3000;
				$('#item_total_txt').text(total.toLocaleString()+"원");
				return;
			}
		});  //end of keyup mouseup
		
		
		//장바구니에 상품을 담기 위해 이벤트 처리
		$('#item_cart').submit(function(event){
			//기본 이벤트 제거
			event.preventDefault();
			
			if($('#order_quantity').val() == ''){
				alert('수량 입력 시에만 주문이 가능합니다.');
				$('#order_quantity').focus();
				return false;
			}
			
			//데이터를 한꺼번에 읽어옴
			let form_data = $(this).serialize();
			
			$.ajax({
				url:'../cart/write.do',
				type:'post',
				data:form_data,
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하실 수 있습니다.');
						location.href='../member/loginForm.do';
					}else if(param.result == 'success'){
						let choice = confirm('장바구니에 상품을 담았습니다. 이동하시겠습니까?');
						if(choice){
							location.href='../cart/list.do';
						}
					}else if(param.result == 'over_quantity'){
						alert('입력하신 주문 수량이 현 재고 수량을 초과하였습니다. 다시 입력해주세요.');
					}else{
						alert('장바구니 담기 오류');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		}); //end of submit
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/><br><br><br>
	<!-- 내용 시작 -->
	<div class="detail-main">
	
	<nav aria-label="breadcrumb" style="--bs-breadcrumb-divider: '>';">
	<div id="nav">
	  <ol class="breadcrumb" style="font-size:15pt;">
	    <li class="breadcrumb-item"><a href="list.do">차림표</a></li>
	    <li class="breadcrumb-item active" aria-current="page"><a href="list.do?category=${book.category}"><b>${book.category}</b></a></li>
	  </ol>
	</div>
	</nav>

		<div class="item-image">
			<img src="${pageContext.request.contextPath}/upload/${book.thumbnail}" width="420" id="item_thumbnail">
		</div>
		<div class="item-detail">
			<form id="item_cart">
				<input type="hidden" name="bk_num" value="${book.bk_num}" id="bk_num">
				<input type="hidden" name="price" value="${book.price}" id="price">
				<input type="hidden" name="stock" value="${book.stock}" id="stock">
				<div class="detail-title">
					${book.title}
				</div>
				
				<div class="detail-author-publisher">
					${book.author} | ${book.publisher}
				</div>
				
				<div class="detail-category">
					${book.category}
				</div>
				
				<div style="float:left; margin-right:30px;">
				<img id="output_mark" data-num="${book.bk_num}" 
					 src="${pageContext.request.contextPath}/images/no_mark.png" width="30">
				책갈피
				</div>
				
				<div style="float:left;">
				<a href="#review_div"><img src="${pageContext.request.contextPath}/images/reply.png" width="27">&nbsp;&nbsp;(${review_count})</a>
				</div>
				
				<div style="clear:both;">
				<input type="button" class="btn btn-outline-primary" value="서평 작성" onclick="location.href='${pageContext.request.contextPath}/post/writeForm.do?bk_num=${book.bk_num}'">
				</div>
				
				<hr size="3" width="100%">
				
				<div class="detail-price">
				<span style="font-size:14pt;"><b>가격</b></span>
				<span id="book_price"><fmt:formatNumber value="${book.price}"/>원</span>
				</div>
				
				<div class="detail-ship-price">
				<span style="font-size:14pt;"><b>배송비</b></span> <span id="ship_price">3,000원</span> <span style="color:grey;">(30,000원 이상 구매 시 무료 배송)</span>
				</div>
				
				<hr size="1" width="100%">
				
				<div class="detail-stock">
				<c:if test="${book.stock > 0}">
				<span style="font-size:14pt;"><b>수량</b></span>
				<input type="number" name="order_quantity" min="1" max="${book.stock}" 
					   autocomplete="off" id="order_quantity" value="1">
				</c:if>
				<c:if test="${book.stock <= 0}">
					<span class="sold-out">품절</span>
				</c:if>
				</div>	
				
				<hr size="1" width="100%">

				
				<div class="total-price">
				<b>총 주문 금액</b> <span id="item_total_txt"><fmt:formatNumber value="${book.price + 3000}"/>원</span>
				</div>
				
				<div>
				<input type="submit" value="장바구니에 담기" class="btn btn-primary">
				</div>
			</form>
		</div> <%--end of item-detail --%>
		
		<hr size="1" noshade="noshade" width="100%">
		
		<div class="detail-content">
			<h3><b>책 소개</b></h3><br>
			${book.content}
		</div>
		
		<br><hr size="3" noshade="noshade" width="100%" style="height:20px; border:0;">
		
		
		<!-- 댓글 시작 -->
		<div id="review_div">
			<div class="detail-content">
				<h3><b>한 줄 기록 </b>(${review_count})</h3><br>
			</div>
			<form id="review_form">
				<input type="hidden" name="bk_num" value="${book.bk_num}" id="bk_num">
				<input type="hidden" name="review_num" value="${review.review_num}" id="review_num">
				<div class="review">
				<div class="user-photo">
					<c:if test="${!empty user_photo}">
	           			<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="40" height="40" class="my-photo">
		      		</c:if>
					<c:if test="${empty user_photo}">
		          		<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
		        	</c:if>
		        </div>
	        	
	        	<div class="review-content">
					<textarea rows="2" cols="130" maxlength="50" name="re_content" id="re_content" class="re-content form-control" placeholder="나만의 생각을 간략하게 남겨주세요" <c:if test="${empty user_num}">disabled="disabled"</c:if>><c:if test="${empty user_num}">로그인 후 작성할 수 있습니다.</c:if></textarea>
				</div>
				
				<div class="submit-button">
				<c:if test="${!empty user_num}"> <!-- 로그인 여부 체크 -->
					<input type="submit" class="btn btn-success" value="등록" style="width:90px;height:90px;">
				</c:if>
				</div>
				</div>
				
				<div class="review-letter-count" id="re_first" style="width:90%;">50/50</div>
				
			</form>
			<hr size="1" noshade="noshade" width="100%">
			
		<!-- 댓글 목록 출력 시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" class="btn btn-success" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/loading.gif" width="50" height="50">
		</div>
		<!-- 댓글 목록 출력 끝 -->	
		</div>
		<!-- 댓글 끝 -->
	</div>
	<!-- 내용 끝 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>