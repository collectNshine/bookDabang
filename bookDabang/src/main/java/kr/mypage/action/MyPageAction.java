package kr.mypage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestFavVO;
import kr.request.vo.RequestVO;
import kr.util.PageUtil;
 
public class MyPageAction implements Action{ //[관리자]도서관리

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 되어 있지 않은 경우
		if(user_num == null) {
			return "redirefct:/member/loginForm.do";
		}
		
		//로그인 되어 있는 경우
		MyPageDAO dao = MyPageDAO.getInstance();
		//회원 정보
		MemberVO vo = dao.getMember(user_num);
		
		//PostDAO boardDao = PostDAO.getInstance();
		//게시판 글
		//List<PostVO> postList = PostDAO.getPostList(1,5, );	
		
		/*---[관리자]도서 관리 시작---*/
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BookDAO bookDao = BookDAO.getInstance();
		int count = bookDao.getItemCount(keyfield, keyword);
		
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"myPage.do");
		
		List<BookVO> list = null;
		if(count > 0) {
			list = bookDao.getBookList(page.getStartRow(), page.getEndRow(), keyfield, keyword);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		/*---[관리자]도서 관리 끝---*/
		
		/* [관리자]도서 신청 시작 */
		RequestDAO req_dao = RequestDAO.getInstance();
		int req_count = req_dao.getRequestCount(keyfield, keyword);
		PageUtil req_page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),req_count,10,10,"myPage.do");
		
	
		RequestFavVO fav = new RequestFavVO();
		fav.setMem_num(user_num);
		req_dao.getFavRequest(fav);
		
		List<RequestVO> req_list = null;
		if(req_count > 0) {
			req_list = req_dao.getListRequest(req_page.getStartRow(), req_page.getEndRow(), keyfield, keyword,user_num);
		}
		
		request.setAttribute("req_count", req_count);
		request.setAttribute("req_list", req_list);
		request.setAttribute("req_page", req_page.getPage());
		
		/* [관리자]도서 신청 끝 */
		
		request.setAttribute("member", vo);
			
		
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/myPage.jsp";
	}

}
