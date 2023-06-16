create table book_request(
 req_num number,
 req_title varchar2(120) not null,
 req_author varchar2(60) not null,
 req_publisher varchar2(60) not null,
 req_etc varchar2(150),
 req_ip varchar2(15) not null,
 req_date date default SYSDATE not null,
 req_modifydate date,
 mem_num number not null,
 constraint book_request_pk primary key (req_num),
 constraint book_request_fk foreign key (mem_num) references member(mem_num)
);
create sequence book_request_seq;

create table book_request_fav(
 req_fav_num number,
 req_num number not null,
 mem_num number not null,
 constraint book_request_fav_pk primary key (req_fav_num),
 constraint book_request_fav_fk1 foreign key (req_num) references book_request(req_num),
 constraint book_request_fav_fk2 foreign key (mem_num) references member(mem_num)
);
create sequence book_request_fav_seq;
