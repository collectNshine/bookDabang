package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class PasswdCheckAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
				/*
				//로그인 여부 체크
				HttpSession session = request.getSession();
				Integer user_num = (Integer)session.getAttribute("user_num");
				
				//로그인 되지 않은 경우
				if(user_num == null) {
					return "redirect:/member/loginForm.do";
				}
		`		*/

				//로그인 되어 있는 경우
				//전송된 데이터 인코딩 처리
				request.setCharacterEncoding("utf-8");
				
				//전송된 데이터 반환
				String passwd = request.getParameter("passwd");
				
				MemberDAO dao = MemberDAO.getInstance();
				MemberVO vo = dao.selectUser(passwd);
				//String id = request.getParameter("id");
				boolean check = false;


				if(vo!=null) {
					if(vo.getPasswd()!=null) {
						check = vo.isCheckedPassword(passwd);
					}
				}
					
				if(check) { //비밀번호 인증 성공
					//DB에서 받아온 정보를 세션에 넣는다.
					HttpSession session = request.getSession();
					session.setAttribute("user_num", vo.getMem_num());
					session.setAttribute("user_name", vo.getName());
					session.setAttribute("user_phone", vo.getPhone());
					session.setAttribute("user_email", vo.getEmail());
					session.setAttribute("user_zipcode", vo.getZipcode());
					session.setAttribute("user_address1", vo.getAddress1());
					session.setAttribute("user_address2", vo.getAddress2());
					session.setAttribute("user_photo", vo.getPhoto());
					
					//인증 성공시 호출
					return "redirect:/mypage/modifyUserForm.do";
				}
				//인증 실패시 호출
				return "/WEB-INF/views/mypage/modifyUserForm.jsp";
		}
	}


