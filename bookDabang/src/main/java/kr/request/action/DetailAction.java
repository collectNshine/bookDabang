package kr.request.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestVO;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request1, HttpServletResponse response) throws Exception {
		int req_num = Integer.parseInt(request1.getParameter("req_num"));
		RequestDAO dao = RequestDAO.getInstance();
		
		
		RequestVO request = dao.getRequest(req_num);
		
		request1.setAttribute("request", request);
		return "/WEB-INF/views/request/detail.jsp";
	}

}
