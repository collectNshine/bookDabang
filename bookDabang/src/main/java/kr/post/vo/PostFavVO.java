package kr.post.vo;

public class PostFavVO {
	private int post_fav_num; //좋아요 번호
	private int post_num; //서평 번호
	private int mem_num; //회원 번호
	
	//디폴트 생성자
	public PostFavVO() {}
	
	//생성자 정의
	public PostFavVO(int post_num, int mem_num) {
		this.post_num = post_num;
		this.mem_num = mem_num;
	}

	public int getPost_fav_num() {
		return post_fav_num;
	}

	public void setPost_fav_num(int post_fav_num) {
		this.post_fav_num = post_fav_num;
	}

	public int getPost_num() {
		return post_num;
	}

	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
}
