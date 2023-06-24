package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.util.EmailSender;
import kr.util.RanGenerator;

public class ShowMyPwAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		//이름과 이메일 정보를 받음.
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		//난수 비밀번호를 생성.
		String temp_passwd = RanGenerator.PwGenerator(10);
		 
		//이메일로 보냄. 
		EmailSender.Send(temp_passwd, email);
		
		//생성된 난수 비밀번호로 변경.
		MemberDAO dao = MemberDAO.getInstance();
		dao.passwdChange(name, email, temp_passwd);
		
		return "/WEB-INF/views/member/showMyPw.jsp";
	}

}
