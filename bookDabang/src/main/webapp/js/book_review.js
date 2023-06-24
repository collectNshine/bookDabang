$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//댓글 목록
	function selectList(pageNum){
		//연산을 위해 pageNum을 변수에 저장
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'listReview.do',
			type:'post',
			data:{pageNum:pageNum,bk_num:$('#bk_num').val()},
			dataType:'json',
			success:function(param){
				$('#loading').hide(); //로딩 이미지 감추기
				count = param.count;
				rowCount = param.rowCount;
				
				if(pageNum == 1){ //1페이지일 경우
					//처음 호출 시엔 해당 ID의 div 내부 내용을 제거
					//(기존 데이터와의 중복을 방지하기 위해)
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h4>' + item.name + '</h4>';
					output += '<div class="sub-item">';
					output += '<p>' + item.review_content + '</p>';
					
					//날짜
					//javascript는 값만 넣어도 값이 있으면 true, 없으면 false로 반환
					if(item.review_modifydate){
						output += '<span class="modify-date">최근 수정일 : ' + item.review_modifydate + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : ' + item.review_date + '</span>';
					}
					
					//수정, 삭제 버튼
					//(로그인한 회원 번호) = (작성자 회원 번호) 일치 여부 체크
					if(param.user_num == item.mem_num){
						//이벤트 발생(버튼 클릭) 시 본인에게서 회원번호를 뽑아내어 item.re_num에 넣어짐
						//이벤트가 반복적으로 발생하므로 id가 아닌 class 속성 부여
						output += ' <input type="button" data-renum="' + item.review_num + '" value="수정" class="modify-btn">';
						output += ' <input type="button" data-renum="' + item.review_num + '" value="삭제" class="delete-btn">';
					}
					
					output += '<hr size="1" noshade width="100%">';
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				//page button 처리
				if(currentPage >= Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				$('#loading').hide(); //로딩 이미지 감추기
				alert('네트워트 오류 발생');
			}
		});
	}
	
	
	//페이지 처리 이벤트 연결 
	//(다음 댓글 보기 버튼 클릭 시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});


	//댓글 등록
	$('#review_form').submit(function(event){
		//기본이벤트 제거를 위해 event를 매개변수로 받음
		//(제거 안 하면 주소가 바뀌면서 submit됨)
		event.preventDefault();
		
		if($('#re_content').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false;
		}ㅠ 
		
		//form 이하의 태그에서 입력한 데이터를 모두 읽어옴
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReview.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서
					//첫 번째 페이지에 게시글을 다시 호출
					selectList(1);
				}else{
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
		$('.review-letter-count').text('50/50');
	}


	//textarea에 내용 입력 시 글자 수 체크
	//미래의 태그에 연결 시 $(document).on() 형식 활용
	$(document).on('keyup','textarea',function(){
		//입력한 글자 수 구하기
		let inputLength = $(this).val().length;
		
		if(inputLength>300){ //300자를 초과한 경우
			$(this).val($(this).val().substring(0,50));
		}else{ //300자 이하인 경우
			let remain = 50 - inputLength;
			remain += '/50';
			if($(this).attr('id') == 're_content'){
				//등록폼 글자 수
				$('.review-letter-count').text(remain);
			}else{
				//수정폼 글자 수
				$('#mre_first .letter-count').text(remain);
			}
		}
	});


	//댓글 삭제
	$(document).on('click', '.delete-btn', function(){
		//댓글 번호 읽어오기
		let review_num = $(this).attr('data-renum');
		$.ajax({
			url:'deleteReview.do',
			type:'post',
			data:{review_num:review_num},
			dataType:'json',
			success:function(param){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
					if(param.result == 'logout'){
						alert('로그인 후 삭제할 수 있습니다.');
					}else if(param.result =='success'){
						alert('댓글이 삭제되었습니다.');
						selectList(1);
					}else if(param.result == 'wrongAccess'){
						alert('본인의 댓글만 삭제할 수 있습니다.');
					}else{
						alert('댓글 삭제 오류 발생');
					}
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	
	//댓글 수정 버튼 클릭 시 수정 폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		//댓글 내용
		//parent() : 부모를 구해줌
		//replace(/A/,'B') : A를 B로 바꿔줌 (g:지정문자열 모두/i:대소문자 무시)
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		//댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
		modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="'+re_num+'">';
		modifyUI += '<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">'+content+'</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += ' <input type="submit" value="수정">';
		modifyUI += ' <input type="button" value="취소" class="re-reset">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정하던 댓글이 있을 경우, 
		//수정버튼을 클릭하면 숨겨져있는 sub-item을 환원하고 수정폼을 초기화함.
		initModifyForm();
		
		//데이터가 표시되어 있는 div를 감춤.
		$(this).parent().hide();
		//수정폼을 수정하고자 하는 데이터가 있는 div에 노출.
		//(부모'들' 중에서 클래스가 item인 부모에 modifyUI를 append시킴으로서)
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';
		//문서객체에 반영
		$('#mre_first .letter-count').text(remain);
	});
	
	
	//수정 폼에서 취소 버튼 클릭 시 수정 폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});
	
	
	//댓글 수정 폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	
	
	//초기 데이터(목록) 호출
	selectList(1);
	
});