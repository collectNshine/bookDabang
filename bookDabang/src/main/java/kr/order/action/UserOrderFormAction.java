package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.mypage.dao.MyPageDAO;

public class UserOrderFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { return "redirect:/member/loginForm.do"; }
		
		// POST 방식의 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) { return "redirect:/item/itemList.do"; }
		
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("UTF-8");
		
		int total_price = Integer.parseInt(request.getParameter("total_price"));	// 선택 상품 총 금액
		
		if(total_price <= 0) {
			request.setAttribute("notice_msg", "정상적인 주문 X or 상품 수량 부족!!");
			request.setAttribute("notice_url", request.getContextPath() + "/book/list.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		CartDAO dao = CartDAO.getInstance();
		String[] cart_nums = request.getParameterValues("cart_nums");
		
		List<CartVO> cartList = dao.getListCartByCartNum(cart_nums);
		
		BookDAO bookDao = BookDAO.getInstance();
		for(CartVO cart : cartList) {
			BookVO book = bookDao.getBook(cart.getBk_num());
			
			if(book.getStock() < cart.getOrder_quantity() || book.getStock() <= 0) {
				request.setAttribute("notice_msg", "[ " + book.getTitle() + " ] 재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
		}
		
		MyPageDAO myDao = MyPageDAO.getInstance();
		MemberVO myVo = myDao.getMember(user_num);

		request.setAttribute("member", myVo);
		request.setAttribute("list", cartList);
		request.setAttribute("cart_nums", cart_nums);
		request.setAttribute("total_price", total_price);
		
		return "/WEB-INF/views/order/user_orderForm.jsp";
	}
}
