package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.dao.MemberDAO;


public class CheckNickAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//받아온 파라미터 값 인코딩 처리하기 
		String nickname = request.getParameter("nickname");
		MemberDAO dao = MemberDAO.getInstance();
		boolean check = false;
		check = dao.checkNick(nickname);
		
		
		Map<String,String> mapAjax = new HashMap<String, String>();
		if(check) {//닉네임 중복값이 있다. 
			mapAjax.put("result","isDuplicated");
		}
	
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax); 
		
		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";

	}

}
