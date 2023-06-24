package kr.book.vo;

public class ReviewVO {
	private int review_num; //댓글 번호
	private String review_content; //내용
	private String review_ip; //ip
	private String review_date; //등록일
	private String review_modifydate; //수정일
	private int mem_num; //작성자 회원번호
	private int bk_num; //도서 번호
	private String name; //닉네임(이름)
	
	public int getReview_num() {
		return review_num;
	}
	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public String getReview_ip() {
		return review_ip;
	}
	public void setReview_ip(String review_ip) {
		this.review_ip = review_ip;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}
	public String getReview_modifydate() {
		return review_modifydate;
	}
	public void setReview_modifydate(String review_modifydate) {
		this.review_modifydate = review_modifydate;
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
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
