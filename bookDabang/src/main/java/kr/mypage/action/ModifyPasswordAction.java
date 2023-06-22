package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;

public class ModifyPasswordAction implements Action{

	@Override
	public String execute(HttpServletRequest request, 
						  HttpServletResponse response) 
								  throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인이 되지 않은 경우
		if(user_num == null) { 
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String id = request.getParameter("id");
		//현재 비밀번호
		String origin_passwd = request.getParameter("origin_passwd");
		//새 비밀번호
		String passwd = request.getParameter("passwd");
		
		//현재 로그인되어있는 아이디
		String user_id = (String)session.getAttribute("user_id");
		
		MyPageDAO dao = MyPageDAO.getInstance();
		//입력한 아이디를 통해서 회원 정보를 반환
		MemberVO vo = dao.checkMember(id);
		boolean check = false;
		
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하는지 체크
		if(vo!=null && id.equals(user_id)) {
			//비밀번호 일치 여부 체크
			check = vo.isCheckedPassword(origin_passwd);
		}
		
		if(check) {//인증 성공
			//비밀번호 변경
			dao.updatePassword(passwd, user_num);
		}
		
		//UI 처리를 위해 check값 저장
		request.setAttribute("check", check);
		
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/modifyPassword.jsp";
	}

}
