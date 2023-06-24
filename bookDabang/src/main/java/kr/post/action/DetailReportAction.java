package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostReportVO;
import kr.util.StringUtil;

public class DetailReportAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//신고 번호
		int repo_num = Integer.parseInt(request.getParameter("repo_num"));
		PostDAO dao = PostDAO.getInstance();
		
		PostReportVO repo = dao.getReport(repo_num);
		
		//HTML 태그를 허용하지 않으면서 줄바꿈 처리
		repo.setRepo_content(StringUtil.useBrNoHtml(repo.getRepo_content()));
		
		request.setAttribute("repo", repo);
		
		//JSP 경로 반환
		return "/WEB-INF/views/post/detail_repo.jsp";
	}

}
