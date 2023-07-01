package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.util.EmailSender;
import kr.util.RanGenerator;

public class CheckAuthNumAction implements Action { //아이디, 비밀번호 찾기시 이메일 인증하기
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		if(request.getMethod()
				  .toUpperCase().equals("GET")) {
			return "redirect:/member/loginForm.do";
		}
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		boolean check = false;
		
		MemberDAO dao = MemberDAO.getInstance();
		check = dao.chackNameEmail(name, email); //true or false 반환
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		if(check == true) {//DB에 이름과 이메일이 일치하는 결과값이 있는 경우
			
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
