<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage_style.css">
<style>
ul.search {
    width: 780px;
    list-style: none;
    padding: 0;
    margin: 0 auto;
    display: flex;
}
element.style {
	width: 100%;
    display: flex;
    margin-left: 500px;
}
form {
    width: 600px;
    margin: 0 auto;
    border: 1px solid #000;
    padding: 10px 10px 10px 30px;
}

.form-control{
    display: block;
    width: 160%;
    padding: .375rem .75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: var(--bs-body-color);
    background-color: var(--bs-body-bg);
    background-clip: padding-box;
    border: var(--bs-border-width) solid var(--bs-border-color);
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    border-radius: var(--bs-border-radius);
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}

.me-2 {
    margin-right: .5rem!important;
}
.btn{
	margin-left: 10px;
}

ul.search li {
    margin: 0px 20px 10px -20px;
    padding: 0;
    display: inline;
}
.h4, h4 {
    font-size: calc(1.275rem + .3vw);
    margin-bottom: 100px;
}
.h4, h4 {
    font-size: 1.5rem;
    margin-top: 15px;
}
.mypage-div {
    width: 100%;
    float: left;
    padding: 5px;
}

</style>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/request.fav.js"></script>
</head>
<body> 
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr size="1" noshade="noshade" width="100%" style="border: solid 2px #black;">
		<h2 class="align-center">마이페이지</h2>
	<hr size="1" noshade="noshade" width="100%" style="border: solid 2px #black;">
	
	<div class="mypage-div" style="width:100%;">
	<!-- 프로필 사진 시작 --> 
		<ul>
			<li style="display:flex;  margin-left: 500px;">
				<c:if test="${empty user_photo}">
					<div>
						<img src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
					</div>
					<div style="margin-left:20px;">
						<h4 style="font-size:15px;"><b style="font-size:28px; margin-right:10px;">${user_nickname}</b>님의 책다방</h4>
						<input type="button" value="정보 수정" onclick="location.href='PasswdCheckForm.do'" class="btn btn-outline-secondary" style="margin-bottom:30px;">
					</div>
					
				</c:if>
				<c:if test="${!empty user_photo}">
					<img src="${pageContext.request.contextPath}/upload/${user_photo}" 
						 width="200" height="200" class="my-photo">
					<div style="display:flex;justify-content: space-around;flex-direction: column; margin-left:25px;">	 
						 <h4 style="font-size:15px;"><b style="font-size:28px; margin-right:10px;">${user_nickname}</b>님의 책다방</h4>
						 <input type="button" value="정보 수정" onclick="location.href='PasswdCheckForm.do'" class="btn btn-outline-secondary">
					 </div>
				</c:if>
			</li>		
		</ul>
		<!-- 프로필 사진 끝 -->

				
		<!-- 사용자 마이페이지 메뉴 시작 -->
		<c:if test="${!empty user_num && user_auth == 1}">
		<ul class="tabWrap" style="border-color:#b3b3b3">
			<li data-tab="book_mark" style="cursor: pointer;" class="on"><a href="myPage.do#book_mark">책갈피</a></li>
			<li data-tab="post" style="cursor: pointer;"><a href="myPage.do#post">작성글</a></li>
			<li data-tab="order" style="cursor: pointer;"><a href="myPage.do#order">주문목록</a></li>
		</ul>
		
			
		<!-- 사용자 [1. 책갈피] 시작 -->
		<div id="book_mark" class="tab_contents on">
			<div class="content-main container">
			<br><h2><a href="myPage.do#book_mark">나의 책갈피</a></h2><br>
			<!-- 검색창 시작 : get방식 -->
			<form id="bm_search_form" action="myPage.do#book_mark" method="get" class="d-flex" role="search" >
				<select name="bm_keyfield" class="form-select">
					<option value="1" <c:if test="${param.bm_keyfield==1}">selected</c:if>>도서명</option>
					<option value="2" <c:if test="${param.bm_keyfield==2}">selected</c:if>>저자명</option>
				</select>
				<input type="search" size="16" name="bm_keyword" id="bm_keyword" value="${param.bm_keyword}" class="form-control me-2">
				<input type="submit" value="검색"  class="btn btn-outline-success">
			</form>
			<script type="text/javascript">
				$(function(){
					$('#bm_search_form').submit(function(){
						if($('#bm_keyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#bm_keyword').val('').focus();
							return false;
						}
					});
				});
			</script> <br>
			<!-- 검색창 끝 -->
			<!-- 목록 시작 -->
			<div class="list-space align-right">
				<input type="button" value="책갈피 해제" class="btn btn-outline-secondary" id="selectDeleteBm_btn" style="margin:30px 50px 10px 0;">
			</div>
			<c:if test="${bm_count == 0}">
				<div class="result-display">
					표시할 상품이 없습니다.
				</div>		
			</c:if>
			<div class="bm-list" style="margin-top:0">
			<c:if test="${bm_count > 0}">
				<c:forEach var="bm" items="${bm_list}">
				<div class="card" style="position:relative; display:inline-block;">
				<input type="checkbox" class="bmBox" name="bmBox" value="${bm.mark_num}" style="position:absolute;left:10px;top:5px;width:20px;height:20px;">
					<a href="${pageContext.request.contextPath}/book/detail.do?bk_num=${bm.bk_num}">
						<img src="${pageContext.request.contextPath}/upload/${bm.thumbnail}" class="card-img-top" >
					</a>
				 	<div class="card-body">
					    <p class="card-title bm-title">${bm.title}</p>
					    <p class="card-text bm-author-publisher">${bm.author} | ${bm.publisher}</p>
				   </div>
				</div>
				</c:forEach>
				<!-- 목록 끝 -->
				<div class="align-center">${bm_page}</div>
			</c:if>
			</div>
			</div>
		</div>
		<script type="text/javascript">
		//선택 삭제 버튼 클릭 이벤트
		 $("#selectDeleteBm_btn").click(function(){
		 		if($("input[class='bmBox']:checked").length < 1){
					alert('하나 이상의 항목을 선택하세요');
					return false;
				}
				
			   let checkBmArr = new Array();
			   
			   $("input[class='bmBox']:checked").each(function(index,item){
			    	checkBmArr.push($(this).val());
			   });
			   
			   $.ajax({
				    url:'deleteMarks.do',
				    type:'post',
				    data:{'checkBmArr':checkBmArr.toString()},
				    dataType:'json',
				    success : function(param){
				    	let choice = confirm("책갈피를 해제하시겠습니까?");
				    	if(choice){
							if(param.result == 'logout'){
								alert('로그인 후 해제할 수 있습니다.');
							}else if(param.result == 'success'){
								alert('선택하신 책갈피가 해제되었습니다.');
								location.href='myPage.do';
							}else{
								alert('책갈피 해제 중 오류가 발생했습니다.');
							}
						}else{
							return false;
						}
				    },
				    error:function(){
				    	alert('네트워크 오류 발생');
				    }
			   });
		 });
		</script>
		
		<!-- [1. 책갈피] 끝 -->
		
		
		
	<!-- 사용자 [2. 작성글] 시작 -->
	<div id="post" class="tab_contents">
		<div class="content-main container">
		<br><h2><a href="myPage.do#post">나의 작성글</a></h2><br>
		<!-- 검색창 시작 : get방식 -->
			<form id="search_form2-1" action="myPage.do#post" method="get" class="d-flex" role="search">
				<select name="mp_keyfield" class="form-select">
					<option value="1" <c:if test="${param.mp_keyfield==1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.mp_keyfield==2}">selected</c:if>>내용</option>
				</select>
					<input type="search" size="16" name="mp_keyword" id="mp_keyword" value="${param.mp_keyword}" class="form-control me-2">
					<input type="submit" value="조회" class="btn btn-outline-success">
			</form>
			
			<script type="text/javascript">
			<%-- 
			$(function() {
				 $(".tabWrap").tabs({
				        select: function(event, ui) {                   
				        window.location.replace(ui.tab.hash);
				   		//위 두줄을 추가를 하면 새로고침을 해도 선택된 탭에서 계속 유지가 된다.
				     	}
				 });
			})
			--%>
				$(function(){
					$('#mp_search_form').submit(function(){
						if($('#mp_keyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#mp_keyword').val('').focus();
							return false;
						}
					});
				});
			</script> 
			<!-- 검색창 끝 -->	
		<c:if test="${mp_count == 0}">
			<div class="result-display">
				표시할 작성글이 없습니다.
			</div>		
		</c:if>
		
		<c:if test="${mp_count > 0}">
		<table class="table table-hover align-center">
			<tr>
				<th>제목</th>
				<th>내용</th>
				<th>등록일</th>
			</tr>
			<c:forEach var="mp" items="${mp_list}">
			<tr>
				<td><a href="${pageContext.request.contextPath}/post/detail.do?post_num=${mp.post_num}">${mp.post_title}</a></td>
				<td><a href="${pageContext.request.contextPath}/post/detail.do?post_num=${mp.post_num}">${mp.post_content}</a></td>
				<td>${mp.post_date}</td>
			</tr>
			</c:forEach>		
		</table>
		</c:if>
		<div class="align-center">${mp_page}</div>
		
		</div>
	</div>
		<!-- [2. 작성글] 끝 -->
		
		
		
		<!-- 사용자 [3. 주문목록] 시작 -->
		<div id="order" class="tab_contents">
		<div class="content-main container">
		<br><h2><a href="myPage.do#order">주문목록</a></h2><br>
		<!-- 검색창 시작 : get방식 -->
			<form id="search_form3_1" action="myPage.do#order" method="get" class="d-flex" role="search">
						<select name="user_orderkeyfield" class="form-select" style="width:55%;">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>주문상태</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>주문명</option>
						</select>
						<input type="search" size="16" name="user_orderkeyword" id="user_orderkeyword" value="${param.keyword}" class="form-control me-2">
					
						<input type="submit" value="검색" class="btn btn-outline-success">
					
				
			</form>
			<script type="text/javascript">
				$(function(){
					$('#search_form3_1').submit(function(){
						if($('#user_orderkeyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#user_orderkeyword').val('').focus();
							return false;
						}
						
						if()
					});
				});
			</script> 
			<!-- 검색창 끝 -->
			<table class="table table-hover align-center">
				<tr>
					<th>주문번호</th>
					<th>주문상태</th>
					<th>주문명</th>
					<th>가격</th>
					<th>주문일</th>
					<th>주문수정일</th>
				</tr>
				<c:forEach var="order" items="${orderList}">
				<tr>
					<td>
						<a href="${pageContext.request.contextPath}/order/userModifyForm.do?order_num=${order.order_num}">
							${order.order_num}
						</a>
					</td>
					<td>
						<a href="${pageContext.request.contextPath}/order/userModifyForm.do?order_num=${order.order_num}">
							<c:if test="${order.status == 1}"><b class="order-waiting">배송대기</b></c:if>
							<c:if test="${order.status == 2}"><b>배송준비중</b></c:if>
							<c:if test="${order.status == 3}"><b>배송중</b></c:if>
							<c:if test="${order.status == 4}"><b class="order-success">배송완료</b></c:if>
							<c:if test="${order.status == 5}"><b class="order-cancel">주문취소</b></c:if>
						</a>
					</td>
					<td><a href="${pageContext.request.contextPath}/order/userModifyForm.do?order_num=${order.order_num}">${order.book_title}</a></td>
					<td><fmt:formatNumber value="${order.order_total}"/>원</td>
					<td>${order.order_date}</td>
					<td>${order.modify_date}</td>
				</tr>
				</c:forEach>
			</table>
			<div class="align-center">${userOrderpage}</div>
			</div>
		</div>
		<!-- [3. 주문목록] 끝 -->
		
		<script type="text/javascript">
			$(function(){
				let hash = window.location.hash;
				if(hash){
					$(this).addClass('on').siblings().removeClass('on')
					$('.tab_contents').removeClass('on')
					$(hash).addClass('on')
					$('.tabWrap li').removeClass('on');
					$('.tabWrap').find('li[data-tab='+hash.substring(1)+']').addClass('on');
				}
			});
			$('.tabWrap li').click(function(){
				const getID = $(this).attr('data-tab')
				console.log(getID)
				$(this).addClass('on').siblings().removeClass('on')
				$('.tab_contents').removeClass('on')
				$("#" + getID).addClass('on')
			})
		</script>	
		</c:if>
		
		
		<!-- 관리자 마이페이지 메뉴 시작 -->
		<c:if test="${!empty user_num && user_auth == 9}">
		<ul class="tabWrap" style="border-color:#b3b3b3">
			<li data-tab="admin_book" style="cursor: pointer;" class="on"><a href="myPage.do#admin_book">도서관리</a></li>
			<li data-tab="admin_order" style="cursor: pointer;"><a href="myPage.do#admin_order">주문관리</a></li>
			<li data-tab="admin_member" style="cursor: pointer;"><a href="myPage.do#admin_member">회원관리</a></li>
			<li data-tab="admin_report" style="cursor: pointer;"><a href="myPage.do#admin_report">신고내역</a></li>
			<li data-tab="admin_request" style="cursor: pointer;"><a href="myPage.do#admin_request">도서신청</a></li>
		</ul>
		
	
		
		<!-- [1. 도서 관리] 시작 -->
		<div id="admin_book" class="tab_contents on">
			<div class="content-main container">
			<br><h2><a href="myPage.do#admin_book">도서 관리</a></h2>
			<!-- 검색창 시작 : get방식 -->
			<form id="search_form" action="myPage.do#admin_book" method="get"  class="d-flex" role="search">
				<select name="keyfield" class="form-select">
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>도서명</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자명</option>
				</select>
				<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}"  class="form-control me-2">
				<input type="submit" value="검색" class="btn btn-outline-success">
			</form>
			<script type="text/javascript">
				$(function(){
					$('#search_form').submit(function(){
						if($('#keyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#keyword').val('').focus();
							return false;
						}
					});
				});
			</script><br>
			<!-- 검색창 끝 -->
		<div class="list-space">
			<input type="button" value="삭제" class="btn btn-outline-secondary" id="selectDelete_btn" style="float:left;">
			<input type="button" value="도서 등록" onclick="location.href='${pageContext.request.contextPath}/book/writeForm.do'" class="btn btn-outline-secondary" style="float:right;">
		</div><br>
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 상품이 없습니다.
			</div>		
		</c:if>
		
		<c:if test="${count > 0}">
			<table class="table table-hover align-center">
				<tr>
					<th><input type="checkbox" name="allCheck" id="allCheck"></th>
					<th>도서번호</th>
					<th>도서명</th>
					<th>저자명</th>
					<th>출판사</th>
					<th>재고</th>
					<th>등록일</th>
				</tr>
				<c:forEach var="book" items="${list}">
				<tr>
					<td><input type="checkbox" class="chBox" name="chBox" value="${book.bk_num}"></td>
					<td>${book.bk_num}</td>
					<td><a href="${pageContext.request.contextPath}/book/updateForm.do?bk_num=${book.bk_num}">${book.title}</a></td>
					<td>${book.author}</td>
					<td>${book.publisher}</td>
					<td><fmt:formatNumber value="${book.stock}"/></td>
					<td>${book.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
			<div class="page-button">${page}</div>
		</c:if>
		</div>
		</div>
		<script type="text/javascript">
		//모두 선택 체크박스 클릭 이벤트
		$("#allCheck").click(function(){
			 var chk = $("#allCheck").prop("checked");
			 if(chk) {
			  $(".chBox").prop("checked", true);
			 } else {
			  $(".chBox").prop("checked", false);
			 }
		});
		
		//모두 선택일 때 개별 체크박스 하나 해제 시 모두 선택 해제
		 $(".chBox").click(function(){ 
			  $("#allCheck").prop("checked", false);
		});
		
		
		//선택 삭제 버튼 클릭 이벤트
		 $("#selectDelete_btn").click(function(){
		 		if($("input[class='chBox']:checked").length < 1){
					alert('하나 이상의 항목을 선택하세요');
					return false;
				}
				
			   let checkArr = new Array();
			   
			   $("input[class='chBox']:checked").each(function(index,item){
			    	checkArr.push($(this).val());
			   });
			   
			   $.ajax({
				    url:'deleteBooks.do',
				    type:'post',
				    data:{'checkArr':checkArr.toString()},
				    dataType:'json',
				    success : function(param){
				    	let choice = confirm("정말 삭제하시겠습니까?");
				    	if(choice){
							if(param.result == 'logout'){
								alert('로그인 후 삭제할 수 있습니다.');
							}else if(param.result == 'success'){
								alert('등록된 도서가 삭제되었습니다.');
								location.href='myPage.do';
							}else{
								alert('도서 정보 삭제 중 오류가 발생했습니다.');
							}
						}else{
							return false;
						}
				    },
				    error:function(){
				    	alert('네트워크 오류 발생');
				    }
			   });
		 });
		
		</script>
		
	<!-- [1. 도서 관리] 끝 -->
	
	
	<!-- [2. 주문 관리] 시작 -->
		<div id="admin_order" class="tab_contents">
			<div class="content-main container">
				<h2><a href="myPage.do#admin_order">주문 관리</a></h2>
					<!-- 검색창 시작 : get방식 -->
					<form id="search_form2" action="myPage.do#admin_order" method="get" class="d-flex">
						<select name="adminOrderkeyfield" class="form-select">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>주문상태</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>회원ID</option>
							<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>도서명</option>
						</select>
							<input type="search" size="16" name="adminOrderkeyword" id="adminOrderkeyword" value="${param.keyword}" class="form-control me-2">
							<input type="submit" value="검색" class="btn btn-outline-success">	
					</form>
					<script type="text/javascript">
						$(function(){
							$('#search_form2').submit(function(){
								if($('#adminOrderkeyword').val().trim() == ''){
									alert('검색어를 입력하세요');
									$('#adminOrderkeyword').val('').focus();
									return false;
								}
							});
						});
					</script>
					<!-- 검색창 끝 -->
					<c:if test="${count == 0}">
						<div class="result-display">
							표시할 상품이 없습니다.
						</div>		
					</c:if>
					
					<c:if test="${count > 0}">
				<table class="table table-hover align-center">
					<tr>
						<th>주문번호</th>
						<th>도서명</th>
						<th>아이디</th>
						<th>주문 상태</th>
						<th>총 주문 금액</th>
						<th>주문 날짜</th>
					</tr>
					<c:forEach var="admin_order" items="${adminOrderlist}"> 
					<tr>
						<td><a href="${pageContext.request.contextPath}/order/adminModifyForm.do?order_num=${admin_order.order_num}">${admin_order.order_num}</a></td>
						<td><a href="${pageContext.request.contextPath}/order/adminModifyForm.do?order_num=${admin_order.order_num}">${admin_order.book_title}</a></td>
						<td>${admin_order.id}</td>
						<td>
							<a href="${pageContext.request.contextPath}/order/userModifyForm.do?order_num=${admin_order.order_num}">
								<c:if test="${admin_order.status == 1}"><b class="order-waiting">배송대기</b></c:if>
								<c:if test="${admin_order.status == 2}"><b>배송준비중</b></c:if>
								<c:if test="${admin_order.status == 3}"><b>배송중</b></c:if>
								<c:if test="${admin_order.status == 4}"><b class="order-success">배송완료</b></c:if>
								<c:if test="${admin_order.status == 5}"><b class="order-cancel">주문취소</b></c:if>
							</a>
						</td>
						<td>${admin_order.order_total}</td>
						<td>${admin_order.order_date}</td>
					</tr>
					</c:forEach>
				</table>
				<div class="align-center">${adminOrderpage}</div>
				</c:if>
			</div>
		</div>
		<!-- [2. 주문 관리] 끝 -->
		
		
		<!-- [3. 회원 관리] 시작 -->
		<div id="admin_member" class="tab_contents">
			<div class="content-main container">
				<h2><a href="myPage.do#admin_member">회원 관리</a></h2>
				
				<!-- 검색창 시작 : get방식 -->
				<form id="search_form3" action="myPage.do#admin_member" method="get" class="d-flex">
					<select name="adminMemberKeyfield" class="form-select">
						<option value="1" <c:if test="${param.adminMemberKeyfield==1}">selected</c:if>>이름</option>
						<option value="2" <c:if test="${param.adminMemberKeyfield==2}">selected</c:if>>이메일</option>
					</select>
						<input type="search" size="16" name="adminMemberKeyword" id="adminMemberKeyword" value="${param.adminMemberKeyword}" class="form-control me-2">
						
						<input type="submit" value="검색" class="btn btn-outline-success">
				</form>
				<script type="text/javascript">
					$(function(){
						$('#search_form3').submit(function(){
							if($('#adminMemberKeyword').val().trim() == ''){
								alert('검색어를 입력하세요');
								$('#adminMemberKeyword').val('').focus();
								return false;
							}
						});
					});
				</script>
				<!-- 검색창 끝 -->
				
				<c:if test="${adminMemberCount == 0}">
					<div class="result-display">
						표시할 회원정보가 없습니다.
					</div>		
				</c:if>
				<c:if test="${adminMemberCount > 0}">
					<table class="table table-hover align-center">
						<tr>
							<th>이름(닉네임)</th>
							<th>성별</th>
							<th>생년월일</th>
							<th>이메일</th>
							<th>계정 생성일</th>
							<th>최근 로그인 날짜</th>
						</tr>
						<c:forEach var="admin_member" items="${adminMemberList}"> 
							<tr>
								<td>${admin_member.name}</td>
								<td>${admin_member.sex}</td>
								<td>${admin_member.birthday}</td>
								<td>${admin_member.email}</td>
								<td>${admin_member.reg_date}</td>
								<td>${admin_member.latest_login}</td>
							</tr>
						</c:forEach>
					</table>
					<div class="align-center">${adminMemberPage}</div>
				</c:if>
			</div>
		</div>
		<!-- [3. 회원 관리] 끝 -->
		
		
		<!-- [4. 신고 내역] 시작 -->
		<div id="admin_report" class="tab_contents">
		<div class="content-main container">
		<br><h2><a href="myPage.do#admin_report">신고 내역</a></h2>
		
		<!-- 검색창 시작 : get방식 -->
			<form id="search_form4" action="myPage.do#admin_report" method="get" class="d-flex" role="search">
				<select name="repoKeyfield" class="form-select" style="width:180px;">
					<option value="1" <c:if test="${param.repoKeyfield==1}">selected</c:if>>신고유형</option>
				</select>
				<input type="search" size="16" name="repoKeyword" id="repoKeyword" value="${param.repoKeyword}" class="form-control me-2">
				<input type="submit" value="조회" class="btn btn-outline-success">
			</form>
			<script type="text/javascript">
				$(function(){
					$('#search_form4').submit(function(){
						if($('#repoKeyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#repoKeyword').val('').focus();
							return false;
						}
					});
				});
			</script> 
			<!-- 검색창 끝 -->
			<input type="button" value="선택 삭제" class="btn btn-outline-secondary" id="selecdel_btn">
			<c:if test="${repoCount == 0}">
				<div class="result-display">
					신고 내역이 없습니다.
				</div>		
			</c:if>
			<c:if test="${repoCount > 0}">
			<table class="table table-hover align-center">
				<tr>
					<th><input type="checkbox" name="allchk" id="allchk"></th>
					<th>신고 번호</th>
					<th>회원 번호</th>
					<th>신고 유형</th>
					<th>신고 내용</th>
					<th>신고 날짜</th>
				</tr>
				<c:forEach var="report" items="${repoList}"> 
				<tr>
					<td><input type="checkbox" class="chkbox" name="chkbox" value="${report.repo_num}"></td>
					<td><a href="${pageContext.request.contextPath}/post/detailReport.do?repo_num=${report.repo_num}">${report.repo_num}</a></td>
					<td>${report.mem_num}</td>
					<td>${report.repo_category}</td>
					<td>${report.repo_content}</td>
					<td>${report.repo_date}</td>
				</tr>
				</c:forEach>
			</table>
			<script type="text/javascript">
				//이벤트 연결
				//모두 선택 체크박스 클릭 이벤트
				$("#allchk").click(function(){
			 		var chk = $("#allchk").prop("checked");
			 		if(chk) {
			  		$(".chkbox").prop("checked", true);
			 		} else {
			  		$(".chkbox").prop("checked", false);
			 		}
				});
		
				//모두 선택일 때 개별 체크박스 하나 해제 시 모두 선택 해제
				$(".chkbox").click(function(){ 
			  		$("#allchk").prop("checked", false);
				});
				
				//선택 삭제
				$('#selecdel_btn').click(function(){
					if($('input:checkbox[name=chkbox]:checked').length<1){
						alert('삭제할 항목을 선택하세요');
						return false;
					}
					let selecdel = new Array();
					$('input:checkbox[name=chkbox]:checked').each(function(index,item){
						selecdel.push($(this).val());
					});
					$.ajax({
						url:'deleteSelecReport.do',
						type:'post',
						data:{'selecdel':selecdel.toString()},
						dataType:'json',
						success:function(param){
							let choice = confirm("정말 삭제하시겠습니까?");
					    	if(choice){
								if(param.result == 'logout'){
									alert('로그인 후 삭제할 수 있습니다.');
								}else if(param.result =='success'){
									alert('신고 내역이 삭제되었습니다.');
									history.go(0);
								}else if(param.result == 'wrongAccess'){
									alert('잘못된 접근입니다.');
								}else{
									alert('신고 내역 삭제 중 오류가 발생했습니다.');
								}
							}
					    },
					    error:function(){
					    	alert('네트워크 오류 발생');
					    }
				   });
				});
			</script>
			</c:if>
			<div class="align-center">${repoPage}</div>
		</div>
		</div>
		<!-- [4. 신고 내역] 끝 -->
		
		
		<!-- [5. 도서 신청] 시작 -->
		<div id="admin_request" class="tab_contents">
			<div class="content-main container">
			<h2><a href="myPage.do#admin_request">도서신청</a></h2>
			<form id="search_form5" action="myPage.do#admin_request" method="get" class="d-flex">
				<select name="req_Keyfield" class="form-select">
					<option value="1" <c:if test="${param.req_keyfield==1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.req_Keyfield==2}">selected</c:if>>저자</option>
				</select>
					<input type="search" size="16" name="req_Keyword" id="req_Keyword" value="${param.req_Keyword}" class="form-control me-2">
					
					<input type="submit" value="조회" class="btn btn-outline-success">
			</form>
			<script type="text/javascript">
				$(function(){
					$('#search_form5').submit(function(){
						if($('#req_Keyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#req_Keyword').val('').focus();
							return false;
						}
					});
				});
			</script> 
			<!-- 검색창 끝 -->
		<!-- <form id="request_admin"> -->
		<div class="list-space align-right">
		
		<input type="button" value="추가완료" id="reqstate_done" class="btn btn-outline-secondary" id="selectDelete_btn" style="float:left;">
			<input type="button" value="도서 등록" onclick="location.href='${pageContext.request.contextPath}/book/writeForm.do'" class="btn btn-outline-secondary" style="float:right;">
		</div>
		
		<c:if test="${req_Count == 0 }">
		<div class="result-display">
			신청된 도서가 없습니다.
		</div>
		</c:if>
		
		<c:if test="${req_Count > 0}">
			<table class="table table-hover align-center">
				<tr>
					<th></th>
					<th>진행상태</th>
					<th>제목</th>
					<th>저자</th>
					<th>출판사</th>
					<th>신청일</th>
					<th>추천수</th>
				</tr>
			<tbody>
				<c:forEach var="request" items="${req_list}">
			<tr>
				<td class="align-center">
				
					<input type="checkbox" class="reqstate" name="reqstate"
					 <c:if test="${request.req_state == 1}">disabled</c:if>
					 value="${request.req_num}" data-num="${request.req_state}">
					
				</td> 
				<td>
				<c:if test="${request.req_state == 0}"><button class="reqbtn readybtn">준비중</button></c:if>
				<c:if test="${request.req_state == 1}"><button class="reqbtn donebtn">추가완료</button></c:if>
				</td>			               
				<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a></td>
				<td>${request.req_author}</td>
				<td>${request.req_publisher}</td>
				<c:choose>
					<c:when test="${request.req_modifydate!=null}">
						<td>${request.req_modifydate}</td>
					</c:when>
					<c:otherwise>
						<td>${request.req_date}</td>
					</c:otherwise>
				</c:choose>
				<td>
					<span class="output-fcount">${request.cnt}</span>	
				</td>							
			</tr>
			</c:forEach>
			</table>
			
			<!-- </form> -->
			<script type="text/javascript">
				$('#reqstate_done').click(function(){
					if($('input[type=checkbox]:checked').length<1){
						alert('하나 이상의 항목을 선택하세요');
						return false;
					}
					let reqstate = new Array();
					$('input[type="checkbox"]:checked').each(function(index,item){
						reqstate.push($(this).val());
					});
					$.ajax({
						url:'requestStateUpdate.do',
						type:'post',
						data:{'reqstate':reqstate.toString()},
						dataType:'json',
						success:function(param){
							if(param.result == 'success'){
								alert('추가되었습니다.');
							}else{
								alert('오류발생');
							}
						},
						error:function(){
							alert('추가할 도서를 선택하세요.')
						}
					});
				});
			</script>
			<div class="align-center">${req_Page}</div>
		</c:if>
		</div>
		</div>
		<!-- [5. 도서 신청] 끝 -->
		<!-- 관리자 마이페이지 메뉴 끝 -->
		
		<script type="text/javascript">
			$(function(){
				let hash = window.location.hash;
				if(hash){
					$(this).addClass('on').siblings().removeClass('on')
					$('.tab_contents').removeClass('on')
					$(hash).addClass('on')
					$('.tabWrap li').removeClass('on');
					$('.tabWrap').find('li[data-tab='+hash.substring(1)+']').addClass('on');
				}
			});
			$('.tabWrap li').click(function(){
				const getID = $(this).attr('data-tab')
				console.log(getID)
				$(this).addClass('on').siblings().removeClass('on')
				$('.tab_contents').removeClass('on')
				$("#" + getID).addClass('on')
			})
		</script>
		</c:if>
	</div>
</div>
</body>
</html>

