package kr.request.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request1, HttpServletResponse response) throws Exception {
		HttpSession session = request1.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		RequestVO request = new RequestVO();
		request1.setCharacterEncoding("utf-8");
		request.setReq_title(request1.getParameter("req_title"));
		request.setReq_author(request1.getParameter("req_author"));
		request.setReq_publisher(request1.getParameter("req_publisher"));
		request.setReq_etc(request1.getParameter("req_etc"));
		request.setReq_ip(request1.getRemoteAddr());
		request.setMem_num(user_num);
		
		RequestDAO dao = RequestDAO.getInstance();
		dao.insertrequest(request);
		  
		return "/WEB-INF/views/request/write.jsp";
	}
	
}
