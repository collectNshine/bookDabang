package kr.request.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;
import kr.request.vo.RequestFavVO;
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
	public List<RequestVO> getListRequest(int start, int end, String keyfield,String keyword, int mem_num) throws Exception{
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
					+ "(SELECT * FROM book_request r JOIN member m USING(mem_num) LEFT OUTER JOIN (SELECT COUNT(*) cnt, req_num FROM book_request_fav group by req_num) f USING(req_num) LEFT OUTER JOIN (select 'clicked' clicked, req_num from book_request_fav WHERE mem_num=?) USING(req_num) " + sub_sql + " ORDER BY req_num DESC)a) "
							+ "WHERE rnum>=? AND rnum<=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, mem_num);
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
				request.setCnt(rs.getInt("cnt"));
				request.setClicked(rs.getString("clicked"));
				
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
				request.setId(rs.getString("id"));
				request.setReq_title(rs.getString("req_title"));
				request.setReq_author(rs.getString("Req_author"));
				request.setReq_publisher(rs.getString("req_publisher"));
				request.setReq_etc(rs.getString("req_etc"));
				request.setMem_num(rs.getInt("mem_num"));
				request.setReq_date(rs.getDate("req_date"));
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return request;
	}
	//글수정
	public void modifyRequest(RequestVO request) throws Exception{ 
		  
		  Connection conn = null; 
		  PreparedStatement pstmt = null; 
		  String sql = null;
	  
		  try {
			 conn = DBUtil.getConnection(); 
			 sql = "UPDATE book_request SET req_title=?,req_author=?,req_publisher=?,req_etc=?,req_modifydate=SYSDATE WHERE req_num=?";
			 
			 pstmt= conn.prepareStatement(sql); 
			 pstmt.setString(1,request.getReq_title());
			 pstmt.setString(2,request.getReq_author());
			 pstmt.setString(3,request.getReq_publisher()); 
			 pstmt.setString(4,request.getReq_etc()); 
			 pstmt.setInt(5,request.getReq_num());
			  
			 pstmt.executeUpdate();
	  
	  
		  }catch(Exception e) { 
			  throw new Exception(e); 
		  }finally {
			  DBUtil.executeClose(null, pstmt, conn); 
		  }
	  
	}
	 
	
	
	//글삭제
	
	  public void deleteRequest(int req_num) throws Exception{ 
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  String sql = null;
		  
		  try {
			  conn = DBUtil.getConnection();	  
			  //글 삭제
			  sql = "DELETE FROM book_request WHERE req_num=?";
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1, req_num);
			  
			  pstmt.executeUpdate();
					  
			 //좋아요 삭제		  
		  }catch(Exception e) {
			  throw new Exception(e);
		  }finally {
			  DBUtil.executeClose(null, pstmt, conn);
		  }
	
	  }
	  /*
	  //추천수
	  public void getFavRequest(RequestFavVO fav) throws Exception{
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  String sql = null;
		  
		  try {
			  conn = DBUtil.getConnection();
			  sql= "SELECT clicked,cnt FROM "
			  		+ "(SELECT * FROM book_request r JOIN member m USING(mem_num) JOIN "
			  		+ "(SELECT COUNT(*) cnt, req_num FROM book_request_fav group by req_num) f USING(req_num) JOIN "
			  		+ "(SELECT 'clicked' clicked, req_num from book_request_fav WHERE mem_num=?) c USING(req_num) "
			  		+ "ORDER BY req_num DESC) a WHERE req_num=? ";
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1,fav.getMem_num());
			  pstmt.setInt(2,fav.getReq_num());
			  pstmt.executeUpdate();
			  
		  }catch(Exception e) {
			  throw new Exception(e);
		  }finally {
			  DBUtil.executeClose(null, pstmt, conn);
		  }
		  
		  
	  }
	  */
		
	  public List<RequestVO> getFavRequest(int mem_num, int req_num) throws Exception{
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  List<RequestVO> list = null;
		  String sql = null;
		  
		  try {
			  conn = DBUtil.getConnection();
				/*
				 * sql =
				 * "SELECT cnt, clicked FROM (SELECT * FROM book_request r JOIN member m USING(mem_num) LEFT OUTER JOIN (SELECT COUNT(*) cnt, req_num FROM book_request_fav group by req_num) f USING(req_num) LEFT OUTER JOIN (select 'clicked' clicked, req_num from book_request_fav WHERE mem_num=?) USING(req_num) ORDER BY req_num DESC) WHERE mem_num=? AND req_num=?"
				 * ;
				 */
			  sql= "SELECT clicked,cnt FROM "
			  		+ "(SELECT * FROM book_request r JOIN member m USING(mem_num) JOIN "
			  		+ "(SELECT COUNT(*) cnt, req_num FROM book_request_fav group by req_num) f USING(req_num) JOIN "
			  		+ "(SELECT 'clicked' clicked, req_num from book_request_fav WHERE mem_num=?) c USING(req_num) "
			  		+ "ORDER BY req_num DESC) a WHERE req_num=? ";
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1,mem_num);
			  pstmt.setInt(2,req_num);
			  list = new ArrayList<RequestVO>();
			  rs = pstmt.executeQuery();
			  while(rs.next()) {
				  RequestVO req = new RequestVO();
				  req.setMem_num(mem_num);
				  req.setReq_num(req_num);
				  req.setClicked(rs.getString("clicked"));
				  req.setCnt(rs.getInt("cnt"));
				  
				  list.add(req);
			  }
			  
		  }catch(Exception e) {
			  throw new Exception(e);
		  }finally {
			  DBUtil.executeClose(null, pstmt, conn);
		  }
		  return list;
		  
	  }	  
	  
	  
	  //추천수 등록
	  public void giveFav(RequestFavVO fav) throws Exception{
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  String sql = null;
		  
		  try {
			  conn = DBUtil.getConnection();
			  sql = "INSERT INTO book_request_fav (req_fav_num, req_num, mem_num) VALUES (book_request_fav_seq.nextval,?,?)";
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1,fav.getReq_num());
			  pstmt.setInt(2, fav.getMem_num());
			  pstmt.executeUpdate();
		  }catch(Exception e) {
			  throw new Exception(e);
		  }finally {
			  DBUtil.executeClose(null, pstmt, conn);
		  }
	  }
  
	  //추천수 개수
	  public int howmanyFav(int req_num) throws Exception{
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  String sql = null;
		  int count = 0;
		  
		  try {
			  conn = DBUtil.getConnection();
			  sql = "SELECT COUNT(*) FROM book_request_fav WHERE req_num=?";
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1, req_num);
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
	  
	  //회원번호와 게시물 번호를 이용한 좋아요 정보 (회원이 게시물을 호출할 때 좋아요 선택 여부 표시)
	  public RequestFavVO doyouFavme(RequestFavVO fav) throws Exception{
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  RequestFavVO fav_me = null;
		  String sql = null;
		  
		  try {
			  conn = DBUtil.getConnection();
			  sql = "SELECT * FROM book_request_fav WHERE req_num=? AND mem_num=?";
			  pstmt = conn.prepareStatement(sql);
			  pstmt.setInt(1, fav.getReq_num());
			  pstmt.setInt(2, fav.getMem_num());
			  rs = pstmt.executeQuery();
			  if(rs.next()) {
				  fav_me = new RequestFavVO();
				  fav_me.setReq_fav_num(rs.getInt("req_fav_num"));
				  fav_me.setReq_num(rs.getInt("req_num"));
				  fav_me.setMem_num(rs.getInt("mem_num"));
			  }
		  }catch(Exception e) {
			  throw new Exception(e);
		  }finally {
			  DBUtil.executeClose(rs, pstmt, conn);
		  }
		  
		  return fav_me;
	  }
	  //추천수 삭제
	 public void ByeFav(int req_fav_num) throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 
		 try {
			 conn = DBUtil.getConnection();
			 sql = "DELETE FROM book_request_fav WHERE req_fav_num=?";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, req_fav_num);
			 pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 DBUtil.executeClose(null, pstmt, conn);
		 }
		 
	 }
}
