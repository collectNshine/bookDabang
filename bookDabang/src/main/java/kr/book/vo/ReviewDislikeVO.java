package kr.book.vo;

public class ReviewDislikeVO {
	private int rev_dislike_num; 
	private int review_num;
	private int mem_num;
	
	//디폴트 생성자
	public ReviewDislikeVO() {}
	
	//생성자 정의
	public ReviewDislikeVO(int review_num, int mem_num) {
		this.review_num = review_num;
		this.mem_num = mem_num;
	}

	public int getRev_dislike_num() {
		return rev_dislike_num;
	}

	public void setRev_dislike_num(int rev_dislike_num) {
		this.rev_dislike_num = rev_dislike_num;
	}

	public int getReview_num() {
		return review_num;
	}

	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
}
