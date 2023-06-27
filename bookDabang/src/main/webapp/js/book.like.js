$(function(){
	//좋아요 선택 여부와 선택한 총 개수 읽기
	function selectLike(){
		$.ajax({
			url:'getLike.do',
			type:'post',
			data:{review_num:$('#output_like').attr('data-num')},
			dataType:'json',
			success:function(param){
				displayLike(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	
	//좋아요 등록(및 삭제) 이벤트 처리
	$('#output_like').click(function(){
		$.ajax({
			url:'writeLike.do',
			type:'post',
			data:{review_num:$('#output_like').attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 좋아요를 눌러주세요');
				}else if(param.result == 'success'){
					displayFav(param);
				}else{
					alert('좋아요 표시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//좋아요 표시 (UI 처리)
	function displayLike(param){
		let output;
		if(param.status == 'noLike'){
			//js파일은 el 사용X - html경로인식 방법 사용
			output = '<i class="bi bi-hand-thumbs-up"></i>'; 
		}else{
			output = '<i class="bi bi-hand-thumbs-up-fill"></i>';
		}
		//문서 객체 설정
		$('#output_like').text(output);
		$('#output_lcount').text(param.count);
	}
	
	//초기 데이터 호출
	selectLike();	
});
