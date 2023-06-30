package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class AdminModifyAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) { return "/WEB-INF/views/common/notice.jsp"; }
		
		request.setCharacterEncoding("UTF-8");
		
		OrderVO order = new OrderVO();
		order.setOrder_num(Integer.parseInt(request.getParameter("order_num")));
		order.setStatus(Integer.parseInt(request.getParameter("status")));
		order.setReceive_name(request.getParameter("receive_name"));
		order.setReceive_post(request.getParameter("receive_post"));
		order.setReceive_address1(request.getParameter("receive_address1"));
		order.setReceive_address2(request.getParameter("receive_address2"));
		order.setReceive_phone(request.getParameter("receive_phone"));
		order.setEmail(request.getParameter("email"));
		order.setNotice(request.getParameter("notice"));
		
		OrderDAO orderDao = OrderDAO.getInstance();
		orderDao.updateOrder(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정 완료!!");
		request.setAttribute("notice_url", request.getContextPath() + "/order/adminModifyForm.do?order_num=" + order.getOrder_num());
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}
}
