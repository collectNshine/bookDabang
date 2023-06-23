package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;

public class NoticeDetailFormAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO vo = new NoticeVO();
		vo = dao.selectOneNotice(noti_num);
		
		request.setAttribute("noti", vo);

		return "/WEB-INF/views/notice/noticeDetailForm.jsp";
	}

}
