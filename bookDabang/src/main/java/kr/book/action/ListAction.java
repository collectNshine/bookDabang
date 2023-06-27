package kr.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		
		BookDAO dao = BookDAO.getInstance();
		int count = dao.getItemCount(keyfield, keyword);
		
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"list.do");
		
		List<BookVO> list = null;
		List<BookVO> list2 = null;
		if(count > 0) {
			list = dao.getBookList(page.getStartRow(), page.getEndRow(), keyfield, keyword);
			list2 = dao.getCategory(page.getStartRow(), page.getEndRow(), category);
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("list2", list2);
		request.setAttribute("page", page.getPage());
	
		
		return "/WEB-INF/views/book/list.jsp";
	}

}
