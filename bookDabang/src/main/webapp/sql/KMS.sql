--서평
CREATE TABLE post(
 post_num number not null,
 post_title varchar2(120) not null,
 post_content clob not null,
 post_date date not null,
 post_modifydate date,
 post_photo varchar2(50),
 post_ip varchar2(15) not null,
 mem_num number not null,
 bk_num number not null,
 constraint post_pk primary key (post_num),
 constraint post_fk1 foreign key (mem_num) references member(mem_num),
 constraint post_fk2 foreign key (bk_num) references booklist(bk_num)
);

CREATE SEQUENCE post_seq;

--서평 좋아요
CREATE TABLE post_fav(
 post_fav_num number not null,
 post_num number not null,
 mem_num number not null,
 constraint post_fav_pk primary key (post_fav_num),
 constraint post_fav_fk1 foreign key (post_num) references post(post_num),
 constraint post_fav_fk2 foreign key (mem_num) references member(mem_num)
);

CREATE SEQUENCE post_fav_seq;

--서평 신고
CREATE TABLE post_report(
 repo_num number not null,
 repo_date date not null,
 repo_ip varchar2(15) not null,
 repo_content clob not null,
 repo_category number(1) not null,
 post_num number not null,
 mem_num number not null,
 constraint post_report_pk primary key (repo_num),
 constraint post_report_fk1 foreign key (post_num) references post(post_num),
 constraint post_reportv_fk2 foreign key (mem_num) references member(mem_num)
);

CREATE SEQUENCE post_report_seq;

--서평 댓글
CREATE TABLE post_reply(
 re_num number not null,
 re_content clob not null,
 re_ip varchar2(15) not null,
 re_date date not null,
 re_modifydate date,
 post_num number not null,
 mem_num number not null,
 constraint post_reply_pk primary key (re_num),
 constraint post_reply_fk1 foreign key (post_num) references post(post_num),
 constraint post_reply_fk2 foreign key (mem_num) references member(mem_num)
);

CREATE SEQUENCE post_reply_seq;

--한줄 리뷰
CREATE TABLE review(
 review_num number not null,
 review_content varchar2(150) not null,
 review_ip varchar2(15) not null,
 review_date date not null,
 review_modifydate date,
 mem_num number not null,
 bk_num number not null,
 constraint review_pk primary key (review_num),
 constraint review_fk1 foreign key (mem_num) references member(mem_num),
 constraint review_fk2 foreign key (bk_num) references booklist(bk_num)
);

CREATE SEQUENCE review_seq;

--한줄리뷰 '최고에요'
CREATE TABLE review_like(
 rev_like_num number not null,
 review_num number not null,
 mem_num number not null,
 constraint review_like_pk primary key (rev_like_num),
 constraint review_like_fk1 foreign key (review_num) references review(review_num),
 constraint review_like_fk2 foreign key (mem_num) references member(mem_num)
);

CREATE SEQUENCE review_like_seq;

--한줄리뷰 '별로에요'
CREATE TABLE review_dislike(
 rev_dislike_num number not null,
 review_num number not null,
 mem_num number not null,
 constraint review_dislike_pk primary key (rev_dislike_num),
 constraint review_dislike_fk1 foreign key (review_num) references review(review_num),
 constraint review_dislike_fk2 foreign key (mem_num) references member(mem_num)
);

CREATE SEQUENCE review_dislike_seq;

--서평 댓글 신고
CREATE TABLE post_reply_report(
 re_repo_num number not null,
 re_repo_date date not null,
 re_repo_ip varchar2(15) not null,
 re_repo_content clob not null,
 re_repo_category number(1) not null,
 re_num number not null,
 mem_num number not null,
 constraint post_reply_report_pk primary key (re_repo_num),
 constraint post_reply_report_fk1 foreign key (re_num) references post_reply(re_num),
 constraint post_reply_report_fk2 foreign key (mem_num) references member(mem_num)
);

CREATE SEQUENCE post_reply_report_seq;