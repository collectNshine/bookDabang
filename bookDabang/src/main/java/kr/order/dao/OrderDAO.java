package kr.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class OrderDAO {
	// Singleton Pattern
	private static OrderDAO instance = new OrderDAO();
	public static OrderDAO getInstance() { return instance; }
	private OrderDAO() {}
	
	// 주문 등록
	public void insertOrder(OrderVO order, List<OrderDetailVO> detailList) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		String sub_sql2 = "";
		int order_num = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			// order_num 구하기
			sql = "SELECT order_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) { order_num = rs.getInt(1); }
			
			// 주문 정보 저장
			if(order.getNotice() != null && !"".equals(order.getNotice())) {
				sub_sql += ", notice";
				sub_sql2 += ", ?";
			}
			sql = "INSERT INTO orders (order_num, book_title, order_total, payment, receive_name, receive_post, receive_address1, receive_address2, receive_phone, email, mem_num" + sub_sql + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" + sub_sql2 + ")";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, order_num);
			pstmt2.setString(2, order.getBook_title());
			pstmt2.setInt(3, order.getOrder_total());
			pstmt2.setInt(4, order.getPayment());
			pstmt2.setString(5, order.getReceive_name());
			pstmt2.setString(6, order.getReceive_post());
			pstmt2.setString(7, order.getReceive_address1());
			pstmt2.setString(8, order.getReceive_address2());
			pstmt2.setString(9, order.getReceive_phone());
			pstmt2.setString(10, order.getEmail());
			pstmt2.setInt(11, order.getMem_num());
			if(order.getNotice() != null && !"".equals(order.getNotice())) { pstmt2.setString(12, order.getNotice()); }
			pstmt2.executeUpdate();
			
			// 주문 상세 정보 저장
			sql = "INSERT INTO order_detail (detail_num, bk_num, book_title, book_price, book_total, book_author, book_publisher, thumbnail, order_quantity, order_num) VALUES (order_detail_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt3 = conn.prepareStatement(sql);
			
			for(int i = 0; i < detailList.size(); i++) {
				OrderDetailVO orderDetail = detailList.get(i);
				pstmt3.setInt(1, orderDetail.getBk_num());
				pstmt3.setString(2, orderDetail.getBook_title());
				pstmt3.setInt(3, orderDetail.getBook_price());
				pstmt3.setInt(4, orderDetail.getBook_total());
				pstmt3.setString(5, orderDetail.getBook_author());
				pstmt3.setString(6, orderDetail.getBook_publisher());
				pstmt3.setString(7, orderDetail.getThumbnail());
				pstmt3.setInt(8, orderDetail.getOrder_quantity());
				pstmt3.setInt(9, order_num);
				pstmt3.addBatch();
				
				if(i % 1000 == 0) { pstmt3.executeBatch(); }
			}
			
			pstmt3.executeBatch();
			
			// 상품의 재고수량 차감
			sql = "UPDATE book_list SET stock = stock - ? WHERE bk_num=?";
			pstmt4 = conn.prepareStatement(sql);
			
			for(int i = 0; i < detailList.size(); i++) {
				OrderDetailVO orderDetail = detailList.get(i);
				pstmt4.setInt(1, orderDetail.getOrder_quantity());
				pstmt4.setInt(2, orderDetail.getBk_num());
				pstmt4.addBatch();
				
				if(i % 1000 == 0) { pstmt4.executeBatch(); }
			}
			
			pstmt4.executeBatch();
			conn.commit();
		} catch(Exception e) {
			// 하나라도 SQL문이 실패 → 여러 개의 SQL문을 사용하기 때문에 필수!!
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	// 장바구니에서 구매내역 삭제
	public void deleteCartByNum(String[] cart_nums) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "DELETE FROM cart WHERE cart_num IN(" + String.join(",", cart_nums) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 개별 상품 목록
	public List<OrderDetailVO> getListOrderDetail(int order_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDetailVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM order_detail WHERE order_num=? ORDER BY bk_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderDetailVO>();
			while(rs.next()) {
				OrderDetailVO detail = new OrderDetailVO();
				detail.setBk_num(rs.getInt("bk_num"));
				detail.setBook_title(StringUtil.useNoHtml(rs.getString("book_title")));
				detail.setBook_price(rs.getInt("book_price"));
				detail.setBook_total(rs.getInt("book_total"));
				detail.setBook_author(StringUtil.useNoHtml(rs.getString("book_author")));
				detail.setBook_publisher(StringUtil.useNoHtml(rs.getString("book_publisher")));
				detail.setThumbnail(rs.getString("thumbnail"));
				detail.setOrder_quantity(rs.getInt("order_quantity"));
				detail.setOrder_num(rs.getInt("order_num"));
				
				list.add(detail);
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	// 주문 삭제(삭제시 재고 원상 복귀 X, 주문 취소일 경우 재고 원상 복귀 O)
	public void deleteOrder(int order_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "DELETE FROM order_detail WHERE order_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_num);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM orders WHERE order_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, order_num);
			pstmt2.executeUpdate();
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 관리자&사용자 : 주문 상세
	public OrderVO getOrder(int order_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO order = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM orders WHERE order_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new OrderVO();
				order.setOrder_num(rs.getInt("order_num"));
				order.setBook_title(StringUtil.useNoHtml(rs.getString("book_title")));
				order.setOrder_total(rs.getInt("order_total"));
				order.setPayment(rs.getInt("payment"));
				order.setStatus(rs.getInt("status"));
				order.setReceive_name(rs.getString("receive_name"));
				order.setReceive_post(rs.getString("receive_post"));
				order.setReceive_address1(StringUtil.useNoHtml(rs.getString("receive_address1")));
				order.setReceive_address2(StringUtil.useNoHtml(rs.getString("receive_address2")));
				order.setReceive_phone(rs.getString("receive_phone"));
				order.setEmail(rs.getString("email"));
				order.setNotice(StringUtil.useNoHtml(rs.getString("notice")));
				order.setOrder_date(rs.getDate("order_date"));
				order.setModify_date(rs.getDate("modify_date"));
				order.setMem_num(rs.getInt("mem_num"));
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return order;
	}
	
	// 관리자&사용자 : 주문 수정
	public void updateOrder(OrderVO order) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			OrderVO db_order = getOrder(order.getOrder_num());	// DB에 저장된 정보
			
			if(order.getStatus() == 1 && db_order.getStatus() == 1) { sub_sql += "receive_name=?, receive_post=?, receive_address1=?, receive_address2=?, receive_phone=?, email=?, notice=?, "; }
			
			sql = "UPDATE orders SET status=?, "+ sub_sql + "modify_date=SYSDATE WHERE order_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, order.getStatus());
			if(order.getStatus() == 1 && db_order.getStatus() == 1) {
				pstmt.setString(++cnt, order.getReceive_name());
				pstmt.setString(++cnt, order.getReceive_post());
				pstmt.setString(++cnt, order.getReceive_address1());
				pstmt.setString(++cnt, order.getReceive_address2());
				pstmt.setString(++cnt, order.getReceive_phone());
				pstmt.setString(++cnt, order.getEmail());
				pstmt.setString(++cnt, order.getNotice());
			}
			pstmt.setInt(++cnt, order.getOrder_num());	
			pstmt.executeUpdate();
			
			// 주문 취소일 경우만 상품개수 조정
			if(order.getStatus() == 5) {
				// 주문정보에 해당하는 상품정보 구하기
				List<OrderDetailVO> detailList = getListOrderDetail(order.getOrder_num());
				
				sql = "UPDATE book_list SET stock = stock + ? WHERE bk_num=?";
				pstmt2 = conn.prepareStatement(sql);
				for(int i = 0; i < detailList.size(); i++) {
					OrderDetailVO detail = detailList.get(i);
					pstmt2.setInt(1, detail.getOrder_quantity());
					pstmt2.setInt(2, detail.getBk_num());
					pstmt2.addBatch();
					
					// 계속 추가하면 outOfMemory 발생, 1000개 단위로 executeBatch()
					if(i % 1000 == 0) { pstmt2.executeBatch(); }
				}
				pstmt2.executeBatch();
			}
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	/* 관리자 */
	// 전체 주문&검색 주문 개수
	public int getOrderCount(String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) {
					if(keyword.equals("배송대기")) sub_sql += "WHERE status = 1";
					else if(keyword.equals("배송준비중")) sub_sql += "WHERE status = 2";
					else if(keyword.equals("배송중")) sub_sql += "WHERE status = 3";
					else if(keyword.equals("배송완료")) sub_sql += "WHERE status = 4";
					else if(keyword.equals("주문취소")) sub_sql += "WHERE status = 5";
				}else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE book_name LIKE ?";
			}
			
			sql = "SELECT COUNT(*) FROM orders o JOIN member m ON o.mem_num = m.mem_num " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				if(!keyfield.equals("1")) { pstmt.setString(1, "%" + keyword + "%"); }
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) { count = rs.getInt(1); }
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return count;
	}
	
	// 전체 주문&검색 주문 목록
	public List<OrderVO> getListOrder(int start, int end, String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) {
					if(keyword.equals("배송대기")) sub_sql += "WHERE status = 1";
					else if(keyword.equals("배송준비중")) sub_sql += "WHERE status = 2";
					else if(keyword.equals("배송중")) sub_sql += "WHERE status = 3";
					else if(keyword.equals("배송완료")) sub_sql += "WHERE status = 4";
					else if(keyword.equals("주문취소")) sub_sql += "WHERE status = 5";
				}else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE book_name LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM orders o JOIN member m ON o.mem_num = m.mem_num " + sub_sql + " ORDER BY order_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				if(!keyfield.equals("1")) { pstmt.setString(++cnt, "%" + keyword + "%"); }
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderVO>();
			while(rs.next()) {
				OrderVO order = new OrderVO();
				order.setOrder_num(rs.getInt("order_num"));
				order.setBook_title(StringUtil.useNoHtml(rs.getString("book_title")));
				order.setOrder_total(rs.getInt("order_total"));
				order.setPayment(rs.getInt("payment"));
				order.setStatus(rs.getInt("status"));
				order.setReceive_name(rs.getString("receive_name"));
				order.setReceive_post(rs.getString("receive_post"));
				order.setReceive_address1(rs.getString("receive_address1"));
				order.setReceive_address2(rs.getString("receive_address2"));
				order.setReceive_phone(rs.getString("receive_phone"));
				order.setEmail(rs.getString("email"));
				order.setNotice(StringUtil.useNoHtml(rs.getString("notice")));
				order.setOrder_date(rs.getDate("order_date"));
				order.setModify_date(rs.getDate("modify_date"));
				order.setMem_num(rs.getInt("mem_num"));
				order.setId(rs.getString("id"));
				
				list.add(order);
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	/* 사용자 */
	// 전체 주문&검색 주문 개수
	public int getOrderCountByMem_num(String keyfield, String keyword, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) {
					if(keyword.equals("배송대기")) sub_sql += "AND status = 1";
					else if(keyword.equals("배송준비중")) sub_sql += "AND status = 2";
					else if(keyword.equals("배송중")) sub_sql += "AND status = 3";
					else if(keyword.equals("배송완료")) sub_sql += "AND status = 4";
					else if(keyword.equals("주문취소")) sub_sql += "AND status = 5";
				} else if(keyfield.equals("2")) sub_sql += "AND book_title LIKE ?";
			}
			
			sql = "SELECT COUNT(*) FROM orders WHERE mem_num=? " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("2")) { pstmt.setString(2, "%" + keyword + "%"); }
				//else if(keyfield.equals("2")) { pstmt.setString(2, "%" + keyword + "%"); }
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) { count = rs.getInt(1); }
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return count;
	}
	
	// 사용자 : 전체 주문/검색 주문 목록
	public List<OrderVO> getListOrderByMem_num(int start, int end, String keyfield, String keyword, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) {
					if(keyword.equals("배송대기")) sub_sql += "AND status = 1";
					else if(keyword.equals("배송준비중")) sub_sql += "AND status = 2";
					else if(keyword.equals("배송중")) sub_sql += "AND status = 3";
					else if(keyword.equals("배송완료")) sub_sql += "AND status = 4";
					else if(keyword.equals("주문취소")) sub_sql += "AND status = 5";
				} else if(keyfield.equals("2")) sub_sql += "AND book_title LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM orders WHERE mem_num=? " + sub_sql + " ORDER BY order_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("2")) { pstmt.setString(++cnt, "%" + keyword + "%"); }
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderVO>();
			while(rs.next()) {
				OrderVO order = new OrderVO();
				order.setOrder_num(rs.getInt("order_num"));
				order.setBook_title(StringUtil.useNoHtml(rs.getString("book_title")));
				order.setOrder_total(rs.getInt("order_total"));
				order.setPayment(rs.getInt("payment"));
				order.setStatus(rs.getInt("status"));
				order.setReceive_name(rs.getString("receive_name"));
				order.setReceive_post(rs.getString("receive_post"));
				order.setReceive_address1(rs.getString("receive_address1"));
				order.setReceive_address2(rs.getString("receive_address2"));
				order.setReceive_phone(rs.getString("receive_phone"));
				order.setEmail(rs.getString("email"));
				order.setNotice(StringUtil.useNoHtml(rs.getString("notice")));
				order.setOrder_date(rs.getDate("order_date"));
				order.setModify_date(rs.getDate("modify_date"));
				order.setMem_num(rs.getInt("mem_num"));
				
				list.add(order);
			} 
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	// 주문 취소
	public void updateOrderCancel(int order_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql="UPDATE orders SET status = 5, modify_date = SYSDATE WHERE order_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_num);
			pstmt.executeUpdate();
			
			// 주문번호에 해당하는 상품정보 구하기
			List<OrderDetailVO> detailList = getListOrderDetail(order_num);
			// 주문취소로 주문상품의 재고수 환원
			sql = "UPDATE book_list SET stock = stock + ? WHERE bk_num=?";
			pstmt2 = conn.prepareStatement(sql);
			// 주문개수가 1개 이상일 가능성이 존재하기 떄문에 for문으로 반복
			for(int i = 0; i < detailList.size(); i++) {
				OrderDetailVO detail = detailList.get(i);
				pstmt2.setInt(1, detail.getOrder_quantity());
				pstmt2.setInt(2, detail.getBk_num());
				pstmt2.addBatch();
				
				// 계속 추가하면 outOfMemory 발생, 1000개 단위로 executeBatch()
				if(i % 1000 == 0) { pstmt2.executeBatch(); }
			}
			pstmt2.executeBatch();
			
			// 모든 SQL문이 성공하면
			conn.commit();
		} catch(Exception e) {
			// SQL문이 하나라도 실패하면
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
