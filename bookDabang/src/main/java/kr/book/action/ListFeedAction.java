package kr.book.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.ReviewVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class ListFeedAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		//request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		//int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do"; 
		}
		//로그인 된 경우
		/*---한줄기록 피드 시작---*/
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BookDAO dao = BookDAO.getInstance();
		int count = dao.getReCount(keyfield, keyword);
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 16, 10, "listFeed.do");
		
		List<ReviewVO> list = null;
		if(count > 0) {
			list = dao.getReList(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		/*---한줄기록 피드 끝---*/
		
		//JSP 경로 반환
		return "/WEB-INF/views/book/listFeed.jsp";
	}

}
