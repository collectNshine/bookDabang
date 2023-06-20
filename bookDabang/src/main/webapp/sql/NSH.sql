-- 채팅방
create table chat(
	chat_num number not null,
	chat_title varchar2(50) not null,
	chat_img varchar2(150),
	hit number(9) default 0 not null,
	reg_date date default sysdate not null,
	constraint chatlist_pk primary key (chat_num)
);

create sequence chat_seq;

-- 채팅 내용
create table chat_message(
	message_num number not null,
	mem_num number not null,
	chat_num number not null,
	chat_content clob not null,
	chat_date date default sysdate not null,
	constraint chatmessage_pk primary key (message_num),
	constraint chatmessage_fk foreign key (chat_num) references chat (chat_num)
);

create sequence chat_message_seq;