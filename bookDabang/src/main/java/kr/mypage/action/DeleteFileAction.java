package kr.mypage.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;
import kr.util.FileUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, 
						  HttpServletResponse response)
								  throws Exception {
		//로그인 여부 체크
		Map<String,String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { //로그인 된 경우
			int mem_num = Integer.parseInt(request.getParameter("mem_num"));
			MyPageDAO dao = MyPageDAO.getInstance();
			MemberVO db_board = dao.getMember(mem_num);
			//로그인한 회원번호 = 작성자 회원번호 일치 여부 체크
			if(user_num!=db_board.getMem_num()) { //불일치할 경우
				mapAjax.put("result", "wrongAccess");
			}else { //일치할 경우
				dao.deletePhoto(mem_num); //db에서 파일 정보 삭제
				FileUtil.removeFile(request, db_board.getPhoto()); //파일 삭제
				mapAjax.put("result", "success");
			}
		}		
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}

