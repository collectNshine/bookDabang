package kr.request.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestVO;
import kr.util.PageUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			user_num = 0;
			return "redirect:/member/loginForm.do";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		RequestDAO dao = RequestDAO.getInstance();
		int count = dao.getRequestCount(keyfield,keyword);
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"list.do");
		
		List<RequestVO> list = null;
		
		if(count > 0 ) { 
			/* if(user_num == null) user_num = 0; */
			list = dao.getListRequest(page.getStartRow(),page.getEndRow(),keyfield,keyword,user_num); 
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
	
		
		
		
		return "/WEB-INF/views/request/list.jsp";
	}

}
