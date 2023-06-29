package kr.cart.action;   

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ListAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		String user_id = (String)session.getAttribute("user_id");
		
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		CartDAO dao = CartDAO.getInstance();
		MemberDAO mem_dao = MemberDAO.getInstance();
		
		int select_price = dao.getTotalByMem_num(user_num);
		
		List<CartVO> list = null;
		MemberVO vo = null;
		
		if(select_price > 0) { 
			list = dao.getListCart(user_num); 
			vo = mem_dao.selectUser(user_id);
		}
		
		request.setAttribute("select_price", select_price);
		request.setAttribute("list", list);
		request.setAttribute("vo", vo);
		
		return "/WEB-INF/views/cart/list.jsp";
	}
}
