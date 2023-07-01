package kr.request.action;
  
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
  
import kr.controller.Action; 
import kr.request.dao.RequestDAO; 
import kr.request.vo.RequestVO;
  
public class ModifyFormAction implements Action{
  
  @Override 
  public String execute(HttpServletRequest request1, HttpServletResponse response) throws Exception { 
	  HttpSession session = request1.getSession(); 
	  Integer user_num = (Integer)session.getAttribute("user_num"); 
	  if(user_num==null) { 
		  return "redirect:/member/loginForm.do"; 
		}
	  
	  int req_num = Integer.parseInt(request1.getParameter("req_num"));
	  RequestDAO dao = RequestDAO.getInstance();
	  RequestVO request = dao.getRequest(req_num);
	  //로그인한 user_num이랑 글쓴 mem_num 일치여부 체크
	  if(user_num!=request.getMem_num()) {
		  return "/WEB-INF/views/common/notice.jsp";
	  }
	  
	  
	  
	  
	  request1.setAttribute("request", request);
	  
	  return "/WEB-INF/views/request/modifyForm.jsp"; 
  }
	  
	  }
 