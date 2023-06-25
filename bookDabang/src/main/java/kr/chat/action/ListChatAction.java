package kr.chat.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatMessageVO;
import kr.controller.Action;

public class ListChatAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		request.setCharacterEncoding("UTF-8");
		int chat_num = Integer.parseInt(request.getParameter("chat_num"));
		
		ChatDAO dao = ChatDAO.getInstance();
		//로그인한 회원이 채팅방에 회원으로 등록되어 있는지 검사
		int auth = dao.selectMem_numIntoChat(chat_num, user_num);
		
		//등록되었지 않으면 등록
		if(auth > 1 || auth < 1) { dao.insertChatInto(chat_num, user_num); }
		//등록이 되어 있으면 해당 채팅방 번호 채팅 메세지를 읽어옴
		
		List<ChatMessageVO> list = null;
		int count = dao.getChatContentCount(chat_num);
		if(count > 0) { list = dao.getListChatContent(chat_num); }
		else { list = Collections.emptyList(); }
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);
		
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
				
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
