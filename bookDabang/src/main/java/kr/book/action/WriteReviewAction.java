package kr.book.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.book.dao.BookDAO;
import kr.book.vo.ReviewVO;
import kr.controller.Action;

public class WriteReviewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { //로그인 되어 있는 경우
			request.setCharacterEncoding("utf-8"); //전송된 데이터 인코딩 처리
			
			ReviewVO review = new ReviewVO();
			review.setMem_num(user_num); //회원 번호 (댓글 작성자)
			review.setReview_content(request.getParameter("re_content"));
			review.setReview_ip(request.getRemoteAddr());
			review.setBk_num(Integer.parseInt(request.getParameter("bk_num")));
			
			BookDAO dao = BookDAO.getInstance();
			dao.insertReview(review);
			
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
