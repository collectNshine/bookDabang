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
		
		if(request.getMethod()
				  .toUpperCase().equals("GET")) {
			return "redirect:/member/loginForm.do";
		}
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = new MemberVO();
		MemberVO db_vo = new MemberVO();
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		boolean check = false;
		boolean check2 = false;
		
		vo = dao.selectUser(id);

		if(vo!=null) {
			if(vo.getPasswd()!=null) {
				check = vo.isCheckedPassword(passwd);
			}
			if(vo.getState()==1) {//휴면계정
				//휴면계정을 다시 활성계정으로 변경한다.
				db_vo = dao.selectSleepUser(id);
				check2 = db_vo.isCheckedPassword(passwd);
			}
		}
		
		if(check && vo.getState()==0) {//(활성계정 로그인 성공 & 활성계정)
			
			dao.updateLatestLoginDate(id);
			//DB에서 받아온 정보를 세션에 넣는다.
			HttpSession session = request.getSession();
			session.setAttribute("user_num", vo.getMem_num());
			session.setAttribute("user_id", vo.getId());
			session.setAttribute("user_auth", vo.getAuth());
			session.setAttribute("user_state", vo.getState());
			session.setAttribute("user_name", vo.getName());
			session.setAttribute("user_nickname", vo.getNickname());
			session.setAttribute("user_photo", vo.getPhoto());
			
			return "redirect:/main/main.do";
		}
		
		if(check2 && db_vo.getState()==1) {
			
			//name을 받아서 DB에서 삭제
			dao.deleteSleepMember(db_vo.getName());
			//DB에서 받아온 정보를 세션에 넣는다.
			HttpSession session = request.getSession();
			session.setAttribute("user_num", db_vo.getMem_num());
			session.setAttribute("user_id", db_vo.getId());
			session.setAttribute("user_auth",db_vo.getAuth());
			session.setAttribute("user_state",db_vo.getState());
			session.setAttribute("user_name",db_vo.getName());
			session.setAttribute("user_nickname", db_vo.getNickname());
			session.setAttribute("user_photo",db_vo.getPhoto());
			session.setAttribute("sleep_date",db_vo.getSsleep_date());
			
			return "/WEB-INF/views/member/sleepMember.jsp";
		}
		
		//삭제 계정은 로그인폼으로 이동한다.
		return "/WEB-INF/views/member/loginForm.jsp";
	}

}