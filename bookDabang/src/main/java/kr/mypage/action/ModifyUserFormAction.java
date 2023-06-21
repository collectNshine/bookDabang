package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;

public class ModifyUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response)
								  throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 되지 않은 경우
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		MyPageDAO dao = MyPageDAO.getInstance();
		//MemberVO vo = dao.getMember(user_num);
		//request에 회원 정보 담기
		//request.setAttribute("member", vo);
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/modifyUserForm.jsp";
	}

}
