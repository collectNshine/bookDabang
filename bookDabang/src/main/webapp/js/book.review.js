$(function(){
	let currentPage;
	let count;
	let rowCount;
	
	//좋아요 버튼 클릭 이벤트 처리 (등록 및 삭제)
	$(document).on('click','.output_like',function(){
		let click_btn = $(this);
		$.ajax({
			url:'writeLike.do',
			type:'post',
			data:{review_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 등록 가능합니다.');
				}else if(param.result == 'success'){
					//좋아요 표시
					displayLike(param,click_btn);
					//별로에요가 등록되어 있었다면 해제 후 UI 처리
					if(param.status_dis){ 
						let dis_btn = click_btn.parent().find('.output_dislike');
						displayDislike(param,dis_btn);
					}
				}else{
					alert('등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	
	//좋아요 표시 (UI 처리)
	function displayLike(param,click_btn){
		let output;
		if(param.status == 'noLike'){
			//js파일은 el 사용X - html경로인식 방법 사용
			output = '<i class="bi bi-hand-thumbs-up"></i>'; 
		}else{
			output = '<i class="bi bi-hand-thumbs-up-fill"></i>';
		}
		//문서 객체 설정
		click_btn.html(output);
		click_btn.parent().find('.output_lcount').text(param.count);
	}
	
	
	//별로에요 버튼 클릭 이벤트 처리 (등록 및 삭제)
	$(document).on('click','.output_dislike',function(){
		let click_btn = $(this);
		$.ajax({
			url:'writeDislike.do',
			type:'post',
			data:{review_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 등록 가능합니다.');
				}else if(param.result == 'success'){
					//별로에요 표시
					displayDislike(param,click_btn);
					//좋아요가 등록되어 있었다면 해제 후 UI 처리
					if(param.status){
						let like_btn = click_btn.parent().find('.output_like');
						displayLike(param,like_btn);
					}
				}else{
					alert('등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	
	//별로에요 표시 (UI 처리)
	function displayDislike(param,click_btn){
		let output;
		if(param.status_dis == 'noDislike'){
			//js파일은 el 사용X - html경로인식 방법 사용
			output = '<i class="bi bi-hand-thumbs-down"></i>'; 
		}else{
			output = '<i class="bi bi-hand-thumbs-down-fill"></i>';
		}
		//문서 객체 설정
		click_btn.html(output);
		click_btn.parent().find('.output_dlcount').text(param.count_dis);
	}

	
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
					//프로필 사진
					output += '<span class="user-photo">';
					if(item.photo){
						output += '<img src="../upload/' + item.photo + '" width="35" height="35" class="my-photo">';
					}else{
						output += '<img src="../images/face.png" width="35" height="35" class="my-photo">';
					}
					output += '</span>';
					//닉네임, 날짜
					output += '<span class="name-date">';
					output += '<div class="name">' + item.name + '</div>';
					if(item.review_modifydate){ //값이 있으면 true, 없으면 false (javascript)
						output += '<span class="modify-date">' + item.review_modifydate + ' 수정됨</span>';
					}else{
						output += '<span class="modify-date">' + item.review_date + '</span>';
					}
					output += '</span>';
					//내용
					output += '<p class="review-content">' + item.review_content + '</p>';
					output += '<ul class="like-buttons">'
					output += '<li>'
					output += '<button type="button" class="btn output_like" data-num="'+item.review_num+'">';
					if(item.clicked_like != 'clicked'){
						output += '<i class="bi bi-hand-thumbs-up"></i>';
					}else{
						output += '<i class="bi bi-hand-thumbs-up-fill" fill="blue" ></i>';
					}
					output += '</button>';
					output += '<span class="output_lcount">'+item.cnt_like+'</span>'
					output += '<button type="button" class="btn output_dislike" data-num="'+item.review_num+'">';
					if(item.clicked_dislike != 'clicked'){
						output += '<i class="bi bi-hand-thumbs-down" style="color:red;"></i>';
					}else{
						output += '<i class="bi bi-hand-thumbs-down-fill" fill="red" style="color:red;"></i>';
					}
					output += '</button>';
					output += '<span class="output_dlcount">'+item.cnt_dislike+'</span>'
					output += '</li>'
					output += '</ul>'
					//수정, 삭제 버튼
					//(로그인한 회원 번호) = (작성자 회원 번호) 일치 여부 체크
					if(param.user_num == item.mem_num){
						//이벤트 발생(버튼 클릭) 시 본인에게서 회원번호를 뽑아내어 item.review_num에 넣어짐
						//이벤트가 반복적으로 발생하므로 id가 아닌 class 속성 부여
						output += '<div class="review-button">'
						output += ' <input type="button" data-renum="' + item.review_num + '" value="수정" class="modify-btn btn btn-outline-primary">';
						output += ' <input type="button" data-renum="' + item.review_num + '" value="삭제" class="delete-btn btn btn-outline-danger">';
						output += '</div>';
					}
					
					output += '<hr size="1" noshade width="100%">';
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
		}
		
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
		let review_num = $(this).attr('data-renum');
		//댓글 내용
		//replace(/A/,'B') : A를 B로 바꿔줌 (g:지정문자열 모두/i:대소문자 무시)
		let content = $(this).parent().parent().find('p').html().replace(/<br>/gi,'\n');
		//댓글 수정폼 UI
		let modifyUI = '<form id="mreview_form">';
		modifyUI += '<input type="hidden" name="review_num" id="mre_num" value="'+review_num+'">';
		modifyUI += '<textarea rows="2" cols="130" maxlength="50" name="review_content" id="mreview_content" class="rep-content">'+content+'</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">50/50</span></div>';
		modifyUI += '<div class="review-button">'
		modifyUI += '<input type="submit" value="수정" class="btn btn-outline-primary">';
		modifyUI += '<input type="button" value="취소" class="re-reset btn btn-outline-secondary">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정하던 댓글이 있을 경우, 
		//수정버튼을 클릭하면 숨겨져있는 sub-item을 환원하고 수정폼을 초기화함.
		initModifyForm();
		
		//데이터가 표시되어 있는 div를 감춤.
		$(this).parent().parent().find('p').hide();
		$(this).parent().parent().find('.review-button').hide();
		//수정폼을 수정하고자 하는 데이터가 있는 div에 노출.
		//(부모'들' 중에서 클래스가 item인 부모에 modifyUI를 append시킴으로서)
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLength = $('#mreview_content').val().length;
		let remain = 50 - inputLength;
		remain += '/50';
		//문서객체에 반영
		$('#mre_first .letter-count').text(remain);
	});
	
	
	//수정 폼에서 취소 버튼 클릭 시 수정 폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
		$('.review-content').show();
	});
	
	
	//댓글 수정 폼 초기화
	function initModifyForm(){
		$('.review-button').show();
		$('.review-content').show();
		$('#mreview_form').remove();
	}
	

	//댓글 수정
	$(document).on('submit','#mreview_form',function(event){
		//기본 이벤트 제거
		event.preventDefault();
		
		if($('#mreview_content').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#mreview_content').val('').focus();
			return false;
		}
		
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'updateReview.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result == 'success'){
					$('#mreview_form').parent().find('p').html($('#mreview_content').val()
					.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/\n/g,'<br>'));
					//날짜 처리
					//수정 폼 삭제 및 초기화
					initModifyForm();
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글을 수정할 수 없습니다.');
				}else{
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	//초기 데이터(목록) 호출
	selectList(1);
	
});