package kr.notice.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.PageUtil_updated;


public class NoticeListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
	
		String keyfield = request.getParameter("keyfield"); 
		String keyword = request.getParameter("keyword");
		//카테고리 안에 null이 들어가면 예외 발생.=> 기본 값을 0으로 보내기?
		String noti_category = request.getParameter("noti_category");
		
		if(noti_category == null) noti_category = "0";
		Integer category = Integer.parseInt(noti_category);
		
		NoticeDAO dao = NoticeDAO.getInstance();
		int count = dao.countNotice(keyfield, keyword, category);
		
		PageUtil_updated page = new PageUtil_updated(keyfield, keyword, Integer.parseInt(pageNum),
				count, 15, 5, "noticeList.do",category);
		
		List<NoticeVO> list = null;
		List<NoticeVO> list2 = null;
		
		if(count>0) {
			
			list = dao.selectList(page.getStartRow(), page.getEndRow(), keyfield, keyword,category);
			list2 = dao.categoryNameNum();
		}

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("list2", list2);
		request.setAttribute("noti_category", category);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/notice/noticeList.jsp";
	}

	
}
