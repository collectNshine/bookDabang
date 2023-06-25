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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/request.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">


</script> 
</head>
<body> 
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr size="1" noshade="noshade" width="100%">
		<h2 class="align-center">마이페이지</h2>
	<hr size="1" noshade="noshade" width="100%">
	<div class="mypage-div">
	<!-- 프로필 사진 시작 --> 
		<ul>
			<li style="display:flex;">
				<c:if test="${empty user_photo}">
					<div>
						<img src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
					</div>
					<div>
						<h4>님의 책다방</h4>
						<input type="button" value="정보 수정" onclick="location.href='PasswdCheckForm.do'">
					</div>
					
				</c:if>
				<c:if test="${!empty user_photo}">
					<img src="${pageContext.request.contextPath}/upload/${user_photo}" 
						 width="200" height="200" class="my-photo">
					<div style="display:flex;justify-content: space-around;flex-direction: column;">	 
						 <h4>님의 책다방</h4>
						 <input type="button" value="정보 수정" onclick="location.href='PasswdCheckForm.do'">
					 </div>
				</c:if>
			</li>		
		</ul>
		<!-- 프로필 사진 끝 -->

				
		<!-- 사용자 마이페이지 메뉴 시작 -->
		<c:if test="${!empty user_num && user_auth == 1}">
		<ul class="tabWrap">
			<li data-tab="book_mark" style="cursor: pointer;" class="on">책갈피</li>
			<li data-tab="post" style="cursor: pointer;">작성글</li>
			<li data-tab="order" style="cursor: pointer;">주문목록</li>
		</ul>
		
		<!-- 사용자 [1. 책갈피] 시작 -->
		<div id="book_mark" class="tab_contents on">
			<table>
				<tr>
					<th>NO.</th>
					<th>도서명</th>
					<th>저자</th>
					<th>등록일</th>
				</tr>
				 <c:forEach var="book_mark" items="${book_mark}"> 
				<tr>
					<td>${bookmark.bk_num}</td>
					<td>${bookmark.title}</td>
					<td>${bookmark.author}</td>
					<td>${bookmark.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<!-- [1. 책갈피] 끝 -->
		
		<!-- 사용자 [2. 작성글] 시작 -->
	<div id="post" class="tab_contents">
		<div class="content-main container">
		<!-- 검색창 시작 : get방식 -->
			<form id="search_form" action="myPagePost.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>게시글 번호</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>게시글 내용</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
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
			</script> 
			<!-- 검색창 끝 -->	
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 작성글이 없습니다.
			</div>		
		</c:if>
		
		<c:if test="${count > 0}">
		<table class="table table-hover align-center">
			<tr>
				<th>NO.</th>
				<th>제목</th>
				<th>내용</th>
				<th>등록일</th>
			</tr>
			<c:forEach var="post" items="${postlist}">
			<tr>
				<td>${post.post_num}</td>
				<td>${post.post_title}</td>
				<td>${post.post_content}</td>
				<td>${post.post_date}</td>
			</tr>
			</c:forEach>		
		</table>
		</c:if>
		<div class="align-center">${page}</div>
		<%-- 
		</c:if>
		--%>
		</div>
	</div>
		<!-- [2. 작성글] 끝 -->
		
		
		<!-- 사용자 [3. 주문목록] 시작 -->
		<div id="order" class="tab_contents">
		<div class="content-main container">
		<!-- 검색창 시작 : get방식 -->
			<form id="search_form" action="myPagePost.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>내용</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
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
			</script> 
			<!-- 검색창 끝 -->
			<table>
				<tr>
					<th>NO.</th>
					<th>주문명</th>
					<th>가격</th>
					<th>주문일</th>
				</tr>
				<c:forEach var="order" items="${order}"> 
				<tr>
					<td>${order.order_num}</td>
					<td>${order.book_title}</td>
					<td>${order.order_total}</td>
					<td>${order.reg_date}</td>
				</tr>
				</c:forEach>
			</table>
			</div>
		</div>
		<!-- [3. 주문목록] 끝 -->
		
		<script type="text/javascript">
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
		<ul class="tabWrap">
			<li data-tab="admin_book" style="cursor: pointer;" class="on">도서관리</li>
			<li data-tab="admin_order" style="cursor: pointer;">주문관리</li>
			<li data-tab="admin_member" style="cursor: pointer;">회원관리</li>
			<li data-tab="admin_report" style="cursor: pointer;">신고내역</li>
			<li data-tab="admin_request" style="cursor: pointer;">도서신청</li>
		</ul>
		
		<!-- [1. 도서 관리] 시작 -->
		<div id="admin_book" class="tab_contents on">
			<div class="content-main container">
			<h2><a href="myPage.do">도서 관리</a></h2>
			<!-- 검색창 시작 : get방식 -->
			<form id="search_form" action="myPage.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>도서명</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자명</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
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
			</script> 
			<!-- 검색창 끝 -->
		<div class="list-space align-right">
			<input type="button" value="도서 등록" onclick="location.href='${pageContext.request.contextPath}/book/writeForm.do'">
		</div>
		
		<c:if test="${count == 0}">
			<div class="result-display">
				표시할 상품이 없습니다.
			</div>		
		</c:if>
		
		<c:if test="${count > 0}">
			<table class="table table-hover align-center">
			<thead>
				<tr>
					<th>도서번호</th>
					<th>도서명</th>
					<th>저자명</th>
					<th>출판사</th>
					<th>재고</th>
					<th>분류</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="book" items="${list}">
				<tr>
					<td>${book.bk_num}</td>
					<td><a href="${pageContext.request.contextPath}/book/updateForm.do?bk_num=${book.bk_num}">${book.title}</a></td>
					<td>${book.author}</td>
					<td>${book.publisher}</td>
					<td><fmt:formatNumber value="${book.stock}"/></td>
					<td>${book.category}</td>
					<td>${book.reg_date}</td>
				</tr>
				</c:forEach>
			</tbody>
			</table>
			<div class="align-center">${page}</div>
		</c:if>
		</div>
	</div>
	<!-- [1. 도서 관리] 끝 -->
	
	<!-- [2. 주문 관리] 시작 -->
		<div id="admin_order" class="tab_contents">
			<table>
				<tr>
					<th>주문번호</th>
					<th>도서명</th>
					<th>아이디</th>
					<th>주문 상태</th>
					<th>총 주문 금액</th>
					<th>주문 날짜</th>
				</tr>
				<c:forEach var="admin_order" items="${post}"> 
				<tr>
					<td>${post.post_num}</td>
					<td>${post.post_title}</td>
					<td>${post.post_content}</td>
					<td>${post.post_date}</td>
					<td>${post.post_date}</td>
					<td>${post.post_date}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<!-- [2. 주문 관리] 끝 -->
		
		<!-- [3. 회원 관리] 시작 -->
		<div id="admin_member" class="tab_contents">
			<div class="content-main">
				<h2><a href="myPage.do">회원 관리</a></h2>
				<!-- 검색창 시작 : get방식 -->
				<form id="search_form2" action="myPage.do" method="get">
					<ul class="search">
						<li>
							<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>이름</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>이메일</option>
						</select>
						</li>
						<li>
							<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
						</li>
						<li>
							<input type="submit" value="검색">
						</li>
					</ul>
				</form>
				<script type="text/javascript">
					$(function(){
						$('#search_form2').submit(function(){
							if($('#keyword').val().trim() == ''){
								alert('검색어를 입력하세요');
								$('#keyword').val('').focus();
								return false;
							}
						});
					});
				</script>
				<!-- 검색창 끝 -->
				<c:if test="${count == 0}">
					<div class="result-display">
						표시할 회원정보가 없습니다.
					</div>		
				</c:if>
				<c:if test="${count > 0}">
					<table class="table table-hover align-center">
						<tr>
							<th>이름(닉네임)</th>
							<th>성별</th>
							<th>생년월일</th>
							<th>이메일</th>
							<th>계정 생성일</th>
							<th>최근 로그인 날짜</th>
						</tr>
						<c:forEach var="admin_member" items="${memberlist}"> 
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
					<div class="align-center">${page}</div>
				</c:if>
				</div>
				</div>
		<!-- [3. 회원 관리] 끝 -->
		
		<!-- [4. 신고 내역] 시작 -->
		<div id="admin_report" class="tab_contents">
		<h2><a href="myPage.do">신고 내역</a></h2>
		<%--
		<!-- 검색창 시작 : get방식 -->
			<form id="search_form" action="myPage.do" method="get">
				<ul class="search">
					<li>
						<select name="repoKeyfield">
							<option value="1" <c:if test="${param.repoKeyfield==1}">selected</c:if>>신고유형</option>
							<option value="2" <c:if test="${param.repoKeyfield==2}">selected</c:if>>회원번호</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="repoKeyword" id="repoKeyword" value="${param.repoKeyword}">
					</li>
					<li>
						<input type="submit" value="검색">
					</li>
				</ul>
			</form>
			<script type="text/javascript">
				$(function(){
					$('#search_form').submit(function(){
						if($('#repoKeyword').val().trim() == ''){
							alert('검색어를 입력하세요');
							$('#repoKeyword').val('').focus();
							return false;
						}
					});
				});
			</script> 
			<!-- 검색창 끝 -->
			--%>
			<c:if test="${repoCount == 0}">
				<div class="result-display">
					신고 내역이 없습니다.
				</div>		
			</c:if>
			<table>
				<tr>
					<th>신고 번호</th>
					<th>회원 번호</th>
					<th>신고 유형</th>
					<th>신고 내용</th>
					<th>신고 날짜</th>
				</tr>
				<c:forEach var="report" items="${repoList}"> 
				<tr>
					<td>
					<c:if test="${user_auth == 9}">
					<input class="checkbox" name="checkbox" type="checkbox" value="${report.repo_num}">
					</c:if>
					<a href="${pageContext.request.contextPath}/post/detailReport.do?repo_num=${report.repo_num}">${report.repo_num}</a>
					</td>
					<td>${report.mem_num}</td>
					<td>${report.repo_category}</td>
					<td>${report.repo_content}</td>
					<td>${report.repo_date}</td>
				</tr>
				</c:forEach>
			</table>
			<c:if test="${user_auth == 9}">
			<input id="all_btn" type="button" value="전체 선택">
			<input id="del_btn" type="button" value="삭제"> 
			<script type="text/javascript">
			let del_btn = document.getElementById('del_btn');
			//이벤트 연결
			del_btn.onclick=function(){
				let choice = confirm('삭제하시겠습니까?');
				if(choice){
				//location.replace('deleteReport.do?repo_num=${report.repo_num}');
				//history.go(0);
				}
			};
			</script>
			</c:if>
			<div class="align-center">${repoPage}</div>
		</div>
		<!-- [4. 신고 내역] 끝 -->
		
		<!-- [5. 도서 신청] 시작 -->
		<div id="admin_request" class="tab_contents">
			<div class="content-main container">
			<h2>도서신청</h2>
			<form id="search_form" action="myPage.do" method="get">
				<ul class="search">
					<li>
						<select name="keyfield">
							<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
							<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>저자</option>
							<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>출판사</option>
						</select>
					</li>
					<li>
						<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
					</li>
					<li>
						<input type="submit" value="조회">
					</li>
				</ul>
			</form>
			<!-- 검색창 끝 -->
		<div class="list-space align-right">
			<input type="button" value="도서 등록" onclick="location.href='${pageContext.request.contextPath}/book/writeForm.do'">
		</div>
		
		<c:if test="${req_count == 0 }">
		<div class="result-display">
			신청된 도서가 없습니다.
		</div>
		</c:if>
		
		<c:if test="${req_count > 0}">
			<table class="table table-hover align-center">
			<thead>
				<tr>
					<th>진행상태</th>
					<th>제목</th>
					<th>저자</th>
					<th>출판사</th>
					<th>신청일</th>
					<th>추천수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="req_list" items="${req_list}">
			<tr>
				<td class="align-center"><input type="checkbox" id="req_admin" value="책등록완료"></td>                     
				<td><a href="${pageContext.request.contextPath}/request/detail.do?req_num=${request.req_num}">${request.req_title}</a></td>
				<td>${request.req_author}</td>
				<td>${request.req_publisher}</td>
				<c:choose>
					<c:when test="${request.req_modifydate!=null}">
						<td>${request.req_modifSydate}</td>
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
			</tbody>
			</table>
			<div class="align-center">${req_page}</div>
		</c:if>
		</div>
		<!-- [5. 도서 신청] 끝 -->
		<!-- 관리자 마이페이지 메뉴 끝 -->
		
		<script type="text/javascript">
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
