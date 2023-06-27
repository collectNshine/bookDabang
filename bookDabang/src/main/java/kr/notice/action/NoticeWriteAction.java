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
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_num == null) {//로그인을 하지 않은 경우.
			return "redirect:/member/loginForm.do";
		}
		if(user_auth != 9) {//관리자 계정이 아닌 경우
			return "redirect:/notice/noticeList.do";
		}
		
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
