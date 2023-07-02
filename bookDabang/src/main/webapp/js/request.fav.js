$(function(){
	//추천수 선택했는지, 총 추천수 개수
	function doyouFavme(){
		$.ajax({
			url:'Favme.do',
			type:'post',
			data:{req_num:$('.output-fav').attr('data-num')},
			dataType:'json',
			success:function(param){
				showmeFav(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}

	//추천수 등록 및 삭제 
	$('.output-fav').click(function(){
	    let click_btn = $(this);
		$.ajax({
			url:'giveFav.do',
			type:'post',
			data:{req_num:click_btn.attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 이용해주세요!')
				}else if(param.result == 'success'){
					showmeFav(param,click_btn);
				}else{
					alert('추천수 표시 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//추천 표시
	function showmeFav(param,click_btn){
		let output;
		if(param.status=='noFav'){
			output = '../upload/fav01.png';
		}else if(param.status=='yesFav'){
			output = '../upload/fav02.png';
		}
		click_btn.attr('src',output);
		click_btn.parent().find('.output-fcount').text(param.count);
	}
});