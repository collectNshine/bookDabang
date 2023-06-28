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
	private String photo; //프로필 사진
	private String title; //도서명
	private String author; //저자명
	private String thumbnail; //책 표지
	
	private int cnt_like;
	private String clicked_like;
	private int cnt_dislike;
	private String clicked_dislike; 
	
	
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public int getCnt_like() {
		return cnt_like;
	}
	public void setCnt_like(int cnt_like) {
		this.cnt_like = cnt_like;
	}
	public String getClicked_like() {
		return clicked_like;
	}
	public void setClicked_like(String clicked_like) {
		this.clicked_like = clicked_like;
	}
	public int getCnt_dislike() {
		return cnt_dislike;
	}
	public void setCnt_dislike(int cnt_dislike) {
		this.cnt_dislike = cnt_dislike;
	}
	public String getClicked_dislike() {
		return clicked_dislike;
	}
	public void setClicked_dislike(String clicked_dislike) {
		this.clicked_dislike = clicked_dislike;
	}
	
	
}
