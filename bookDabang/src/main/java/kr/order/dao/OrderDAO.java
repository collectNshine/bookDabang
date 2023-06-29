package kr.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;
import kr.util.DBUtil;

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
		PreparedStatement pstmt5 = null;
		ResultSet rs = null;
		String sql = null;
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
			sql = "INSERT INTO orders (order_num, book_title, order_total, payment, receive_name, receive_post, receive_address1, receive_address2, receive_phone, notice, mem_num) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			pstmt2.setString(10, order.getNotice());
			pstmt2.setInt(11, order.getMem_num());
			pstmt2.executeUpdate();
			
			// 주문 상세 정보 저장
			sql = "INSERT INTO zorder_detail (detail_num, bk_num, book_title, book_price, book_total, order_quantity, order_num) VALUES (zorder_detail_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt3 = conn.prepareStatement(sql);
			
			for(int i = 0; i < detailList.size(); i++) {
				OrderDetailVO orderDetail = detailList.get(i);
				pstmt3.setInt(1, orderDetail.getBk_num());
				pstmt3.setString(2, orderDetail.getBook_title());
				pstmt3.setInt(3, orderDetail.getBook_price());
				pstmt3.setInt(4, orderDetail.getBook_total());
				pstmt3.setInt(5, orderDetail.getOrder_quantity());
				pstmt3.setInt(6, order_num);
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
			
			// 장바구니에서 주문 상품 삭제
			sql = "DELETE FROM cart WHERE mem_num=?";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setInt(1, order.getMem_num());
			pstmt5.executeUpdate();
			
			conn.commit();
		} catch(Exception e) {
			// 하나라도 SQL문이 실패 → 여러 개의 SQL문을 사용하기 때문에 필수!!
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
}
