package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 여부 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request); //FileUtil 클래스에서 인코딩 작업 처리
		//자바빈(VO) 생성
		PostVO post = new PostVO();
		post.setPost_title(multi.getParameter("post_title"));
		post.setPost_content(multi.getParameter("post_content"));
		post.setPost_photo(multi.getFilesystemName("post_photo"));
		post.setPost_ip(request.getRemoteAddr());
		post.setMem_num(user_num);
		post.setBk_num(Integer.parseInt(multi.getParameter("bk_num")));
		
		PostDAO dao = PostDAO.getInstance();
		dao.insertPost(post);
		
		//refresh 정보를 응답 헤더에 추가
		response.addHeader("Refresh", "2;url=list.do");//=2초 뒤에 list.do로 이동
				
		request.setAttribute("accessMsg", "서평이 등록되었습니다.");
		request.setAttribute("accessUrl", "list.do");
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/notice.jsp";
	}

}
