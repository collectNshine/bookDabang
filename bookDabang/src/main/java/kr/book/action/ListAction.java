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
		if(category==null) category="";
		
		BookDAO dao = BookDAO.getInstance();
		int count = dao.getItemCount(keyfield, keyword,category);
		
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"list.do","&category="+category);
		
		List<BookVO> list = null;
		if(count > 0) {
			list = dao.getBookList(page.getStartRow(), page.getEndRow(), keyfield, keyword,category);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("category", category);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/book/list.jsp";
	}

}
