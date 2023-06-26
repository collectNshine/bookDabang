package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.dto.QnaDTO;

public class QnaDetailFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		int qna_num =Integer.parseInt(request.getParameter("qna_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaDTO dto = new QnaDTO();
		
		dto = dao.selectDetail(qna_num);

		request.setAttribute("dto", dto);
		
		return "/WEB-INF/views/qna/qnaDetail.jsp";
	}

}
