package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인이 된 경우
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		PostDAO dao = PostDAO.getInstance();
		PostVO db_post = dao.getPost(post_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != db_post.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteBoard(post_num);
		
		//파일 삭제
		FileUtil.removeFile(request, db_post.getPost_photo());
		
		return "redirect:/post/list.do";
	}

}
