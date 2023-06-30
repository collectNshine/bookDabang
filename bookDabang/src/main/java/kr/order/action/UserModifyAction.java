package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class UserModifyAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		// POST방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) { return "redirect:/item/itemList.do"; }
		
		request.setCharacterEncoding("UTF-8");
		
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		
		// 주문 수정전 배송 상태를 한번 더 체크
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_num);
		
		if(db_order.getStatus() > 1) {
			// 배송준비중 이상으로 관리자가 변경한 상품을 주문자가 변경 불가
			request.setAttribute("notice_msg", "배송상태가 변경되어 주문자가 주문정보 변경 불가");
			request.setAttribute("notice_url", request.getContextPath() + "/order/orderList.do");
		}
		
		// 배송상태가 배송대기일 경우
		OrderVO order = new OrderVO();
		order.setOrder_num(order_num);
		order.setStatus(Integer.parseInt(request.getParameter("status")));
		order.setReceive_name(request.getParameter("receive_name"));
		order.setReceive_post(request.getParameter("receive_post"));
		order.setReceive_address1(request.getParameter("receive_address1"));
		order.setReceive_address2(request.getParameter("receive_address2"));
		order.setReceive_phone(request.getParameter("receive_phone"));
		order.setEmail(request.getParameter("email"));
		order.setNotice(request.getParameter("notice"));
		
		// 배송관련 주문 정보 수정
		dao.updateOrder(order);
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath() + "/order/userModifyForm.do?order_num=" + order_num);
		 
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}
}
