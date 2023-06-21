package kr.mypage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;

public class MyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 되어 있지 않은 경우
		if(user_num == null) {
			return "redirefct:/member/loginForm.do";
		}
		
		//로그인 되어 있는 경우
		MyPageDAO dao = MyPageDAO.getInstance();
		//회원 정보
		MemberVO vo = dao.getMember(user_num);
		
		//PostDAO boardDao = PostDAO.getInstance();
		//게시판 글
		//List<PostVO> postList = PostDAO.getPostList(1,5, );	
		
		request.setAttribute("member", vo);
			
		
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/myPage.jsp";
	}

}
