package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class ShowMyPwAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//난수 비밀번호를 생성.
		//이름과 이메일 정보를 받음. 
		//이메일로 보냄. 
		//생성된 난수 비밀번호로 변경.
		return "/WEB-INF/views/member/showMyPw.jsp";
	}

}
