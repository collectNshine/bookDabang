package kr.request.action;
  
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
  
import kr.controller.Action; 
import kr.request.dao.RequestDAO; 
import kr.request.vo.RequestVO;
  
public class ModifyAction implements Action{
  
  @Override 
  public String execute(HttpServletRequest request1, HttpServletResponse response) throws Exception { 
	  request1.setCharacterEncoding("utf-8");
	  HttpSession session = request1.getSession(); 
	  Integer user_num = (Integer)session.getAttribute("user_num"); 
	  if(user_num==null) { 
		  return "redirect:/member/loginForm.do"; 
		}
	  int req_num = Integer.parseInt(request1.getParameter("req_num"));
	  
	  RequestVO request = new RequestVO(); 
	  request.setReq_num(req_num);
	  request.setReq_title(request1.getParameter("req_title"));
	  request.setReq_author(request1.getParameter("req_author"));
	  request.setReq_publisher(request1.getParameter("req_publisher"));
	  request.setId(request1.getParameter("id"));
	  request.setReq_ip(request1.getRemoteAddr()); 
	  request.setMem_num(user_num);
	  
	  RequestDAO dao = RequestDAO.getInstance();
	  dao.modifyRequest(request);
	  
	  return "/WEB-INF/views/request/modify.jsp"; 
	  
	  
	  
	  }
}
 