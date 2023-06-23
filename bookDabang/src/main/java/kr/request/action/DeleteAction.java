package kr.request.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request1, HttpServletResponse response) throws Exception {
		HttpSession session = request1.getSession();
		Integer user_num =(Integer) session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		int req_num = Integer.parseInt(request1.getParameter("req_num"));
		
		request1.setCharacterEncoding("utf-8");
		RequestDAO dao = RequestDAO.getInstance();
		RequestVO db_request = dao.getRequest(req_num);
		
		if(user_num != db_request.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		dao.deleteRequest(req_num);
		
		
		return "redirect:/request/list.do";
	}

}
