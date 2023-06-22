package kr.mypage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;
import kr.util.FileUtil;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response)throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 되지 않은 경우
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 되어 있는 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		//로그인한 아이디 가져오기
		String user_id = (String)session.getAttribute("user_id");
		
		MyPageDAO dao = MyPageDAO.getInstance();
		MemberVO db_member = dao.checkMember(id);
		boolean check = false;
		
		//<여부 체크>
		//1. 사용자가 입력한 아이디의 존재하는지
		//2. 로그인한 아이디 = 입력한 아이디 일치하는지
		//3. 입력한 이메일 = 저장된 이메일 일치하는지
		if(db_member!=null && id.equals(user_id)) {
			//비밀번호 일치 여부 체크
			check = db_member.isCheckedPassword(passwd);
		}
		
		if(check) { //여부 체크 인증 성공
			//회원 탈퇴
			dao.deleteMember(user_num);
			//프로필 사진 삭제
			FileUtil.removeFile(request, db_member.getPhoto());
			//로그아웃 처리
			session.invalidate();
		}
		
		request.setAttribute("check", check);
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/deleteUser.jsp";
	}

}
