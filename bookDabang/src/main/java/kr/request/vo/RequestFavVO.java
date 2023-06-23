package kr.request.vo;

public class RequestFavVO {
	private int req_fav_num;
	private int req_num;
	private int mem_num;
	
	public RequestFavVO() {}
	public RequestFavVO(int req_num, int mem_num) {
		this.req_num = req_num;
		this.mem_num = mem_num;
	}
	
	
	public int getReq_fav_num() {
		return req_fav_num;
	}
	public void setReq_fav_num(int req_fav_num) {
		this.req_fav_num = req_fav_num;
	}
	public int getReq_num() {
		return req_num;
	}
	public void setReq_num(int req_num) {
		this.req_num = req_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	
}
