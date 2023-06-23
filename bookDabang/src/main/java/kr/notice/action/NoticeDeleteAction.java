package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;

public class NoticeDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.deleteNotice(noti_num);
		
		return "redirect:/notice/noticeList.do" ;
	}

}
