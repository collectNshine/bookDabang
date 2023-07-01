<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#left{
width:20%;
height:100%;
float:left;
padding:1rem;
}
</style>
<div id="list">
	<div id="left">
		<ul>
			<li><b><a href="noticeList.do" >자주하는 질문</a></b></li>   
			<li><a href="noticeList.do?noti_category=1">회원</a></li>
			<li><a href="noticeList.do?noti_category=2">주문/주문변경</a></li>
			<li><a href="noticeList.do?noti_category=3">결제</a></li>
			<li><a href="noticeList.do?noti_category=4">증빙서류</a></li>
			<li><p></li>
			<li><hr size="1" noshade width="100%"></li>
			<li><b><a href="noticeList.do?noti_category=5">공지사항</a></b></li>   
		</ul>
	</div>
</div>