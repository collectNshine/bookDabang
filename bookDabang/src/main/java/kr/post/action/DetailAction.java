package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//서평 번호
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		PostDAO dao = PostDAO.getInstance();
		
		PostVO post = dao.getPost(post_num);
		
		//HTML 태그를 허용하지 않음
		post.setPost_title(StringUtil.useNoHtml(post.getPost_title()));
		
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		//post.setPost_content(StringUtil.useBrNoHtml(post.getPost_content()));
		
		request.setAttribute("post", post);
		
		//JSP 경로 반환
		return "/WEB-INF/views/post/detail.jsp";
	}

}
