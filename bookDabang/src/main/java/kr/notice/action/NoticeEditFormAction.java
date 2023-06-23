package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;

public class NoticeEditFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO vo = new NoticeVO();
		
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		vo = dao.selectOneNotice(noti_num);
		
		request.setAttribute("noti", vo);
		
		return "/WEB-INF/views/notice/noticeEditForm.jsp";
	}

}
