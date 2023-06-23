package kr.notice.vo;

import java.sql.Date;

public class NoticeVO {
	private int noti_num;//게시글 번호
	private Date noti_date;//작성일
	private int noti_category;//카테고리
	private String noti_title;//게시글 제목
	private String noti_content;//게시글 내용
	private Date latest_ed_date;//작성 또는 최근 수정일
	private int mem_num;//작성자 번호
	
	/////////////////////////////////
	private String noti_category_name;
	private int noti_category_count;
	/////////////////////////////////
	
	public String getNoti_category_name() {
		return noti_category_name;
	}
	public void setNoti_category_name(String noti_category_name) {
		this.noti_category_name = noti_category_name;
	}
	public int getNoti_category_count() {
		return noti_category_count;
	}
	public void setNoti_category_count(int noti_category_count) {
		this.noti_category_count = noti_category_count;
	}
	
	public int getNoti_num() {
		return noti_num;
	}
	public void setNoti_num(int noti_num) {
		this.noti_num = noti_num;
	}
	public Date getNoti_date() {
		return noti_date;
	}
	public void setNoti_date(Date noti_date) {
		this.noti_date = noti_date;
	}

	public int getNoti_category() {
		return noti_category;
	}
	public void setNoti_category(int noti_category) {
		this.noti_category = noti_category;
	}
	public String getNoti_title() {
		return noti_title;
	}
	public void setNoti_title(String noti_title) {
		this.noti_title = noti_title;
	}
	public String getNoti_content() {
		return noti_content;
	}
	public void setNoti_content(String noti_content) {
		this.noti_content = noti_content;
	}
	public Date getLatest_ed_date() {
		return latest_ed_date;
	}
	public void setLatest_ed_date(Date latest_ed_date) {
		this.latest_ed_date = latest_ed_date;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	
	
}
