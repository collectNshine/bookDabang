package kr.order.vo; 

import java.sql.Date;

public class OrderVO {
	private int order_num;
	private String book_title;
	private int order_total;
	private int payment;
	private int status;
	private String receive_name;
	private String receive_post;
	private String receive_address1;
	private String receive_address2;
	private String receive_phone;
	private String email;
	private String notice;
	private Date order_date;
	private Date modify_date;
	private int mem_num;
	private String id;
	
	// Getters and Setters
	public int getOrder_num() { return order_num; }
	public void setOrder_num(int order_num) { this.order_num = order_num; }
	
	public String getBook_title() { return book_title; }
	public void setBook_title(String book_title) { this.book_title = book_title; }
	
	public int getOrder_total() { return order_total; }
	public void setOrder_total(int order_total) { this.order_total = order_total; }
	
	public int getPayment() { return payment; }
	public void setPayment(int payment) { this.payment = payment; }
	
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	
	public String getReceive_name() { return receive_name; }
	public void setReceive_name(String receive_name) { this.receive_name = receive_name; }
	
	public String getReceive_post() { return receive_post; }
	public void setReceive_post(String receive_post) { this.receive_post = receive_post; }
	
	public String getReceive_address1() { return receive_address1; }
	public void setReceive_address1(String receive_address1) { this.receive_address1 = receive_address1; }
	
	public String getReceive_address2() { return receive_address2; }
	public void setReceive_address2(String receive_address2) { this.receive_address2 = receive_address2; }
	
	public String getReceive_phone() { return receive_phone; }
	public void setReceive_phone(String receive_phone) { this.receive_phone = receive_phone; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getNotice() { return notice; }
	public void setNotice(String notice) { this.notice = notice; }
	
	public Date getOrder_date() { return order_date; }
	public void setOrder_date(Date order_date) { this.order_date = order_date; }
	
	public Date getModify_date() { return modify_date; }
	public void setModify_date(Date modify_date) { this.modify_date = modify_date; }
	
	public int getMem_num() { return mem_num; }
	public void setMem_num(int mem_num) { this.mem_num = mem_num; }
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
}
