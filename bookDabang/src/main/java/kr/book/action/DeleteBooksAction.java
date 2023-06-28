package kr.book.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.book.dao.BookDAO;
import kr.book.vo.ReviewVO;
import kr.controller.Action;

public class DeleteBooksAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> mapAjax = new HashMap<String,String>();
		
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { 
			mapAjax.put("result", "logout");
		}else{
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int bk_num = Integer.parseInt(request.getParameter("bk_num"));
			ArrayList<Integer> checkArr = new ArrayList<>();
			BookDAO dao = BookDAO.getInstance();
			dao.deleteBook(bk_num);
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";

	}

}
