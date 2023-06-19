package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("utf-8");

		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = new MemberVO();
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		boolean check = false;

		vo = dao.selectUser(id);

		if(vo!=null) {
			if(vo.getPasswd()!=null) {
				check = vo.isCheckedPassword(passwd);
			}
			if(vo.getState()==1) {//휴면계정은 전용 페이지로 이동한다.
				//휴면계정을 다시 활성계정으로 변경한다.
				return "redirect:/member/sleepMember.do";
			}
		}

		if(check && vo.getState()==0) {//로그인 성공 & 활성계정
			//DB에서 받아온 정보를 세션에 넣는다.
			HttpSession session = request.getSession();
			session.setAttribute("user_num", vo.getMem_num());
			session.setAttribute("user_id", vo.getId());
			session.setAttribute("user_auth", vo.getAuth());
			session.setAttribute("user_state", vo.getState());
			session.setAttribute("user_name", vo.getName());
			session.setAttribute("user_photo", vo.getPhoto());

			return "redirect:/main/main.do";
		}
		//삭제 계정은 로그인폼으로 이동한다.
		return "/WEB-INF/views/member/loginForm.jsp";
	}

}