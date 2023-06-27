-- 채팅방  
create table chat(
	chat_num number not null,
	mem_num number not null,
	chat_title varchar2(100) not null,
	chat_img varchar2(150),
	reg_date date default sysdate not null,
	constraint chatlist_pk primary key (chat_num)
);

create sequence chat_seq;

-- 채팅 참여
create table chat_into(
	chat_num number not null,
	mem_num number not null,
	constraint chatinto_fk foreign key (chat_num) references chat (chat_num)
);

-- 채팅 내용
create table chat_message(
	message_num number not null,
	mem_num number not null,
	chat_num number not null,
	chat_content clob not null,
	chat_date date default sysdate not null,
	constraint chatmessage_pk primary key (message_num),
	constraint chatmessage_fk foreign key (chat_num) references chat (chat_num),
	constraint chatmessage_fk2 foreign key (mem_num) references member (mem_num)
);

create sequence chat_message_seq;

-- 장바구니
create table cart(
	cart_num number,
	bk_num number not null,
	order_quantity number(5) not null,
	cart_date date default sysdate not null,
	mem_num number not null,
	constraint cart_pk primary key (cart_num),
	constraint cart_book_fk foreign key (bk_num) references book_list (bk_num),
	constraint cart_book_fk2 foreign key (mem_num) references member (mem_num)
);

create sequence cart_seq;