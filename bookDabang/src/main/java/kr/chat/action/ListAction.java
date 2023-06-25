package kr.chat.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatMessageVO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class ListAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		ChatDAO dao = ChatDAO.getInstance();
		int count = dao.getChatCount(keyfield, keyword);
		
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 5, 10, "list.do");
		
		List<ChatVO> list = null;
		if(count > 0) { list = dao.getListChat(page.getStartRow(), page.getEndRow(), keyfield, keyword); }
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/chat/list.jsp";
	}
}
