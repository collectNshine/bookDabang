package kr.post.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
	
		//로그인이 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		int post_num = Integer.parseInt(multi.getParameter("post_num"));
		String post_photo = multi.getFilesystemName("post_photo");
		
		PostDAO dao = PostDAO.getInstance();
		//수정 전 데이터 반환
		PostVO db_post = dao.getPost(post_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num != db_post.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			FileUtil.removeFile(request, post_photo);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호 일치
		PostVO post = new PostVO();
		post.setPost_num(post_num);
		post.setPost_title(multi.getParameter("post_title"));
		post.setPost_content(multi.getParameter("post_content"));
		post.setPost_ip(request.getRemoteAddr());
		post.setPost_photo(post_photo);
		
		dao.updatePost(post);
		
		//새 파일로 교체할 때 원래 파일 제거
		if(post_photo != null) {
			FileUtil.removeFile(request, db_post.getPost_photo());
		}
		return "redirect:/post/detail.do?post_num="+ post_num;
	}

}
