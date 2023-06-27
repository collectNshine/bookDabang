package kr.cart.vo; 

import java.sql.Date;

import kr.book.vo.BookVO;
import kr.member.vo.MemberVO;

public class CartVO {
	private int cart_num, bk_num, order_quantity, mem_num, sub_total;
	private Date cart_date;
	
	private MemberVO memberVO;
	private BookVO bookVO;

	public int getCart_num() { return cart_num; }
	public void setCart_num(int cart_num) { this.cart_num = cart_num; }

	public int getBk_num() { return bk_num; }
	public void setBk_num(int bk_num) { this.bk_num = bk_num; }

	public int getOrder_quantity() { return order_quantity; }
	public void setOrder_quantity(int order_quantity) { this.order_quantity = order_quantity; }

	public int getMem_num() { return mem_num; }
	public void setMem_num(int mem_num) { this.mem_num = mem_num; }

	public int getSub_total() { return sub_total; }
	public void setSub_total(int sub_total) { this.sub_total = sub_total; }

	public Date getCart_date() { return cart_date; }
	public void setCart_date(Date cart_date) { this.cart_date = cart_date; }
	
	public BookVO getBookVO() { return bookVO; }
	public void setBookVO(BookVO bookVO) { this.bookVO = bookVO; }
	
	public MemberVO getMemberVO() { return memberVO; }
	public void setMemberVO(MemberVO memberVO) { this.memberVO = memberVO; }
}
