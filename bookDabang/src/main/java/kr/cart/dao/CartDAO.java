package kr.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.book.vo.BookVO;
import kr.cart.vo.CartVO;
import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class CartDAO {
	// Singleton Pattern
	private static CartDAO instance = new CartDAO();
	public static CartDAO getInstance() { return instance; }
	private CartDAO() {}
	
	// 장바구니 등록
	public void insertCart(CartVO cart) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO cart (cart_num, bk_num, order_quantity, mem_num) VALUES (cart_seq.nextval, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getBk_num());
			pstmt.setInt(2, cart.getOrder_quantity());
			pstmt.setInt(3, cart.getMem_num());
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 장바구니 목록
	public List<CartVO> getListCart(int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CartVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM book_list b JOIN cart c ON b.bk_num=c.bk_num JOIN member_detail m ON c.mem_num=m.mem_num WHERE c.mem_num=? ORDER BY b.bk_num ASC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<CartVO>();
			while(rs.next()) {
				CartVO cart = new CartVO();
				cart.setCart_num(rs.getInt("cart_num"));
				cart.setBk_num(rs.getInt("bk_num"));
				cart.setOrder_quantity(rs.getInt("order_quantity"));
				cart.setCart_date(rs.getDate("cart_date"));
				cart.setMem_num(rs.getInt("mem_num"));
				
				// 도서 정보를 담기 위해 BookVO 객체 생성
				BookVO book = new BookVO();
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				book.setPrice(rs.getInt("price"));
				book.setStock(rs.getInt("stock"));
				book.setCategory(rs.getString("category"));
				book.setThumbnail(rs.getString("thumbnail"));
				
				// BookVO를 CartVO에 저장
				cart.setBookVO(book);
				
				// 회원 정보를 담기 위해 MemberVO 객체 생성
				MemberVO member = new MemberVO();
				member.setName(rs.getString("name"));
				member.setAddress1(rs.getString("address1"));
				member.setAddress2(rs.getString("address2"));
				member.setZipcode(rs.getString("zipcode"));
				
				// MemberVO를 CartVO에 저장
				cart.setMemberVO(member);
				
				// 같은 도서의 총 구매 금액 구하기
				cart.setSub_total(cart.getOrder_quantity() * book.getPrice());
				
				list.add(cart);
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	// 주문번호별 장바구니 목록
		public List<CartVO> getListCartByCartNum(String[] cart_num) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<CartVO> list = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				
				sql = "SELECT * FROM book_list b JOIN cart c ON b.bk_num=c.bk_num JOIN member_detail m ON c.mem_num=m.mem_num WHERE c.cart_num IN(" + String.join(",", cart_num) + ") ORDER BY b.bk_num ASC";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<CartVO>();
				while(rs.next()) {
					CartVO cart = new CartVO();
					cart.setBk_num(rs.getInt("bk_num"));
					cart.setOrder_quantity(rs.getInt("order_quantity"));
					cart.setMem_num(rs.getInt("mem_num"));
					
					// 도서 정보를 담기 위해 BookVO 객체 생성
					BookVO book = new BookVO();
					book.setTitle(rs.getString("title"));
					book.setPrice(rs.getInt("price"));
					book.setStock(rs.getInt("stock"));
					book.setThumbnail(rs.getString("thumbnail"));
					
					// BookVO를 CartVO에 저장
					cart.setBookVO(book);
					
					// 회원 정보를 담기 위해 MemberVO 객체 생성
					MemberVO member = new MemberVO();
					member.setName(rs.getString("name"));
					member.setAddress1(rs.getString("address1"));
					member.setAddress2(rs.getString("address2"));
					member.setZipcode(rs.getString("zipcode"));
					member.setPhone(rs.getString("phone"));
					member.setEmail(rs.getString("email"));
					
					// MemberVO를 CartVO에 저장
					cart.setMemberVO(member);
					
					cart.setSub_total(cart.getOrder_quantity() * book.getPrice());
					
					list.add(cart);
				}
			} catch(Exception e) { throw new Exception(e); }
			finally { DBUtil.executeClose(rs, pstmt, conn); }
			
			return list;
		}
	
	// 장바구니 상세
	public CartVO getCart(CartVO cart) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CartVO cartSaved = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM cart WHERE bk_num=? AND mem_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getBk_num());
			pstmt.setInt(2, cart.getMem_num());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cartSaved = new CartVO();
				cartSaved.setCart_num(rs.getInt("cart_num"));
				cartSaved.setBk_num(rs.getInt("bk_num"));
				cartSaved.setOrder_quantity(rs.getInt("order_quantity"));
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return cartSaved;
	}
	
	// 장바구니 수정
	public void updateCart(CartVO cart) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE cart SET order_quantity=? WHERE cart_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getOrder_quantity());
			pstmt.setInt(2, cart.getCart_num());
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 장바구니 도서번호와 회원번호별 수정 → 동일 도서일 경우 수량이 합쳐지도록 처리
	public void updateCartByBk_num(CartVO cart) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE cart SET order_quantity=? WHERE bk_num=? AND mem_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getOrder_quantity());
			pstmt.setInt(2, cart.getBk_num());
			pstmt.setInt(3, cart.getMem_num());
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 장바구니 삭제
	public void deleteCart(int cart_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "DELETE FROM cart WHERE cart_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart_num);
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 회원번호(mem_num)별 총 구매 금액
	public int getTotalByMem_num(int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int total = 0;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT SUM(sub_total) FROM (SELECT c.mem_num, c.order_quantity * b.price AS sub_total FROM cart c JOIN book_list b ON c.bk_num=b.bk_num) WHERE mem_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) { total = rs.getInt(1); }
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return total;
	}
}
