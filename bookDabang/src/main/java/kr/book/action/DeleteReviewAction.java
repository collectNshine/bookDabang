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

public class DeleteReviewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		BookDAO dao = BookDAO.getInstance();
		ReviewVO db_review = dao.getReviewDetail(review_num);
		
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { 
			//로그인 X
			mapAjax.put("result", "logout");
		}else if(user_num != null && user_num == db_review.getMem_num()){
			//로그인 O, 로그인한 회원번호 = 작성자 회원번호 (일치)
			dao.deleteReview(review_num);
			mapAjax.put("result", "success");
		}else { 
			//로그인 O, 로그인한 회원번호 != 작성자 회원번호 (불일치)
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
