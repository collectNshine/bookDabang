package kr.request.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestVO;

public class UpdateStateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDAO dao = RequestDAO.getInstance();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Map<String,String> mapAjax = new HashMap<String,String>();
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else if(user_num!=null) {
			String[] reqstate = request.getParameter("reqstate").split(","); 
			dao.updateState(reqstate);
			System.out.println(reqstate);
			mapAjax.put("result", "success");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
		
		
		
		
	
	}

}
