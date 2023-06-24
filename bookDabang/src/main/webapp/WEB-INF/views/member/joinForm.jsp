<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
#type_num{
	display:none;
}
#logo{
	cursor: pointer;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
//아이디 입력값 검증
$(document).ready(function() {
	
	var click_check = false;
	
	var rannum = "";
	var input = "";
	var email = "";
	
    $("#id").blur(function(){
        if (!/^[A-Za-z0-9]{5,20}$/.test($('#id').val())) {
            $('#guide').text("아이디는 5~20자의 영어대소문자와 숫자로 구성되어야 합니다.").css('color','#F00');
            $('#id').val('');
            return false;
        }
        //아이디 중복체크
        $.ajax({ 
    		url:'checkId.do',
    		type:'post',
    		data:{id:$('#id').val()},
    		dataType:'json',
    		success:function(param){
    			if(param.result == 'isDuplicated'){
    				$('#guide').text("이미 사용중인 아이디입니다.").css('color','#F00');
    				$('#id').val('');
    				return false;
    			}
    		},
    		error:function(){
    			alert('에러가 발생했습니다.');
    			$('#id').val('');
    			return false;
    		}
    	});
    });
 	 //비밀번호 입력값 검증
	$("#passwd").blur(function(){
		click_check = false;
		
		if(!/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test($("#passwd").val())){
			 $('#guide').text("비밀번호는 8~16자 영문 대소문자, 숫자와 특수문자로 구성되어야 합니다.").css('color','#F00');
	            $('#passwd').val('');
	            return false;
		}
	});
	
	//비밀번호 일치 확인 
	$('#passwd_btn').click(function(){
		click_check = true;
		//비밀번호와 비밀번호 확인값 일치 확인
		if($('#passwd').val()!=$('#passwd2').val()){
			$('#guide').text("비밀번호와 비밀번호 확인이 불일치합니다.").css("color","#F00");
			$('#passwd').val('').focus();
			$('#passwd2').val('');
			return false;
		}else{
			$('#guide').text("비밀번호와 비밀번호 확인이 일치합니다.")
		}
	});
	
	//체크박스 하나만 선택하게 만들기 
	$('input[type="checkbox"]').click(function(){
		if($(this).prop('checked')){//체크 액션 발생 속성이 checked가 됨
			$('input[type="checkbox"]').prop('checked',false);//나머지는 체크풀고 
			$(this).prop('checked',true);//액션 발생한 체크박스만 체크
		}
	});
	//입력하면 가이드 다 지우기 
	$('input').keydown(function(){
		$('#guide').text('');
		$('#guide2').text('');
	});
	//버튼을 누르면 인증 번호 입력창 등장.
	$('#email_btn').click(function(){
		//전체 입력값 확인
		let checks = document.getElementsByClassName('check');
		for(let i = 0; i<checks.length; i++){
			   if(checks[i].value.trim()==''){
				   $('#guide2').text(checks[i].placeholder +"은(는) 필수 입력값입니다.").css("color","#F00");
				   checks[i].focus();
				   
				   return false;
			   }
		}
		
		// 비밀번호 확인버튼 눌렀는지 확인
		if(click_check == false){
			$('#guide').text("비밀번호 확인 버튼을 눌러주세요.").css("color","#F00");
			return false;
		}else{
			$('#guide').text('');
		}
		
		$('#type_num').show();
		//이메일 인증 기능
		$.ajax({
			url:'sendAuthNum.do',
			type:'post',
			dataType:'json',
			data:{email : $('#email').val()},
			success:function(data){
				rannum = data.rannum;
				//처음 입력한 이메일 값을 변수에 저장한다. 
				email = $('#email').val();
			},
			error:function(){
				alert('에러가 발생했습니다.');
			}
		});
		$('#guide').text('');
		$('#guide2').text('');
	});//end of email_btn
	
	$('#join_Form').submit(function(){
		
		input = $('#auth').val().trim();
		if(rannum !== input || rannum == ''){
			$('#guide2').text('다시 입력 바랍니다.').css('color','#F00');
			return false ;
		}
		if(email != $('#email').val().trim()){ //인증 후 이메일 주소 바꾸기 불가능.
			$('#guide2').text('잘못된 접근입니다.').css('color','#F00');
			return false ;
		}
	});
});
		
</script>
</head>
<body>
	<div>
		<img id="logo" src="../images/임시_로고.png" width="130" onclick="location.href='../main/main.do'">
		<form id="join_Form" action="join.do" method="post">
			<div>
				<ul>
					<li>
						<input class="check" id="id" name="id" type="text" placeholder="아이디">
					</li>
					<li>
						<input class="check" id="passwd" name="passwd" type="password" placeholder="비밀번호">
					</li>	
					<li>
						<input class="check" id="passwd2" name="passwd2" type="password" placeholder="비밀번호 확인">
						<input id="passwd_btn" type="button" value="확인">
					</li>
					<li>
						<input class="check" id="phone" name="phone" type="text" placeholder="전화번호">
					</li>
					<li id="guide"></li>
					<li>
						<input class="check" id="name" name="name" type="text" placeholder="이름">
					</li>
					<li>
						<label>생일</label>
						<input class="check" id="birthday" name="birthday" type="date" placeholder ="생일">
					</li>
					<li>
						<input class="check" id="zipcode" name="zipcode" type="text" placeholder="우편번호">
					</li>
					<li>
						<input type="button" value="주소찾기" onclick="execDaumPostcode()">
					</li>
					<li>
						<input class="check" id="address1" name="address1" type="text" placeholder="주소">
					</li>
					<li>
						<input class="check" id="address2" name="address2" type="text" placeholder="상세주소">
					</li>
					<li>
						<label>성별</label>
						<input  id="sex1" name="sex" type="checkbox" value="1" checked>남자
						<input  id="sex2" name="sex" type="checkbox" value="2">여자
					<li>
					<li>
						<input class="check" id="email" name="email" type="email" placeholder="이메일">
					</li>
					<li id="guide2"></li>
					<li>
						<input  id="email_btn" type="button" value="이메일주소로 인증번호발송">
					</li>
				</ul>	
			</div>
			<div id="type_num" >
				<ul >
					<li>
						<input id="auth" name="auth" type="text" placeholder="인증번호">
					</li>
					<li>
						<input id="all_submit" type="submit" value="가입하기">
					</li>

				</ul>
			</div>
			<!-- 우편번호 검색 시작 -->
	<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    //(주의)address1에 참고항목이 보여지도록 수정
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //(수정) document.getElementById("address2").value = extraAddr;
                
                } 
                //(수정) else {
                //(수정)    document.getElementById("address2").value = '';
                //(수정) }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
                document.getElementById("address1").value = addr + extraAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
	<!-- 우편번호 검색 끝 -->
		</form>
	</div>
</body>
</html>