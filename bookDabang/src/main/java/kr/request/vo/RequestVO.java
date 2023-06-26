package kr.request.vo;

import java.sql.Date;

public class RequestVO {
	private int req_num; //글 번호
	private String req_title; //책 제목
	private String req_author; //저자
	private String req_publisher; //출판사
	private String req_etc; //기타
	private String req_ip; //ip
	private Date req_date; //등록날짜
	private Date req_modifydate; //수정날짜
	private int mem_num; //회원번호
	private String id; //회원이름
	private int req_state; //신청 상태 0:준비중 1:추가완료
	
	
	private int cnt;//좋아요 개수
	private String clicked; //로그인한 사용자가 클릭했지는 clicked 면 클릭, null이면 미 클릭

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReq_num() {
		return req_num;
	}
	public void setReq_num(int req_num) {
		this.req_num = req_num;
	}
	public String getReq_title() {
		return req_title;
	}
	public void setReq_title(String req_title) {
		this.req_title = req_title;
	}
	public String getReq_author() {
		return req_author;
	}
	public void setReq_author(String req_author) {
		this.req_author = req_author;
	}
	public String getReq_publisher() {
		return req_publisher;
	}
	public void setReq_publisher(String req_publisher) {
		this.req_publisher = req_publisher;
	}
	public String getReq_etc() {
		return req_etc;
	}
	public void setReq_etc(String req_etc) {
		this.req_etc = req_etc;
	}
	public String getReq_ip() {
		return req_ip;
	}
	public void setReq_ip(String req_ip) {
		this.req_ip = req_ip;
	}
	public Date getReq_date() {
		return req_date;
	}
	public void setReq_date(Date req_date) {
		this.req_date = req_date;
	}
	public Date getReq_modifydate() {
		return req_modifydate;
	}
	public void setReq_modifydate(Date req_modifydate) {
		this.req_modifydate = req_modifydate;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getClicked() {
		return clicked;
	}
	public void setClicked(String clicked) {
		this.clicked = clicked;
	}
	public int getReq_state() {
		return req_state;
	}
	public void setReq_state(int req_state) {
		this.req_state = req_state;
	}

	
}
