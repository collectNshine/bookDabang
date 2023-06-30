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

public class WriteReviewDislikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		//로그인 여부 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { //로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			//전송된 데이터 반환
			int review_num = Integer.parseInt(request.getParameter("review_num"));
			
			BookDAO dao = BookDAO.getInstance();
			ReviewDislikeVO dislikeVO = new ReviewDislikeVO();
			dislikeVO.setReview_num(review_num);
			dislikeVO.setMem_num(user_num);
			//별로에요 등록 여부 체크
			ReviewDislikeVO db_dislike = dao.selectReviewDislike(dislikeVO);
			if(db_dislike != null) { //별로에요 등록 되어있는 경우
				//별로에요 삭제 처리
				dao.deleteReviewDislike(db_dislike.getRev_dislike_num());
				mapAjax.put("result" , "success");
				mapAjax.put("status_dis" , "noDislike");
				mapAjax.put("count_dis" , dao.selectReviewDislikeCount(review_num));
			}else { //별로에요 등록 안 되어있는 경우
				//별로에요 등록 처리
				dao.insertReviewDislike(dislikeVO);
				mapAjax.put("result" , "success");
				mapAjax.put("status_dis" , "yesDislike");
				mapAjax.put("count_dis" , dao.selectReviewDislikeCount(review_num));
				
				ReviewLikeVO likeVO = new ReviewLikeVO();
				likeVO.setReview_num(review_num);
				likeVO.setMem_num(user_num);
				
				//좋아요 등록 여부 체크
				ReviewLikeVO db_like = dao.selectReviewLike(likeVO);
				if(db_like != null) { //좋아요 등록 되어있는 경우
					//좋아요 삭제 처리
					dao.deleteReviewLike(db_like.getRev_like_num());
					mapAjax.put("status" , "noLike");
					mapAjax.put("count" , dao.selectReviewLikeCount(review_num));
				}
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
