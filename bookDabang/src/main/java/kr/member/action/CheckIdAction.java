package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class CheckIdAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//받아온 파라미터 값 인코딩 처리하기 
		if(request.getMethod()
				  .toUpperCase().equals("GET")) {
			return "redirect:/member/loginForm.do";
		}
		String id = request.getParameter("id");
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = new MemberVO();
		vo = dao.selectUser(id);
		
		Map<String,String> mapAjax = new HashMap<String, String>();
		if(vo == null) {//아이디는 유니크하다.
			mapAjax.put("result","notFound");
		}else {//중복 아이디가 있다. 
			mapAjax.put("result","isDuplicated");
		}
		
		//
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax); 
		
		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
	
}
