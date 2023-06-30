package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class UserModifyFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		// GET방식으로 값을 전달받기 때문에 order_num 값 저장 가능
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO order = dao.getOrder(order_num);
		
		List<OrderDetailVO> detailList = dao.getListOrderDetail(order_num);
		
		request.setAttribute("order", order);
		request.setAttribute("detailList", detailList);
		
		return "/WEB-INF/views/order/user_modifyForm.jsp";
	}
}
