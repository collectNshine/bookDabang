package kr.main.action;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.PageUtil;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*----추천도서---*/	
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		if(category==null) category="";
		
		BookDAO dao = BookDAO.getInstance();
		int count = dao.getItemCount(keyfield, keyword,category);
		
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,3,3,"list.do","&category="+category);
		PageUtil b_page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,5,5,"list.do","&category="+category);
		
		
		List<BookVO> booklist = null;
		List<BookVO> book_list = null;
		if(count > 0) {
			booklist = dao.getBookList(page.getStartRow(), page.getEndRow(), keyfield, keyword,category);
			book_list = dao.getBookList( b_page.getStartRow(),  b_page.getEndRow(), keyfield, keyword,category);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("booklist", booklist);
		request.setAttribute("book_list", book_list);
		request.setAttribute("category", category);
		request.setAttribute("page", page.getPage());
		request.setAttribute("b_page", page.getPage());
		
		
		
		
		String postpageNum = request.getParameter("pageNum");
		if(postpageNum == null) postpageNum = "1";
		
		String postkeyfield = request.getParameter("keyfield");
		String postkeyword = request.getParameter("keyword");
		
		PostDAO postdao = PostDAO.getInstance();
		
		int postcount = postdao.getPostCount(postkeyfield, postkeyword);
									//keyfield, keyword, currentPage, count, rowCount, pageCount, 요청URL
		PageUtil postpage = new PageUtil(postkeyfield, postkeyword, Integer.parseInt(postpageNum), postcount, 3, 3, "list.do");
		
		List<PostVO> postlist = null;
		if(postcount > 0) {
			postlist = postdao.getPostList(postpage.getStartRow(), postpage.getEndRow(), postkeyfield, postkeyword);
		}
		request.setAttribute("postcount", postcount);
		request.setAttribute("postlist", postlist);
		request.setAttribute("postpage", postpage.getPage());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}

}




