package kr.notice.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeReplyVO;

public class InsertReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");
		if(user_num == null) {
			mapAjax.put("result", "noLogin");
		}else {
			request.setCharacterEncoding("utf-8");
			
			NoticeReplyVO vo = new NoticeReplyVO();
			vo.setRe_content(request.getParameter("re_content"));
			vo.setRe_ip(request.getRemoteAddr());
			vo.setNoti_num(Integer.parseInt(request.getParameter("noti_num")));
			vo.setMem_num(user_num);
			
			NoticeDAO dao = NoticeDAO.getInstance();
			dao.insertReply(vo);
			
			mapAjax.put("result","success");
		}
		
		//JSON 데이터 생성
		 ObjectMapper map = new ObjectMapper();
		 String ajaxData = map.writeValueAsString(mapAjax);
		 
		 request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
	
}
