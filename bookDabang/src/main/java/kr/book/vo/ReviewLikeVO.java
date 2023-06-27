package kr.book.vo;

public class ReviewLikeVO {
	private int rev_like_num; 
	private int review_num;
	private int mem_num;
	
	//디폴트 생성자
	public ReviewLikeVO() {}
	
	//생성자 정의
	public ReviewLikeVO(int review_num, int mem_num) {
		this.review_num = review_num;
		this.mem_num = mem_num;
	}

	public int getRev_like_num() {
		return rev_like_num;
	}

	public void setRev_like_num(int rev_like_num) {
		this.rev_like_num = rev_like_num;
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
