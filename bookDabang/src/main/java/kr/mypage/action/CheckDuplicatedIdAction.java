package kr.mypage.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;

public class CheckDuplicatedIdAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
						  HttpServletResponse response) 
								  throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String id = request.getParameter("id");
		
		MyPageDAO dao = MyPageDAO.getInstance();
		MemberVO mypage = dao.checkMember(id);
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		if(mypage == null) { //아이디 미중복 (=rs.next()에 행이 없는 것)
			mapAjax.put("result", "idNotFound");
		}else { //아이디 중복
			mapAjax.put("result", "idDuplicated");
		}
		
		//잭슨 라이브러리 사용을 위해 객체 생성
		ObjectMapper mapper = new ObjectMapper();
		//key와 value의 쌍으로 되어 있는 Map 데이터를 JSON 형식의 문자열 데이터로 변환 후 반환
		String ajaxData = mapper.writeValueAsString(mapAjax);
		//request영역에 저장 (ajax_view.jsp에서는 읽어오기만 하면 됨)
		request.setAttribute("ajaxData",ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
