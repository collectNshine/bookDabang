package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.dto.QnaDTO;

public class QnaWriteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");//작성자
		
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		String qna_title = request.getParameter("qna_title");
		String qna_content = request.getParameter("qna_content");
		
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaDTO dto = new QnaDTO();
		dto.setQna_num(qna_num);
		dto.setQna_title(qna_title);
		dto.setQna_content(qna_content);
		dto.setMem_num(user_num);
		
		dao.replyQna(dto);
		
		return "redirect:/qna/qnaList.do";
	}

}
