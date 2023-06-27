$(function(){
	//책갈피 등록 여부와 등록된 총 개수 읽기
	function selectMark(){
		$.ajax({
			url:'getMark.do',
			type:'post',
			data:{bk_num:$('#output_mark').attr('data-num')},
			dataType:'json',
			success:function(param){
				displayMark(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//책갈피 등록(및 삭제) 이벤트 처리
	$('#output_mark').click(function(){
		$.ajax({
			url:'writeMark.do',
			type:'post',
			data:{bk_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 책갈피 등록이 가능합니다');
				}else if(param.result == 'success'){
					displayMark(param);
				}else{
					alert('책갈피 표시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//좋아요 표시 (UI 처리)
	function displayMark(param){
		let output;
		if(param.status == 'noMark'){
			//js파일은 el 사용X - html경로인식 방법 사용
			output = '../images/no_mark.png'; 
		}else{
			output = '../images/yes_mark.png';
		}
		//문서 객체 설정
		$('#output_mark').attr('src', output);
	}
	
	//초기 데이터 호출
	selectMark();	
});