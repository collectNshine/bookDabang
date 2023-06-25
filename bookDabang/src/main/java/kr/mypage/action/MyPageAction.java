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
import kr.post.dao.PostDAO;
import kr.post.vo.PostReportVO;
import kr.post.vo.PostVO;
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
		
		/*-- [사용자]작성글 시작 --*/
		String PostpageNum = request.getParameter("pageNum");
		if(PostpageNum == null) PostpageNum = "1";
		String Postkeyfield = request.getParameter("keyfield");
		String Postkeyword = request.getParameter("keyword");
		
		MyPageDAO postdao = MyPageDAO.getInstance();
		int post_count = postdao.getMyPostCount(Postkeyfield, Postkeyword);
		PageUtil post_page = new PageUtil(Postkeyfield,Postkeyword,Integer.parseInt(PostpageNum),post_count,10,10,"myPage.do");
		
		List<PostVO> postlist = null;
		if(post_count > 0) {
			postlist = postdao.getListMyPost(post_page.getStartRow(), post_page.getEndRow(), Postkeyfield, Postkeyword, user_num);
		}
		request.setAttribute("postCount", post_count);
		request.setAttribute("postList", postlist);
		request.setAttribute("postPage", post_page.getPage());
		
		/*-- [사용자]작성글 끝 --*/
		
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
		String req_PageNum = request.getParameter("req_PageNum");
		if(req_PageNum == null) req_PageNum = "1";
		String req_keyfield = request.getParameter("req_keyfield");
		String req_keyword = request.getParameter("req_keyword");
		
		RequestDAO req_dao = RequestDAO.getInstance();
		int req_count = req_dao.getRequestCount(req_keyfield, req_keyword);
		PageUtil req_page = new PageUtil(req_keyfield,req_keyword,Integer.parseInt(req_PageNum),req_count,10,10,"myPage.do");
		
	
		RequestFavVO fav = new RequestFavVO();
		fav.setMem_num(user_num);
		req_dao.getFavRequest(fav);
		
		List<RequestVO> req_list = null;
		if(req_count > 0) {
			req_list = req_dao.getListRequest(req_page.getStartRow(), req_page.getEndRow(), req_keyfield, req_keyword,user_num);
		}
		
		request.setAttribute("req_count", req_count);
		request.setAttribute("req_list", req_list);
		request.setAttribute("req_page", req_page.getPage());
		
		/* [관리자]도서 신청 끝 */
		
		/*---[관리자]신고 관리 시작---*/
		String repoPageNum = request.getParameter("repoPageNum");
		if(repoPageNum == null) repoPageNum = "1";
		String repoKeyfield = request.getParameter("repoKeyfield");
		String repoKeyword = request.getParameter("repoKeyword");
		PostDAO postDao = PostDAO.getInstance();
		int repoCount = postDao.getReportCount(repoKeyfield, repoKeyword);
									//keyfield, keyword, currentPage, count, rowCount, pageCount, 요청URL
		PageUtil repoPage = new PageUtil(repoKeyfield, repoKeyword, Integer.parseInt(repoPageNum), repoCount, 10, 10, "myPage.do");
		
		List<PostReportVO> repoList = null;
		if(repoCount > 0) {
			repoList = postDao.getReportList(repoPage.getStartRow(), repoPage.getEndRow(), repoKeyfield, repoKeyword);
		}
		
		request.setAttribute("repoCount", repoCount);
		request.setAttribute("repoList", repoList);
		request.setAttribute("repoPage", repoPage.getPage());
		/*---[관리자]신고 관리 끝---*/
		
		request.setAttribute("member", vo);
			
		
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/myPage.jsp";
	}

}
