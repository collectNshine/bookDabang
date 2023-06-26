package kr.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.dto.QnaDTO;

public class QnaListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		QnaDAO dao = QnaDAO.getInstance();
		List<QnaDTO> list = null;
		list = dao.selectList();
		
		request.setAttribute("list", list);
		
		return "/WEB-INF/views/qna/qnaList.jsp";
	}
	
}
