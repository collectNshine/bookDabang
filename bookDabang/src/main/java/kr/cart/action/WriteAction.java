package kr.cart.action;   

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.book.dao.BookDAO;
import kr.book.vo.BookVO;
import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;

public class WriteAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { mapAjax.put("result", "logout"); }
		else {
			request.setCharacterEncoding("UTF-8");
			
			CartVO cart = new CartVO();
			cart.setBk_num(Integer.parseInt(request.getParameter("bk_num")));
			cart.setOrder_quantity(Integer.parseInt(request.getParameter("order_quantity")));
			cart.setMem_num(user_num);
			
			CartDAO dao = CartDAO.getInstance();
			CartVO db_cart = dao.getCart(cart);
			if(db_cart == null) {
				dao.insertCart(cart);
				mapAjax.put("result", "success");
			} else {
				BookDAO bookDao = BookDAO.getInstance();
				BookVO book = bookDao.getBook(db_cart.getBk_num());
				
				int order_quantity = db_cart.getOrder_quantity() + cart.getOrder_quantity();
				
				if(book.getStock() < order_quantity) { mapAjax.put("result", "over_quantity"); }
				else {
					cart.setOrder_quantity(order_quantity);
					dao.updateCartByBk_num(cart);
					mapAjax.put("result", "success");
				}
			}
		}
		
		// JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
