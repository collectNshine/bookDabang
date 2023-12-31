package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;

public class AdminDeleteOrderAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 9) { return "/WEB-INF/views/common/notice.jsp"; }
		
		int order_num = Integer.parseInt(request.getParameter("order_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		dao.deleteOrder(order_num);
		
		request.setAttribute("notice_msg", "정상적으로 주문정보 삭제 완료!!");
		request.setAttribute("notice_url", request.getContextPath() + "/mypage/myPage.do#admin_order");
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}
}
