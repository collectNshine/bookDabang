package kr.mypage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookMarkVO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;
import kr.post.dao.PostDAO;
import kr.post.vo.PostReportVO;
import kr.post.vo.PostVO;
import kr.request.dao.RequestDAO;
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
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 되어 있는 경우
		MyPageDAO dao = MyPageDAO.getInstance();
		//회원 정보
		MemberVO vo = dao.getMember(user_num);
		
		//PostDAO boardDao = PostDAO.getInstance();
		//게시판 글
		//List<PostVO> postList = PostDAO.getPostList(1,5, );	
		
		
		
		/*--- [사용자]책갈피 시작 ---*/
		String bm_pageNum = request.getParameter("bm_pageNum");
		if(bm_pageNum == null) bm_pageNum = "1";
		String bm_keyfield = request.getParameter("bm_keyfield");
		String bm_keyword = request.getParameter("bm_keyword");
		
		BookDAO bm_dao = BookDAO.getInstance();
		int bm_count = bm_dao.selectUserMarkCount(bm_keyfield, bm_keyword, user_num);
		
		PageUtil bm_page = new PageUtil(bm_keyfield,bm_keyword,Integer.parseInt(bm_pageNum),bm_count,10,10,"myPage.do","#book_mark");
		
		List<BookMarkVO> bm_list = null;
		if(bm_count > 0) {
			bm_list = bm_dao.getMarkList(bm_page.getStartRow(), bm_page.getEndRow(), bm_keyfield, bm_keyword, user_num);
		}
		
		request.setAttribute("bm_count", bm_count);
		request.setAttribute("bm_list", bm_list);
		request.setAttribute("bm_page", bm_page.getPage());
		/*--- [사용자]책갈피 끝 ---*/
		
		
		
		/*-- [사용자]작성글 시작 --*/
		String mp_pageNum = request.getParameter("mp_pageNum");
		if(mp_pageNum == null) mp_pageNum = "1";
		String mp_keyfield = request.getParameter("mp_keyfield");
		String mp_keyword = request.getParameter("mp_keyword");
		
		MyPageDAO postdao = MyPageDAO.getInstance();
		int mp_count = postdao.getMyPostCount(mp_keyfield, mp_keyword);
		PageUtil mp_page = new PageUtil(mp_keyfield,mp_keyword,Integer.parseInt(mp_pageNum),mp_count,10,10,"myPage.do","#post");
		
		List<PostVO> mp_list = null;
		if(mp_count > 0) {
			mp_list = postdao.getListMyPost(mp_page.getStartRow(), mp_page.getEndRow(), mp_keyfield, mp_keyword, user_num);
		}
		request.setAttribute("mp_count", mp_count);
		request.setAttribute("mp_list", mp_list);
		request.setAttribute("mp_page", mp_page.getPage());
		/*-- [사용자]작성글 끝 --*/
		
		
		/*---[관리자]도서 관리 시작---*/ 
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		if(category==null) category="";
		
		BookDAO bookDao = BookDAO.getInstance();
		int count = bookDao.getItemCount(keyfield, keyword, category);
		
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"myPage.do","#admin_book");
		
		List<BookVO> list = null;
		if(count > 0) {
			list = bookDao.getBookList(page.getStartRow(), page.getEndRow(), keyfield, keyword, category);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		/*---[관리자]도서 관리 끝---*/
		
	
		/*---[관리자]회원 관리 시작---*/
		String adminMemberpageNum = request.getParameter("adminMemberpageNum");
		if(adminMemberpageNum == null) adminMemberpageNum = "1";
		
		String adminMemberKeyfield = request.getParameter("adminMemberKeyfield");
		String adminMemberKeyword = request.getParameter("adminMemberKeyword");
		
		MyPageDAO adminMemberDao = MyPageDAO.getInstance();
		int adminMemberCount = adminMemberDao.getMemberCountByAdmin(adminMemberKeyfield, adminMemberKeyword);
		
		//페이지 처리
		PageUtil adminMemberPage = new PageUtil(adminMemberKeyfield,adminMemberKeyword,Integer.parseInt(pageNum),adminMemberCount,10,10,"myPage.do","#admin_member");
		List<MemberVO> adminMemberList = null;
		if(adminMemberCount > 0) {
			adminMemberList = dao.getListMemberByAdmin(adminMemberPage.getStartRow(), adminMemberPage.getEndRow(), adminMemberKeyfield, adminMemberKeyword);
			
		}
		
		request.setAttribute("adminMemberCount", adminMemberCount);
		request.setAttribute("adminMemberList", adminMemberList);
		request.setAttribute("adminMemberPage", adminMemberPage.getPage());
		
		/*---[관리자]회원 관리 끝---*/
		
		
		
		
		/*---[관리자]신고 관리 시작---*/
		String repoPageNum = request.getParameter("repoPageNum");
		if(repoPageNum == null) repoPageNum = "1";
		String repoKeyfield = request.getParameter("repoKeyfield");
		String repoKeyword = request.getParameter("repoKeyword");
		PostDAO postDao = PostDAO.getInstance();
		int repoCount = postDao.getReportCount(repoKeyfield, repoKeyword);
									//keyfield, keyword, currentPage, count, rowCount, pageCount, 요청URL
		PageUtil repoPage = new PageUtil(repoKeyfield, repoKeyword, Integer.parseInt(pageNum), repoCount, 10, 10, "myPage.do","#admin_report");
		
		List<PostReportVO> repoList = null;
		if(repoCount > 0) {
			repoList = postDao.getReportList(repoPage.getStartRow(), repoPage.getEndRow(), repoKeyfield, repoKeyword);
		}
		
		request.setAttribute("repoCount", repoCount);
		request.setAttribute("repoList", repoList);
		request.setAttribute("repoPage", repoPage.getPage());
		/*---[관리자]신고 관리 끝---*/
		
		/*---[관리자]도서 신청 시작---*/
		String req_PageNum = request.getParameter("req_PageNum");
		
		RequestDAO req_dao = RequestDAO.getInstance();
		RequestVO reqvo = new RequestVO();
		
		if(req_PageNum == null) req_PageNum = "1";
		String req_Keyfield = request.getParameter("req_Keyfield");
		String req_Keyword = request.getParameter("req_Keyword");
		
		int req_Count = req_dao.getRequestCount(req_Keyfield, req_Keyword);
		PageUtil req_Page = new PageUtil(req_Keyfield,req_Keyword,Integer.parseInt(pageNum),req_Count,10,10,"myPage.do","#admin_request");
		
		List<RequestVO> req_list = null;
		if(req_Count > 0) {
			req_list = req_dao.getListRequest(req_Page.getStartRow(), req_Page.getEndRow(), req_Keyfield, req_Keyword,user_num);
		}
		
		request.setAttribute("req_Count", req_Count);
		request.setAttribute("req_list", req_list);
		request.setAttribute("req_Page", req_Page.getPage());
		/*---[관리자]도서 신청 끝---*/
		
		
		request.setAttribute("member", vo);
			
		//JSP 경로 반환
		return "/WEB-INF/views/mypage/myPage.jsp";
	}

}
