package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		BookDAO dao = BookDAO.getInstance();	
		BookVO book = dao.getBook(bk_num);
		
		request.setAttribute("book", book);

		return "/WEB-INF/views/book/detail.jsp";
	}

}
