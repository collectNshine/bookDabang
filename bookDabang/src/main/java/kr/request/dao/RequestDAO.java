package kr.request.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;
import kr.request.vo.RequestVO;

public class RequestDAO {
	private static RequestDAO instance = new RequestDAO();
	public static RequestDAO getInstance() {
		return instance;
	}
	private RequestDAO() {}
	
	
	//글 등록
	public void insertrequest(RequestVO request) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO book_request(req_num,req_title,req_author,req_publisher,req_etc,req_ip,req_date,mem_num) "
					+ "VALUES (book_request_seq.nextval,?,?,?,?,?,SYSDATE,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, request.getReq_title());
			pstmt.setString(2, request.getReq_author());
			pstmt.setString(3, request.getReq_publisher());
			pstmt.setString(4, request.getReq_etc());
			pstmt.setString(5, request.getReq_ip());
			pstmt.setInt(6, request.getMem_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//총 레코드수(검색 레코드 수)
	public int getRequestCount(String keyfield, String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1"))sub_sql += "WHERE r.req_title LIKE '%' || ? || '%'";
				if(keyfield.equals("2"))sub_sql += "WHERE r.req_author LIKE '%' || ? || '%'";
				if(keyfield.equals("3"))sub_sql += "WHERE r.req_publisher LIKE '%' || ? || '%'";
			}
			sql = "SELECT COUNT(*) FROM book_request r JOIN member m USING(mem_num) " + sub_sql ; 
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	//글 목록
	public List<RequestVO> getListRequest(int start, int end, String keyfield,String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RequestVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1"))sub_sql += "WHERE r.req_title LIKE '%' || ? || '%'";
				if(keyfield.equals("2"))sub_sql += "WHERE r.req_author LIKE '%' || ? || '%'";
				if(keyfield.equals("3"))sub_sql += "WHERE r.req_publisher LIKE '%' || ? || '%'";
			}
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM book_request r JOIN member m USING(mem_num) " + sub_sql + " ORDER BY r.req_num DESC)a) "
							+ "WHERE rnum>=? AND rnum<=?";
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt,keyword);
			}
			pstmt.setInt(++cnt,start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<RequestVO>();
			while(rs.next()) {
				RequestVO request = new RequestVO();
				request.setReq_num(rs.getInt("req_num"));
				request.setReq_title(rs.getString("req_title"));
				request.setReq_author(rs.getString("req_author"));
				request.setReq_publisher(rs.getString("req_publisher"));
				request.setReq_etc(rs.getString("req_etc"));
				request.setReq_date(rs.getDate("req_date"));
				request.setReq_ip(rs.getString("req_ip"));
				//추천수도 추가해야해
				
				list.add(request);
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//글상세
	public RequestVO getRequest(int req_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RequestVO request = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM book_request r JOIN member m USING(mem_num) WHERE r.req_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, req_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				request = new RequestVO();
				request.setReq_num(rs.getInt("req_num"));
				request.setReq_title(rs.getString("req_title"));
				request.setReq_author(rs.getString("Req_author"));
				request.setReq_publisher(rs.getString("req_publisher"));
				request.setReq_etc(rs.getString("req_etc"));
				request.setId(rs.getString("id"));
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return request;
	}
	//글수정
	/*
	 * public void updateRequest(RequestVO request) throws Exception{ Connection
	 * conn = null; PreparedStatement pstmt = null; String sql = null;
	 * 
	 * try { conn = DBUtil.getConnection(); sql =
	 * "UPDATE (SELECT * FROM book_request r LEFT OUTER JOIN member USING(mem_num)) "
	 * +
	 * "SET req_title=?,req_author=?,req_publisher=?,req_etc=?,req_modifydate=SYSDATE WHERE req_num=?"
	 * ;
	 * 
	 * pstmt= conn.prepareStatement(sql); pstmt.setString(1,request.getReq_title());
	 * pstmt.setString(2, request.getReq_author());
	 * pstmt.setString(3,request.getReq_publisher()); pstmt.setString(4,
	 * request.getReq_etc()); pstmt.setInt(5,request.getReq_num());
	 * 
	 * pstmt.executeUpdate();
	 * 
	 * 
	 * }catch(Exception e) { throw new Exception(e); }finally {
	 * DBUtil.executeClose(null, pstmt, conn); }
	 * 
	 * }
	 */
	
	
	//글삭제
	/*
	 * public void deleteRequest(RequestVO request) throws Exception{ Connection
	 * conn = null; PreparedStatement pstmt = null;
	 * 
	 * 
	 * }
	 */
	
}
