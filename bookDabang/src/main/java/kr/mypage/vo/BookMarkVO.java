package kr.mypage.vo;

import kr.book.vo.BookVO;

public class BookMarkVO {
	private int mark_num; //즐겨찾기 번호
	private int bk_num; //도서 번호
	private int mem_num; //회원 번호
	
	private BookVO bookVO;

	public int getMark_num() {
		return mark_num;
	}

	public void setMark_num(int mark_num) {
		this.mark_num = mark_num;
	}

	public int getBk_num() {
		return bk_num;
	}

	public void setBk_num(int bk_num) {
		this.bk_num = bk_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public BookVO getBookVO() {
		return bookVO;
	}

	public void setBookVO(BookVO bookVO) {
		this.bookVO = bookVO;
	}
	

}
