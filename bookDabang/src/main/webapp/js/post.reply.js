$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'listReply.do',
			type:'post',
			data:{pageNum:pageNum, post_num:$('#post_num').val()}, //{key:value}
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum == 1){
					//처음 호출 시에는 해당 ID의 div 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					//프로필 사진
					output += '<span class="user-photo" style="float:left;">';
					if(item.photo){
						output += '<img src="../upload/' + item.photo + '" width="35" height="35" class="my-photo">';
					}else{
						output += '<img src="../images/face.png" width="35" height="35" class="my-photo">';
					}
					output += '</span>';
					//닉네임, 날짜
					output += '<span class="name-date" style="float:left; margin-left:15px;">';
					output += '<div class="name">' + item.name + '</div>';
					if(item.re_modifydate){ //값이 있으면 true, 없으면 false (javascript)
						output += '<span class="modify-date">' + item.re_modifydate + ' 수정됨</span>';
					}else{
						output += '<span class="modify-date">' + item.re_date + '</span>';
					}
					output += '</span>';
					output += '<div class="sub-item" style="clear:both; margin-left: 48px;">';
					output += '<p class="re-content">' + item.re_content + '</p>';
					
					//수정,삭제 버튼
					//로그인한 회원번호와 작성자의 회원번호 일치 여부 체크
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 작성자 회원번호 일치
						output += ' <input type="button" data-renum="' + item.re_num + '" value="수정" class="modify-btn btn btn-outline-primary">';	
						output += ' <input type="button" data-renum="' + item.re_num + '" value="삭제" class="delete-btn btn btn-outline-danger">';
						output += '</div>';	
					}
					
					output += '<hr size="1" noshade width="100%">'
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
					
					//let re_num = '<input type="hidden" name="re_num" value="' + item.re_num +'">'
					//$('#delete_renum').text(re_num); 
				});
				
				//page button 처리
				if(currentPage>=Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				} else{
					//다음 페이지가 존재
					$('.paging-button').show();	
				}
					
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생');	
			}
		});
		
	}
	
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭 시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	//댓글 등록
	$('#re_form').submit(function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#re_content').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false;
		}
		
		//form 이하의 태그에서 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				} else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출함
					selectList(1);
				} else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	//textarea에 내용 입력 시 글자수 체크
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ //300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		} else{ //300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id') == 're_content'){
				//등록폼 글자수
				$('#re_first .letter-count').text(remain);
			} else{
				//수정폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	
	//댓글 수정 버튼 클릭 시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		//댓글 내용                                        모든 <br> 태그를 찾아 \n으로 변경
		let content = $(this).parent().find('p').html().replace(/<br>/gi, '\n');
														//g:지정 문자열 모두, i:대소문자 무시
		//댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
		modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="' + re_num + '">';
									  	//name: 서버로 전송, id: 자바스크립트 or CSS를 위해 명시
		modifyUI +=	'<textarea rows="3" cols="30" name="re_content" id="mre_content" class="chat-content form-control" style="resize:none;">'+ content +'</textarea>';			
		modifyUI += '<div id="mre_first"  style="margin-right: 50px;"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div class="review-button">';
		modifyUI += ' <input type="submit" value="수정" class="btn btn-outline-primary">';
		modifyUI += ' <input type="button" value="취소" class="re-reset btn btn-outline-secondary">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정 중인 댓글이 있을 경우, 수정버튼을 클릭하면 숨겨져 있는 sub-item을환원시키고 수정폼을 초기화함
		initModifyForm();
		
		//데이터가 표시되어 있는 div 감추기
		$(this).parent().hide();
		//수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 세팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);
		
	});
	
	//수정폼에서 취소 버튼 클릭 시 수정폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});
	
	//댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	
	//댓글 수정
	$(document).on('submit','#mre_form', function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#mre_content').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			return false;
		}
		
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'updateReply.do',
			type:'post',
			data:form_data, //변수-> 작은 따옴표x
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				} else if(param.result == 'success'){
					$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'<br>'));
					//수정폼 삭제 및 초기화
					initModifyForm();
				} else if(param.result == 'wrongAccess'){
					alert('타인의 글을 수정할 수 없습니다.');
				} else{
					alert('댓글 수정 오류');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');	
			}
		});
	});
	
	//댓글 삭제
	$(document).on('click','.delete-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		
		$.ajax({
			url:'deleteReply.do',
			type:'post',
			data:{re_num:re_num},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				} else if(param.result == 'success'){
					alert('삭제 완료!');
					selectList(1);
				} else if(param.result == 'wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다.');
				} else{
					alert('댓글 삭제 오류 발생!');
				}
			},
			error:function(){
				alert('네트워크 오류 발생'); //해당 alert창 뜰 시 이클립스의 콘솔 확인
			}
		});
	});
	
	//초기 데이터(목록) 호출
	selectList(1);
	
});