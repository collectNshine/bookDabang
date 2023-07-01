package kr.notice.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;

public class NoticeDeleteAjaxAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		if(request.getMethod()
				  .toUpperCase().equals("GET")) {
			return "redirect:/member/loginForm.do";
		}
		String[] noti_nums = request.getParameterValues("noti_nums");
		
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.deleteNotice(noti_nums);
		
		return"redirect:/notice/noticeList.do";
	}

}
