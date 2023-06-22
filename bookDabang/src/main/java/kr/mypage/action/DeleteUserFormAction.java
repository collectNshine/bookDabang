package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class DeleteUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response)throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인되지 않은 경우
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 되어있는 경우
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/deleteUserForm.jsp";
	}
}
