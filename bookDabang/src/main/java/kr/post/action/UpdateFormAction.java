package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		PostDAO dao = PostDAO.getInstance();
		PostVO post = dao.getPost(post_num);

		request.getParameter("post_title");
		request.getParameter("post_content");
		request.getParameter("post_photo");
		
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != post.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//큰 따옴표 처리(수정폼의 input태그에서 오동작)
		post.setPost_title(StringUtil.parseQuot(post.getPost_title()));
		//내용 - <br>태그 처리
		post.setPost_content(StringUtil.changeBr(post.getPost_content()));
		
		//로그인이 되어 있고, 로그인한 회원번호와 작성자 회원번호 일치
		request.setAttribute("post", post);
		
		return "/WEB-INF/views/post/updateForm.jsp";
	}

}
