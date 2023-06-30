package kr.order.vo;

public class OrderDetailVO {
	private int detail_num, bk_num, book_price, book_total, order_quantity, order_num;
	private String book_title, thumbnail, book_author, book_publisher;
	
	// Getters and Setters
	public int getDetail_num() { return detail_num; }
	public void setDetail_num(int detail_num) { this.detail_num = detail_num; }
	
	public int getBk_num() { return bk_num; }
	public void setBk_num(int bk_num) { this.bk_num = bk_num; }
	
	public int getBook_price() { return book_price; }
	public void setBook_price(int book_price) { this.book_price = book_price; }
	
	public int getBook_total() { return book_total; }
	public void setBook_total(int book_total) { this.book_total = book_total; }
	
	public String getBook_author() { return book_author; }
	public void setBook_author(String book_author) { this.book_author = book_author; }
	
	public String getBook_publisher() { return book_publisher; }
	public void setBook_publisher(String book_publisher) { this.book_publisher = book_publisher; }
	
	public int getOrder_quantity() { return order_quantity; }
	public void setOrder_quantity(int order_quantity) { this.order_quantity = order_quantity; }
	
	public int getOrder_num() { return order_num; }
	public void setOrder_num(int order_num) { this.order_num = order_num; }
	
	public String getBook_title() { return book_title; }
	public void setBook_title(String book_title) { this.book_title = book_title; }
	
	public String getThumbnail() { return thumbnail; }
	public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
}
