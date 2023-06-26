package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;

public class QnaDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		QnaDAO dao = QnaDAO.getInstance();
		String[] num = request.getParameterValues("qna_num");
		dao.deleteQna(num);
		
		return "redirect:/qna/qnaList.do";
	}

}
