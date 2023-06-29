package kr.order.vo;

public class OrderDetailVO {
	private int detail_num, bk_num, book_price, book_total, order_quantity, order_num;
	private String book_title;
	
	// Getters and Setters
	public int getDetail_num() { return detail_num; }
	public void setDetail_num(int detail_num) { this.detail_num = detail_num; }
	
	public int getBk_num() { return bk_num; }
	public void setBk_num(int bk_num) { this.bk_num = bk_num; }
	
	public int getBook_price() { return book_price; }
	public void setBook_price(int book_price) { this.book_price = book_price; }
	
	public int getBook_total() { return book_total; }
	public void setBook_total(int book_total) { this.book_total = book_total; }
	
	public int getOrder_quantity() { return order_quantity; }
	public void setOrder_quantity(int order_quantity) { this.order_quantity = order_quantity; }
	
	public int getOrder_num() { return order_num; }
	public void setOrder_num(int order_num) { this.order_num = order_num; }
	
	public String getBook_title() { return book_title; }
	public void setBook_title(String book_title) { this.book_title = book_title; }
}
