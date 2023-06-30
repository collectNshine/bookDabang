package kr.book.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.book.dao.BookDAO;
import kr.book.vo.ReviewDislikeVO;
import kr.book.vo.ReviewLikeVO;
import kr.controller.Action;

public class GetReviewLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int review_num = Integer.parseInt(request.getParameter("review_num"));
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		//로그인 여부 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		BookDAO dao = BookDAO.getInstance();
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("status", "noLike");
			mapAjax.put("count", dao.selectReviewLikeCount(review_num));
		}else { //로그인된 경우 - 객체 생성
			ReviewLikeVO reviewLike = dao.selectReviewLike(new ReviewLikeVO(review_num, user_num));
			if(reviewLike != null) { //로그인 회원이 해당 글에 좋아요 표시한 경우
				mapAjax.put("status", "yesLike");
				mapAjax.put("count", dao.selectReviewLikeCount(review_num));
			}else { //로그인 회원이 해당 글에 좋아요 표시한 경우
				mapAjax.put("status", "noLike");
				mapAjax.put("count", dao.selectReviewLikeCount(review_num));
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";

	}

}
