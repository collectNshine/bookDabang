package kr.post.vo;

import java.sql.Date;

public class PostReportVO {
	private int repo_num; //신고 번호
	private Date repo_date; //신고 날짜
	private String repo_ip; //신고자 ip
	private String repo_content; //신고내용
	private int repo_category; //신고범위
	private int post_num; //서평 번호
	private int mem_num; //신고자 회원번호
	
	public int getRepo_num() {
		return repo_num;
	}
	public void setRepo_num(int repo_num) {
		this.repo_num = repo_num;
	}
	public Date getRepo_date() {
		return repo_date;
	}
	public void setRepo_date(Date repo_date) {
		this.repo_date = repo_date;
	}
	public String getRepo_ip() {
		return repo_ip;
	}
	public void setRepo_ip(String repo_ip) {
		this.repo_ip = repo_ip;
	}
	public String getRepo_content() {
		return repo_content;
	}
	public void setRepo_content(String repo_content) {
		this.repo_content = repo_content;
	}
	public int getRepo_category() {
		return repo_category;
	}
	public void setRepo_category(int repo_category) {
		this.repo_category = repo_category;
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
