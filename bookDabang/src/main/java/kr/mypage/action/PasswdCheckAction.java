package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.Encrypt;

public class PasswdCheckAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			//로그인 여부 체크
			HttpSession session = request.getSession();
			Integer user_num = (Integer)session.getAttribute("user_num");
			if(user_num == null) {
				return "redirect:/member/loginForm.do";
			}

			//로그인 되어 있는 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			String id = (String)session.getAttribute("user_id");
			//전송된 데이터 반환
			String passwd = request.getParameter("passwd");
			
			boolean check = false;
			
			MemberDAO dao = MemberDAO.getInstance();
			MemberVO vo = dao.selectUser(id);
			check = vo.isCheckedPassword(passwd);

			if(check) {
				return "redirect:/mypage/modifyUserForm.do";
			}else {
				//자바스크립트로 띄움
				request.setAttribute("notice_msg", "비밀번호가 불일치합니다.");
				request.setAttribute("notice_url", "PasswdCheckForm.do");
				
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
		}
	}