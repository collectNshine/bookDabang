package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;

public class ModifyUserAction implements Action {

	@Override
	public String execute(HttpServletRequest request, 
						  HttpServletResponse response) 
								  throws Exception {
		///로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 되지 않은 경우
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//자바빈(VO)을 생성하고 전송된 데이터를 자바빈에 저장
		MemberVO vo = new MemberVO();
		vo.setMem_num(user_num);
		vo.setName(request.getParameter("name"));
		vo.setPhone(request.getParameter("phone"));
		vo.setEmail(request.getParameter("email"));
		vo.setZipcode(request.getParameter("zipcode"));
		vo.setAddress1(request.getParameter("address1"));
		vo.setAddress2(request.getParameter("address2"));
		
		MyPageDAO dao = MyPageDAO.getInstance();
		dao.updateMember(vo);
		
		return "/WEB-INF/views/member/modifyUser.jsp";
	}

}
