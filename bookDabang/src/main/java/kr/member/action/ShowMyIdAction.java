package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;


public class ShowMyIdAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		if(request.getMethod()
				  .toUpperCase().equals("GET")) {
			return "redirect:/member/loginForm.do";
		}
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		MemberDAO dao = MemberDAO.getInstance();
		String id = dao.idSearch(name, email);
		
		request.setAttribute("id", id);
		
		return "/WEB-INF/views/member/showMyId.jsp";
	}

}
