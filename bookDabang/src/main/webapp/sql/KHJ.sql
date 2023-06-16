--도서 목록    
CREATE TABLE book_list (
    bk_num number,
    bk_title varchar2(120) not null,
    bk_author varchar2(60) not null,
    bk_publisher varchar2(60) not null,
    bk_price number(8) not null,
    bk_category varchar2(50) not null,
    bk_thumbnail varchar2(150) not null,
    bk_content clob not null,
    bk_stock number(4) default 0 not null,
    bk_reg_date date default SYSDATE not null,
    mem_num number not null,
    constraint book_list_pk primary key (bk_num),
    constraint  book_list_fk1 foreign key (mem_num) references member (mem_num)
);

CREATE SEQUENCE book_list_seq;


--카테고리(도서분류)
CREATE TABLE book_category (
    bookcat_num number(2),
    bookcat_name varchar2(50) not null,
    constraint book_category_pk primary key (bookcat_num)
);

CREATE SEQUENCE book_category_seq;


--책갈피(즐겨찾기)
CREATE TABLE book_mark (
    mark_num number,
    bk_num number not null,
    mem_num number not null,
    constraint book_mark_pk primary key (mark_num),
    constraint book_mark_fk1 foreign key (bk_num) references book_list (bk_num),
    constraint book_mark_fk2 foreign key (mem_num) references member (mem_num)
);

CREATE SEQUENCE book_mark_seq;
