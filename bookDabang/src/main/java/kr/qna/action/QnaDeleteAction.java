package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;

public class QnaDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {//로그인을 하지 않은 경우.
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 9) {//관리자 계정이 아닌 경우
			return "redirect:/notice/noticeList.do";
		}
		
		QnaDAO dao = QnaDAO.getInstance();
		String[] num = request.getParameterValues("qna_num");
		dao.deleteQna(num);
		
		return "redirect:/qna/qnaList.do";
	}

}
