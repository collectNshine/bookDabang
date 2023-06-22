package kr.book.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.book.dao.BookDAO;
import kr.book.vo.BookMarkVO;
import kr.controller.Action;

public class WriteMarkAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		//로그인 여부 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { //로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 자바빈에 저장
			int bk_num = Integer.parseInt(request.getParameter("bk_num"));
			BookMarkVO markVO = new BookMarkVO();
			markVO.setBk_num(bk_num);
			markVO.setMem_num(user_num);
			
			//책갈피 등록 여부 체크
			BookDAO dao = BookDAO.getInstance();
			BookMarkVO db_mark = dao.selectMark(markVO);
			if(db_mark != null) { //책갈피 등록 되어있는 경우 -> 클릭 시 등록 해제 처리
				dao.deleteMark(db_mark.getMark_num());
				mapAjax.put("result" , "success");
				mapAjax.put("status" , "noMark");
				mapAjax.put("count" , dao.selectMarkCount(bk_num));
			}else { //책갈피 등록 안 되어있는 경우 -> 클릭 시 등록 처리
				dao.insertMark(markVO);
				mapAjax.put("result" , "success");
				mapAjax.put("status" , "yesMark");
				mapAjax.put("count" , dao.selectMarkCount(bk_num));
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";

	}

}
