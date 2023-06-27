package kr.book.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class UpdateAction implements Action{

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

		//POST 방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			return "redirect:/mypage/myPage.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		int bk_num = Integer.parseInt(multi.getParameter("bk_num"));
		String thumbnail = multi.getFilesystemName("thumbnail");
		BookDAO dao = BookDAO.getInstance();
		//수정 전 데이터 반환
		BookVO db_book = dao.getBook(bk_num);
		//업데이트 될 내용 자바빈에 저장
		BookVO book = new BookVO();
		book.setBk_num(bk_num);
		book.setTitle(multi.getParameter("title"));
		book.setAuthor(multi.getParameter("author"));
		book.setPublisher(multi.getParameter("publisher"));
		book.setPrice(Integer.parseInt(multi.getParameter("price")));
		book.setStock(Integer.parseInt(multi.getParameter("stock")));
		book.setCategory(multi.getParameter("category"));
		book.setThumbnail(thumbnail);
		book.setContent(multi.getParameter("content"));
		
		dao.updateBook(book);
		
		//새 파일로 교체할 때 원래 파일은 제거
		if(thumbnail != null) {
			FileUtil.removeFile(request, db_book.getThumbnail());
		}

		//자바스크립트로 띄움
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/book/updateForm.do?bk_num="+bk_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}
}
