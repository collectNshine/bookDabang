
create table member(
mem_num number,
id varchar2(20) unique not null,
auth number(1) default 0 not null, --회원 등급 :  0 탈퇴회원, 1 일반회원, 9 관리자회원
state number(1) default 0 not null,--0 활성계정,1 휴면계정,2 가입이력은 있으나 DB에 남아있지 않는 계정 
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
try_login number(1)default 0, --로그인 시도횟수 1 ~ 3의 값을 가진다.

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
constraint member_sleep_pk primary key (mem_num),
constraint member_sleep_fk foreign key (mem_num)
							 references member (mem_num)
);


create table notice_board(--공지 게시판 정보
noti_num number,
noti_categoty number not null, --1: 회원 2:주문/ 주문변경 3:결제 4:증빙서류 5:공지사항
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

create table notice_reply (-- 공지 댓글
re_num number,
re_content varchar2(150) not null,
re_emo varchar2(1000) ,
re_ip varchar2(15) not null,
re_date date not null,
re_motifydate date,
noti_num number not null,
mem_num number not null,
constraint noti_reply_pk 
                   primary key (re_num),
 constraint noti_reply1_fk
                 foreign key (noti_num) 
                 references notice_board (noti_num),
constraint noti_reply2_fk
                 foreign key (mem_num) 
                 references member (mem_num)
);

create sequence notice_reply_seq;

create table qna_board (--1:1 게시판
qna_num number, --글번호
refer number default 0 not null, -- 글의 그룹
step number default 0 not null,-- 답글의 순서
depth number default 0 not null,-- 답글의 들여쓰기 
qna_title varchar2(150) not null, -- 글 제목
qna_content varchar2(4000)not null, -- 글 내용
reg_date date not null default SYSDATE,-- 글 작성일
mem_num number not null,-- 글 작성자 
delflag number not null default 0, -- 미삭제:0, 삭제:1
constraint qna_board_pk 
                   primary key (qna_num),
constraint qna_board_fk
                 foreign key (mem_num) 
                 references member (mem_num)
);

create sequence qna_board_seq;