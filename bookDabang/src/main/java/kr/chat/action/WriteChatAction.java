package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatMessageVO;
import kr.controller.Action;

public class WriteChatAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) { mapAjax.put("result", "logout"); }
		else {
			request.setCharacterEncoding("UTF-8");
			
			ChatMessageVO message = new ChatMessageVO();
			message.setMem_num(user_num);
			message.setChat_num(Integer.parseInt(request.getParameter("chat_num")));
			message.setChat_content(request.getParameter("chat_content"));
			
			ChatDAO dao = ChatDAO.getInstance();
			dao.insertChatContent(message);
			
			mapAjax.put("result", "success");
		}
		
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
