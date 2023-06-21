package kr.post.vo;

import java.sql.Date;

public class PostVO {
	private int post_num;
	private String post_title;
	private String post_content;
	private Date post_date;
	private Date post_modifydate;
	private String post_photo;
	private String post_ip;
	private int mem_num;
	private int bk_num;
	
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
	public Date getPost_date() {
		return post_date;
	}
	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
	public Date getPost_modifydate() {
		return post_modifydate;
	}
	public void setPost_modifydate(Date post_modifydate) {
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
	
}
