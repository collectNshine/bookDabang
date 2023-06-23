package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;

public class NoticeWriteAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//인코딩 처리 
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		NoticeVO vo = new NoticeVO();
		
		vo.setNoti_title(request.getParameter("noti_title"));
		vo.setNoti_content(request.getParameter("noti_content"));
		vo.setNoti_category(Integer.parseInt(request.getParameter("noti_category")));
		vo.setMem_num(user_num);
		
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.insertNotice(vo);
		
		return "redirect:/notice/noticeList.do";
	}

}
