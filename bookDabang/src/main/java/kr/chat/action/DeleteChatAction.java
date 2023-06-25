package kr.chat.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;

public class DeleteChatAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		int chat_num = Integer.parseInt(request.getParameter("chat_num"));

		ChatDAO dao = ChatDAO.getInstance();
		ChatVO chat = dao.getChatInfo(chat_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { mapAjax.put("result", "logout"); }
		else if(user_num != null && user_num == chat.getMem_num()) {
			dao.deleteChat(chat_num);
			mapAjax.put("result", "success");
		} else { mapAjax.put("result", "wrongAccess"); }
		
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";	
	}
}
