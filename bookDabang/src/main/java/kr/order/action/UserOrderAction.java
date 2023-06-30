package kr.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class UserOrderAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		// POST 방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) { return "redirect:/item/itemList.do"; }
		
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		CartDAO dao = CartDAO.getInstance();
		String[] cart_nums = request.getParameterValues("cart_nums");
		
		List<CartVO> cartList = dao.getListCartByCartNum(cart_nums);
		
		String book_title;
		if(cartList.size() == 1) { book_title = cartList.get(0).getBookVO().getTitle(); }
		else { book_title = cartList.get(0).getBookVO().getTitle() + " 외 " + (cartList.size() - 1) + "건"; }
		
		// 개별 상품 정보 담기
		List<OrderDetailVO> detailList = new ArrayList<OrderDetailVO>();
		BookDAO bookDao = BookDAO.getInstance();
		
		for(CartVO cart : cartList) {
			BookVO book = bookDao.getBook(cart.getBk_num());
			
			if(book.getStock() < cart.getOrder_quantity()) {
				request.setAttribute("notice_msg", "[ " + book.getTitle() + " ] 재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
			
			OrderDetailVO orderDetail = new OrderDetailVO();
			orderDetail.setBk_num(cart.getBk_num());
			orderDetail.setBook_title(cart.getBookVO().getTitle());
			orderDetail.setBook_price(cart.getBookVO().getPrice());
			orderDetail.setBook_total(Integer.parseInt(request.getParameter("total_price")));
			orderDetail.setBook_author(cart.getBookVO().getAuthor());
			orderDetail.setBook_publisher(cart.getBookVO().getPublisher());
			orderDetail.setThumbnail(cart.getBookVO().getThumbnail());
			orderDetail.setOrder_quantity(cart.getOrder_quantity());
			orderDetail.setBook_total(cart.getSub_total());
			
			detailList.add(orderDetail);
		}
		
		OrderVO order = new OrderVO();
		order.setBook_title(book_title);
		order.setOrder_total(Integer.parseInt(request.getParameter("total_price")));
		order.setPayment(Integer.parseInt(request.getParameter("payment")));
		order.setReceive_name(request.getParameter("receive_name"));
		order.setReceive_post(request.getParameter("receive_post"));
		order.setReceive_address1(request.getParameter("receive_address1"));
		order.setReceive_address2(request.getParameter("receive_address2"));
		order.setReceive_phone(request.getParameter("receive_phone"));
		order.setEmail(request.getParameter("email"));
		order.setNotice(request.getParameter("notice"));
		order.setMem_num(user_num);
		
		OrderDAO orderDao = OrderDAO.getInstance();
		orderDao.insertOrder(order, detailList);
		orderDao.deleteCartByNum(cart_nums);
		
		// refresh 정보를 응답 header에 추가
		response.addHeader("Refresh", "2;url=../main/main.do");
		
		request.setAttribute("accessMsg", "주문 완료!!");
		request.setAttribute("accessUrl", request.getContextPath() + "/main/main.do");
		
		return "/WEB-INF/views/common/notice.jsp";
	}
}