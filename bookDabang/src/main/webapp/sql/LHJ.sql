create table orders(
	order_num number,
	book_title varchar2(120) not null,
	order_total number(9) not null,
	payment number(1) not null,
	receive_name varchar2(30) not null,
	receive_post varchar2(5) not null,
	receive_address1 varchar2(90) not null,
	receive_address2 varchar2(90) not null,
	receive_phone varchar2(15) not null,
	notice varchar2(4000) not null,
	order_date date default sysdate not null,
	mem_num number not null,
	constraint order_pk primary key (order_num),
	constraint order_fk foreign key (mem_num) references member (mem_num)
);

create sequence order_seq;

create table order_detail(
	detail_num number not null,
	bk_num number not null,
	book_title varchar2(120) not null,
	book_price number(8) not null,
	book_total number(8) not null,
	order_quantity number(7) not null,
	order_num number not null,
	constraint order_detail_pk primary key (detail_num),
	constraint order_detail_fk foreign key (order_num) references orders (order_num)
);

create sequence order_detail_seq;
