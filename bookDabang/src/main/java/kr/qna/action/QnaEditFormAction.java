package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.dto.QnaDTO;

public class QnaEditFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {//로그인을 하지 않은 경우.
			return "redirect:/member/loginForm.do";
		}
		
		int qna_num =Integer.parseInt(request.getParameter("qna_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaDTO db_dto = new QnaDTO();
		db_dto = dao.selectDetail(qna_num);
		
		if(user_num == db_dto.getMem_num() || user_auth == 9) {
			QnaDTO dto = new QnaDTO();
			dto = dao.selectDetail(qna_num);
			
			request.setAttribute("dto", dto);
			return "/WEB-INF/views/qna/qnaEdit.jsp";
		}
		
		return"redirect:/qna/qnaList.do";
	}

}
