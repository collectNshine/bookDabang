package kr.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.dto.QnaDTO;
import kr.util.PageUtil_updated;

public class QnaListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		QnaDAO dao = QnaDAO.getInstance();
		int count = dao.countQna();
		
		List<QnaDTO> list = null;
		PageUtil_updated page = new PageUtil_updated(Integer.parseInt(pageNum), count,15, 5, "qnaList.do");
		
		if(count > 0) {
		list = dao.selectList(page.getStartRow(),page.getEndRow());
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());

		return "/WEB-INF/views/qna/qnaList.jsp";
	}
	
}
