package kr.post.vo;

import java.sql.Date;

public class PostVO {
	private int post_num; //서평 번호
	private String post_title; //제목
	private String post_content; //내용
	private String post_date; //등록일
	private String post_modifydate; //수정일
	private String post_photo; //첨부파일명
	private String post_ip; //ip주소
	private int mem_num; //회원번호
	private int bk_num; //도서번호
	
	private String nickname; //회원 이름(닉네임)
	private String photo; //회원 프로필 사진 파일명
	private String thumbnail; //책 표지
	private int cnt; //좋아요 개수
	private int rcnt; //댓글 개수
	
	public int getPost_num() {
		return post_num;
	}
	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public String getPost_date() {
		return post_date;
	}
	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}
	public String getPost_modifydate() {
		return post_modifydate;
	}
	public void setPost_modifydate(String post_modifydate) {
		this.post_modifydate = post_modifydate;
	}
	public String getPost_photo() {
		return post_photo;
	}
	public void setPost_photo(String post_photo) {
		this.post_photo = post_photo;
	}
	public String getPost_ip() {
		return post_ip;
	}
	public void setPost_ip(String post_ip) {
		this.post_ip = post_ip;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getBk_num() {
		return bk_num;
	}
	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}
	public String getName() {
		return nickname;
	}
	public void setName(String nickname) {
		this.nickname = nickname;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getRcnt() {
		return rcnt;
	}
	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}
	
}
