package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override 
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 되지 않은 경우
		if(user_num == null) { 
			return "redirect:/member/loginForm.do";
		}
		//관리자가 아닌 경우
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) { //관리자로 로그인 X
			return "/WEB-INF/views/common/notice.jsp";
		}

		//관리자 로그인 O
		request.setCharacterEncoding("utf-8");
		int bk_num = Integer.parseInt(request.getParameter("bk_num"));
		BookDAO dao = BookDAO.getInstance();
		BookVO db_book = dao.getBook(bk_num);
		
		dao.deleteBook(bk_num);
		//파일 삭제 (null여부는 메서드에 포함되어 있으므로 따로 체크X)
		FileUtil.removeFile(request, db_book.getThumbnail());
		
		//refresh 정보를 응답 헤더에 추가
		response.addHeader("Refresh", "2;url=list.do");//=2초 뒤에 list.do로 이동
		
		request.setAttribute("accessMsg", "정상적으로 삭제되었습니다.");
		request.setAttribute("accessUrl", request.getContextPath()+"/mypage/myPage.do");
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/notice.jsp";
	}
}
