
package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class WriteAction implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 X
			return "redirect:/member/loginForm.do";
		}

		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) { //관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
	
		//관리자 로그인 O
		MultipartRequest multi = FileUtil.createFile(request);
		//자바빈에 저장
		BookVO book = new BookVO();
		//MultipartRequest의 getParameter 사용 (기존 request -> 무력화)
		book.setTitle(multi.getParameter("title"));
		book.setAuthor(multi.getParameter("author"));
		book.setPublisher(multi.getParameter("publisher"));
		book.setPrice(Integer.parseInt(multi.getParameter("price")));
		book.setStock(Integer.parseInt(multi.getParameter("stock")));
		book.setCategory(multi.getParameter("category"));
		book.setThumbnail(multi.getFilesystemName("thumbnail"));
		book.setContent(multi.getParameter("content"));

		BookDAO dao = BookDAO.getInstance();
		dao.insertBook(book);
		
		//refresh 정보를 응답 헤더에 추가
		response.addHeader("Refresh", "2;url=list.do");//=2초 뒤에 list.do로 이동
		
		request.setAttribute("accessMsg", "성공적으로 등록되었습니다.");
		request.setAttribute("accessUrl", "list.do");
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/notice.jsp";
		//notice.jsp를 보여주다가 2초 뒤에 list.do로 이동

	}

}
