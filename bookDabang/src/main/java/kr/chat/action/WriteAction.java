package kr.chat.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.chat.dao.ChatDAO;
import kr.chat.vo.ChatMessageVO;
import kr.chat.vo.ChatVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class WriteAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { mapAjax.put("result", "logout"); }
		else {
			request.setCharacterEncoding("UTF-8");
			
			MultipartRequest multi = FileUtil.createFile(request);
			
			ChatVO chat = new ChatVO();
			chat.setMem_num(user_num);
			chat.setChat_title(multi.getParameter("chat_title"));
			chat.setChat_img(multi.getFilesystemName("chat_img"));
			
			ChatDAO dao = ChatDAO.getInstance();
			dao.insertChat(chat);
			
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
