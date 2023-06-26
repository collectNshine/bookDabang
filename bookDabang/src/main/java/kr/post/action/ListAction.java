package kr.post.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.book.vo.ReviewVO;
import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostFavVO;
import kr.post.vo.PostVO;
import kr.util.PageUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		//request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		//int post_num = Integer.parseInt(request.getParameter("post_num"));
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		/*---서평 피드 시작---*/
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		PostDAO dao = PostDAO.getInstance();
		//int favcount = dao.selectFavCount(post_num);
		//int recount = dao.getReplyPostCount(post_num);
		
		int count = dao.getPostCount(keyfield, keyword);
									//keyfield, keyword, currentPage, count, rowCount, pageCount, 요청URL
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 10, 10, "list.do");
		
		List<PostVO> list = null;
		if(count > 0) {
			list = dao.getPostList(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		//request.setAttribute("favcount", favcount);
		//request.setAttribute("recount", recount);
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		/*---서평 피드 끝---*/
		
		/*---한줄기록 피드 시작---*
		//request.setCharacterEncoding("utf-8");
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		
		String rePageNum = request.getParameter("rePageNum");
		if(rePageNum == null) rePageNum = "1";
		
		String reKeyfield = request.getParameter("reKeyfield");
		String reKeyword = request.getParameter("reKeyword");
		
		BookDAO bookDao = BookDAO.getInstance();
		BookVO book = bookDao.getBook(bk_num);
		request.setAttribute("book", book);
		
		int reCount = bookDao.getReCount(reKeyfield, reKeyword);
		PageUtil rePage = new PageUtil(reKeyfield, reKeyword, Integer.parseInt(rePageNum), reCount, 10, 10, "list.do");
		
		List<ReviewVO> reList = null;
		if(reCount > 0) {
			reList = bookDao.getListReview(rePage.getStartRow(), rePage.getEndRow(), bk_num);
		}
		request.setAttribute("reCount", reCount);
		request.setAttribute("reList", reList);
		request.setAttribute("rePage", rePage.getPage());
		*---한줄기록 피드 끝---*/
		
		//JSP 경로 반환
		return "/WEB-INF/views/post/list.jsp";
	}

}
