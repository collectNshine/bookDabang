package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.util.EmailSender;
import kr.util.RanGenerator;

public class SendAuthNumAction implements Action{//회원가입시 이메일 인증하기 

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String email = request.getParameter("email");
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if(email != null) {
			//이메일을 전송한다. 
			String rannum = RanGenerator.NumGenerator();
			EmailSender.Send(rannum,email);
			mapAjax.put("rannum",rannum);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax); 
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
