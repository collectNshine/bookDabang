$(function(){
	//신고 버튼 클릭 시 모달창 팝업
	$("#modal").on('click',function(){
		$("#exampleModal").modal("show");
	});
	
	//신고 등록
	$('#repo_submit').submit(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		//라디오 버튼 미선택 시
		if ( ! $('input[name=repo_category]:checked').val()) {
			alert('신고 유형을 선택해주세요!');
			return false;
		}
		if($('#repo_content').val().trim() == ''){
			alert('상세 사유는 필수 입력 항목입니다.');
			$('#repo_content').val('').focus();
			return false;
		}

		//form 이하의 태그에서 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReport.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 신고할 수 있습니다.');
				} else if(param.result == 'success'){	
					alert('신고가 접수되었습니다.');
					history.go(0);
				} else{
					alert('신고 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
});