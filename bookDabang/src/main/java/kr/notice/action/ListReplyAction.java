package kr.notice.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeReplyVO;
import kr.util.PageUtil;

public class ListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");
		
		//댓글도 페이지 번호가 필요하다. >페이지번호 제공이 아닌 목록 제공하기 위해 rownum이 필요하기 때문.
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		//전체 글 갯수 확인
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		int count = dao.countReply(noti_num);
		
		int rowCount = 10;
		PageUtil page = new PageUtil(Integer.parseInt(pageNum),count,rowCount);
		
		//댓글 배열을 만든다. 
		List<NoticeReplyVO> list = null;
		
		if(count > 0) {//댓글이 있는 경우. 리스트 값을 받아온다. 
			list = dao.listReply(page.getStartRow(), page.getEndRow(), noti_num);
		}else {
			list=Collections.emptyList();//null을 넘기면 위험하니 빈 객체를 넘긴다. 
		}
		
		//ajax
		Map<String,Object> mapAjax = new HashMap<String, Object>();
		mapAjax.put("count", count);//전체 글의 수=> 목록처리하는데 값이 필요함. 
		mapAjax.put("rowCount", rowCount);//열의 수=> 목록처리하는데 값이 필요함.
		mapAjax.put("list", list);
		mapAjax.put("user_num", user_num);//댓글의 작성자와 접근자가 동일한지 확인용
		
		//json 형식으로 보내기 
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
