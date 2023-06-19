package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;

public class UpdateFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인되지 않은 경우
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//관리자로 로그인하지 않은 경우
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) { 
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//일치할 경우
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		BookDAO dao = BookDAO.getInstance();
		BookVO book = dao.getBook(bk_num);
		request.setAttribute("book", book);
		
		return "/WEB-INF/views/book/updateForm.jsp";

	}

}