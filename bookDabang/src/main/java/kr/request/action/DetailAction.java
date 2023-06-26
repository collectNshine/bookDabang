package kr.request.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestFavVO;
import kr.request.vo.RequestVO;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request1, HttpServletResponse response) throws Exception {
		HttpSession session = request1.getSession();
		/* String id = session.getAttribute("id"); */
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		int req_num = Integer.parseInt(request1.getParameter("req_num"));
		
		
		RequestDAO dao = RequestDAO.getInstance();
		RequestVO request = dao.getRequest(req_num);
		request.setMem_num(user_num);
		
		List<RequestVO> list = null;
		list = dao.getFavRequest(user_num, req_num);
		request1.setAttribute("list", list);
	
		/* request.setId(id); */
		request1.setAttribute("request", request);
		
		

		return "/WEB-INF/views/request/detail.jsp"; 
	}

}
