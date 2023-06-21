package kr.request.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.request.dao.RequestDAO;
import kr.request.vo.RequestVO;
import kr.util.PageUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		RequestDAO dao = RequestDAO.getInstance();
		int count = dao.getRequestCount(keyfield,keyword);
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"list.do");
		
		List<RequestVO> list = null;
		
		if(count > 0 ) { list = dao.getListRequest(page.getStartRow(),page.getEndRow(),keyfield,keyword); }
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/request/list.jsp";
	}

}
