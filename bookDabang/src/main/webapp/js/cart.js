$(function() {  
	// 전체선택
	/*$('#check_all').click(function() {
		if($('#check_all').prop('checked')) {
			$('.select-book').prop('checked', true);
			totalPrice();
		} else {
			$('.select-book').prop('checked', false);
			totalPrice();
		}
	});*/
	
	// 장바구니 상품 삭제 event 연결
	$(document).on('click', '.cart-del', function() {
		$.ajax({
			url:'deleteCart.do',
			type:'post',
			data:{cart_num:$(this).attr('data-cartnum')},
			dataType:'json',
			success:function(param) {
				if(param.result == 'logout') { alert('Login 후 사용 가능!!'); }
				else if(param.result == 'success') {
					alert('선택 상품 삭제 완료!!');
					location.href='list.do';
				}
				else { alert('장바구니 상품 삭제 오류!!'); }
			},
			error:function() { alert('Network 오류 발생!!'); }
		});
	});
	
	// 장바구니 상품 수량 변경 event 연결
	$(document).on('click', '.cart-modify', function() {
		// 유효성 검사
		let input_quantity = $(this).parent().find('input[name="order_quantity"]');
		if(input_quantity.val() == '') {
			alert('수량 입력 필수!!');
			input_quantity.focus();
			return;
		}
		
		if(input_quantity.val() < 1) {
			alert('1개 이상 구매 필수!!');
			input_quantity.val(input_quantity.attr('value'));	// 변경전 value 값을 읽어옴
			return;
		}
		
		// 서버와 통신
		$.ajax({
			url:'modifyCart.do',
			type:'post',
			data:{cart_num:$(this).attr('data-cartnum'),bk_num:$(this).attr('data-bknum'),order_quantity:input_quantity.val()},
			dataType:'json',
			success:function(param) {
				if(param.result == 'logout') { alert('Login 후 사용 가능!!'); }
				else if(param.result == 'noQuantity') {
					alert('수량 부족!!');
					location.href='list.do';
				} else if(param.result == 'success') {
					alert('상품 개수 수정 완료!!');
					location.href='list.do';
				} else { alert('장바구니 상품 개수 수정 오류 발생!!'); }
			},
			error:function() { alert('Network 오류 발생!!'); }
		});
	});
});
