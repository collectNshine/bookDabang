package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;

public class NoticeEditAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO vo = new NoticeVO();
		vo.setNoti_num(Integer.parseInt(request.getParameter("noti_num")));
		vo.setNoti_category(Integer.parseInt(request.getParameter("noti_category")));
		vo.setNoti_title(request.getParameter("noti_title"));
		vo.setNoti_content(request.getParameter("noti_content"));
	
		dao.updateNotice(vo);
		
		return "redirect:/notice/noticeList.do";
	}

}
