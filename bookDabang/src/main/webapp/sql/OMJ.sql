create table member(
mem_num number,
id varchar2(20) unique not null,
auth number(1) default 0 not null, --회원 등급 :  0 탈퇴회원, 1 일반회원, 9 관리자회원
state number(1) default 0 not null,--0 활성계정,1 휴면계정, 2 삭제계정, 3 가입이력은 있으나 DB에 남아있지 않는 계정 
constraint member_pk primary key(mem_num)
);

create sequence member_seq; 

create table member_detail(--0 활성계좌 정보
mem_num number,
name varchar2(30) not null,
passwd varchar2(64) not null,--*)8~16자 영문 대 소문자, 숫자, 특수문자
sex number(1) not null,-- 1 남성, 2여성
birthday date not null,
phone varchar2(11) not null, 
photo varchar2(150),
zipcode varchar2(5) not null,
address1 varchar2(90) not null,
address2 varchar2(90) not null,
email varchar2(50) not null,
reg_date date default SYSDATE not null,
latest_login date default SYSDATE not null,
try_login number(1),default 0 --로그인 시도횟수 1 ~ 3의 값을 가진다.

constraint member_detail_pk primary key (mem_num),
constraint member_detail_fk foreign key (mem_num)
							 references member (mem_num)
);

create table member_sleep(--1 휴면계정 정보
mem_num number,
sname varchar2(30) not null,
spasswd varchar2(64) not null,--*)8~16자 영문 대 소문자, 숫자, 특수문자
ssex number(1) not null,-- 1 남성, 2여성
sbirthday date not null,
sphone varchar2(11) not null, 
sphoto varchar2(150),
szipcode varchar2(5) not null,
saddress1 varchar2(90) not null,
saddress2 varchar2(90) not null,
semail varchar2(50) not null,
sreg_date date default SYSDATE not null,
ssleep_date date not null,
sharddel_date date not null,
constraint member_sleep_pk primary key (mem_num),
constraint member_sleep_fk foreign key (mem_num)
							 references member (mem_num)
);


create table notice_board(--공지 게시판 정보
noti_num number,
noti_date date not null,
noti_title varchar2(150) not null,
noti_content clob not null,
latest_ed_date date not null,
mem_num number not null,
constraint notice_board_pk primary key (noti_num),
constraint notice_board_fk foreign key (mem_num)
							references member (mem_num)
);

create sequence notice_seq;