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

public class UpdateReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int re_num = Integer.parseInt(request.getParameter("re_num"));
			
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeReplyVO db_vo = dao.selectOneReply(re_num);
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(user_num == null) {
			mapAjax.put("result", "noLogin");
		}else if(user_num != null && user_num == db_vo.getMem_num()) { //현재 세션 사용자랑 DB에 저장된 작성자 정보랑 같으면 update를 수행한다. 
			NoticeReplyVO vo = new NoticeReplyVO();
			vo.setRe_content(request.getParameter("re_content"));
			vo.setRe_ip(request.getRemoteAddr());
			vo.setRe_num(re_num);
			
			dao.updateReply(vo);
			mapAjax.put("result", "success");
		}else {
			mapAjax.put("result", "wrongAccess");
		}
		//JSON 데이터 생성
			ObjectMapper mapper = new ObjectMapper();
			String ajaxData = 
			mapper.writeValueAsString(mapAjax);
				
			request.setAttribute("ajaxData", ajaxData);
			//JSP 경로 반환
			return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
