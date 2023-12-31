/*
package kr.mypage.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.mypage.dao.MyPageDAO;
import kr.mypage.vo.BookMarkVO;

public class BookMarkAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { //로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int board_num = Integer.parseInt(request.getParameter("mark_num"));
			MyPageDAO dao = MyPageDAO.getInstance();
			BookMarkVO bookmarkVO = new BookMarkVO();
			//bookmarkVO.setMark_num(mark_num);
			bookmarkVO.setMem_num(user_num);
			
			
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
				
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
*/
