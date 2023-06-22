package kr.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.book.vo.BookMarkVO;
import kr.book.vo.BookVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BookDAO {
		//싱글턴 패턴 
		private static BookDAO instance = new BookDAO();
		public static BookDAO getInstance() {
			return instance;
		}
		private BookDAO() {}
		
		
		
		/*--------------------도서 관리--------------------*/
		
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
				pstmt.setString(1, StringUtil.useNoHtml(book.getTitle()));
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
					book.setContent(StringUtil.useBrNoHtml(rs.getString("content")));
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
		public void updateBook(BookVO book) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//조건 체크 - 파일을 업로드한 경우
				if(book.getThumbnail() != null) {
					sub_sql += ",thumbnail=?";
				}
				//SQL문 작성
				sql = "UPDATE book_list SET title=?,author=?,publisher=?,price=?,stock=?,category=?" 
						+ sub_sql + ",content=? WHERE bk_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(++cnt, book.getTitle());
				pstmt.setString(++cnt, book.getAuthor());
				pstmt.setString(++cnt, book.getPublisher());
				pstmt.setInt(++cnt, book.getPrice());
				pstmt.setInt(++cnt, book.getStock());
				pstmt.setString(++cnt, book.getCategory());
				if(book.getThumbnail() != null) {
					pstmt.setString(++cnt, book.getThumbnail());
				}
				pstmt.setString(++cnt, book.getContent());
				pstmt.setInt(++cnt, book.getBk_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

		
		//도서 삭제 (관리자)
		public void deleteBook(int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				//conn.setAutoCommit(false);
				
				//포스트 삭제 메서드(포스트,포스트좋아요,포스트신고,포스트댓글,포스트댓글신고)
				
				//리뷰 삭제 메서드(리뷰,리뷰좋아요,리뷰싫어요)
				
				//부모 테이블 삭제
				sql = "DELETE FROM book_list WHERE bk_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bk_num);
				pstmt.executeUpdate();
				
				//conn.commit();
			}catch(Exception e) {
				//conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		
		
		
		/*--------------------책갈피--------------------*/
		
		//책갈피 등록
		public void insertMark(BookMarkVO markVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO book_mark (mark_num,bk_num,mem_num) "
					+ "VALUES (book_mark_seq.nextval,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, markVO.getBk_num());
				pstmt.setInt(2, markVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//책갈피 해제
		public void deleteMark(int mark_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM book_mark WHERE mark_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, mark_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//책갈피 여부 확인 (내가 이 책을 책갈피 등록해놨는지 확인 용)
		public BookMarkVO selectMark(BookMarkVO markVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BookMarkVO mark = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM book_mark WHERE bk_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, markVO.getBk_num());
				pstmt.setInt(2, markVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					mark = new BookMarkVO();
					mark.setMark_num(rs.getInt("mark_num"));
					mark.setBk_num(rs.getInt("bk_num"));
					mark.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return mark;
		}
		
		
		//도서 별 책갈피 저장 개수 (몇 명의 회원이 이 책을 책갈피에 담았는지 확인 용)
		public int selectMarkCount(int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM book_mark WHERE bk_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, bk_num);
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
		

		//회원 별 책갈피 목록
		public List<BookVO> getMarkList(int start, int end, int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookVO> list = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * "
					+ "FROM (SELECT a.*, rownum rnum "
							+ "FROM (SELECT * "
									+ "FROM member m JOIN book_mark bm USING(mem_num) JOIN book_list bl USING(bk_num) "
									+ "WHERE m.mem_num=? ORDER BY mark_num DESC)a) "
							+ "WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, mem_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<BookVO>();
				while(rs.next()) {
					BookVO book = new BookVO();
					book.setBk_num(rs.getInt("bk_num"));
					book.setTitle(StringUtil.useNoHtml(rs.getString("title")));
					book.setAuthor(rs.getString("author"));
					list.add(book);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
}
