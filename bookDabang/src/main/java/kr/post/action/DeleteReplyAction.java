package kr.post.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostReplyVO;

public class DeleteReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		PostDAO dao = PostDAO.getInstance();
		PostReplyVO db_reply = dao.getReplyPost(re_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인되지 않은 경우
			mapAjax.put("result", "logout");
		} else if(user_num != null && user_num == db_reply.getMem_num()){
			//로그인이 되어 있고, 로그인한 회원번호와 작성자 회원번호가 일치
			dao.deleteReplyPost(re_num);
			mapAjax.put("result", "success");
		} else {
			//로그인이 되어 있고, 로그인한 회원번호와 작성자 회원번호가 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
