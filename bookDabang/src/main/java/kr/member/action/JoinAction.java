package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class JoinAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = new MemberVO();
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		//서버단에서 입력값 확인
		
		//자바빈에 개인정보 담기 
		vo.setId(id);
		vo.setName(request.getParameter("name"));
		vo.setPasswd(passwd);
		vo.setSex(Integer.parseInt(request.getParameter("sex")));
		vo.setBirthday(request.getParameter("birthday"));
		vo.setPhone(request.getParameter("phone"));
		vo.setZipcode(request.getParameter("zipcode"));
		vo.setAddress1(request.getParameter("address1"));
		vo.setAddress2(request.getParameter("address2"));
		vo.setEmail(request.getParameter("email"));
		//입력		
		dao.insertUser(vo);
		
		//입력이 성공적이면 세션 제공
		MemberVO vo1 = new MemberVO();
		vo1 = dao.selectUser(id);
		
		HttpSession session = request.getSession();
		session.setAttribute("user_num", vo1.getMem_num());
		session.setAttribute("user_id", vo1.getId());
		session.setAttribute("user_auth", vo1.getAuth());
		session.setAttribute("user_state", vo1.getState());
		session.setAttribute("user_name", vo1.getName());
		session.setAttribute("user_photo", vo1.getPhoto());
	
		return "/WEB-INF/views/member/welcome.jsp";
	}
	
}
