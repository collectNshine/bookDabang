package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;

public class WriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		BookDAO dao = BookDAO.getInstance();
		BookVO book = dao.getBook(bk_num);
		
		request.setAttribute("book", book);
		
		//로그인 된 경우
		//JSP 경로 반환
		return "/WEB-INF/views/post/writeForm.jsp";
	}

}
