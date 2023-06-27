package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.dto.QnaDTO;

public class QnaDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		int qna_num =Integer.parseInt(request.getParameter("qna_num"));
		System.out.println(qna_num);
		if(user_num == null) {//로그인을 하지 않은 경우.
			return "redirect:/member/loginForm.do";
		}

		QnaDAO dao = QnaDAO.getInstance();
		QnaDTO dto = new QnaDTO();
		dto = dao.selectDetail(qna_num);
	
		
		request.setAttribute("dto", dto);
		return "/WEB-INF/views/qna/qnaDetail.jsp";
		
	}

}
