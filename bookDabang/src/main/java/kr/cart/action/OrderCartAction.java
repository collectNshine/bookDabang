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

public class OrderCartAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//유저 정보와 선택한 DB의 값을 넘긴다. 
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return"redirect:/member/loginForm.jsp";
		}
		String user_id = (String) session.getAttribute("user_id");
		String[] cart_nums = request.getParameterValues("cart_nums");
		MemberDAO mem_dao = MemberDAO.getInstance();
		MemberVO mem_vo = mem_dao.selectUser(user_id);
		
		CartDAO dao = CartDAO.getInstance();
		List<CartVO> list = null; 
		list = dao.getListCartByCartNum(cart_nums);
		
		request.setAttribute("member", mem_vo);
		request.setAttribute("list", list);
		
		return "/WEB-INF/views/cart/order.jsp";
	}

}
