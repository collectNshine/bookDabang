package kr.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.book.vo.BookVO;
import kr.util.DBUtil;

public class BookDAO {
		//싱글턴 패턴
		private static BookDAO instance = new BookDAO();
		public static BookDAO getInstance() {
			return instance;
		}
		private BookDAO() {}
		
		
		//도서 등록 (관리자)
		public void insertBook(BookVO book) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당받음
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO book_list (bk_num,title,author,publisher,price,stock,category,thumbnail,content) "
					+ "VALUES (book_list_seq.nextval,?,?,?,?,?,?,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, book.getTitle());
				pstmt.setString(2, book.getAuthor());
				pstmt.setString(3, book.getPublisher());
				pstmt.setInt(4, book.getPrice());
				pstmt.setInt(5, book.getStock());
				pstmt.setString(6, book.getCategory());
				pstmt.setString(7, book.getThumbnail());
				pstmt.setString(8, book.getContent());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally{
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//전체 도서 개수 & 검색 도서 개수
		public int getItemCount(String keyfield, String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당받음
				conn = DBUtil.getConnection();
				//sub_sql문 작성
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "WHERE title LIKE ?";
					if(keyfield.equals("2")) sub_sql += "WHERE author LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM book_list " + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(1, "%"+keyword+"%");
				}
				//SQL문 실행
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
		
		
		//전체 도서 목록 & 검색 도서 목록
		public List<BookVO> getBookList(int start, int end, String keyfield,
										String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당받음
				conn = DBUtil.getConnection();
				//sub_sql문 작성
				if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE ?";
				if(keyfield.equals("2")) sub_sql += "WHERE author LIKE ?";
				}
				//SQL문 작성	
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						  		   + "FROM (SELECT * FROM book_list "
						  		   			+sub_sql+" ORDER BY bk_num DESC)a) "
						  		   + "WHERE rnum>=? AND rnum<=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<BookVO>();
				while(rs.next()) {
				BookVO book = new BookVO();
				book.setBk_num(rs.getInt("bk_num"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				book.setPrice(rs.getInt("price"));
				book.setStock(rs.getInt("stock"));
				book.setCategory(rs.getString("category"));
				book.setThumbnail(rs.getString("thumbnail"));
				book.setContent(rs.getString("content"));
				book.setReg_date(rs.getDate("reg_date"));
				
				list.add(book);
			}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}

		
		//도서 상세
		public BookVO getBook(int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BookVO book = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당받음
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM book_list WHERE bk_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, bk_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					book = new BookVO();
					book.setBk_num(rs.getInt("bk_num"));
					book.setTitle(rs.getString("title"));
					book.setAuthor(rs.getString("author"));
					book.setPublisher(rs.getString("publisher"));
					book.setPrice(rs.getInt("price"));
					book.setStock(rs.getInt("stock"));
					book.setCategory(rs.getString("category"));
					book.setThumbnail(rs.getString("thumbnail"));
					book.setContent(rs.getString("content"));
					book.setReg_date(rs.getDate("reg_date"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return book;
		}
		
		
		//파일 정보 삭제
		public void deleteFile(int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE book_list SET thumbnail='' WHERE bk_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, bk_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//도서 수정 (관리자)
		//도서 삭제 (관리자)

}
