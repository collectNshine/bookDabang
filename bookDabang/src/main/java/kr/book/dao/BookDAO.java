package kr.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.book.vo.BookMarkVO;
import kr.book.vo.BookVO;
import kr.book.vo.ReviewDislikeVO;
import kr.book.vo.ReviewLikeVO;
import kr.book.vo.ReviewVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
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
				pstmt.setString(1,book.getTitle());
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
		public int getItemCount(String keyfield, String keyword, String category) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당받음
				conn = DBUtil.getConnection();
				//sub_sql문 작성
				
				if(category!=null && !"".equals(category)) {
					sub_sql += "WHERE category = ?";
				}else {
					sub_sql += "WHERE category IS NOT NULL";
				}
				
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += " AND title LIKE ?";
					if(keyfield.equals("2")) sub_sql += " AND author LIKE ?";
				}
				
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM book_list " + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(category!=null && !"".equals(category)) {
					pstmt.setString(++cnt, category);
				}
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
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
										String keyword, String category) throws Exception{
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
				if(category!=null && !"".equals(category)) {
					sub_sql += "WHERE category = ?";
				}else {
					sub_sql += "WHERE category IS NOT NULL";
				}
				
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += " AND title LIKE ?";
					if(keyfield.equals("2")) sub_sql += " AND author LIKE ?";
				}
				//SQL문 작성	
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						  		   + "FROM (SELECT * FROM book_list "
						  		   			+sub_sql+" ORDER BY reg_date DESC)a) "
						  		   + "WHERE rnum>=? AND rnum<=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(category!=null && !"".equals(category)) {
					pstmt.setString(++cnt, category);
				}
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
				book.setContent(StringUtil.useNoHtml(rs.getString("content")));
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
				pstmt.setString(++cnt,book.getContent());
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
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				//장바구니에 담겨있는 상품 삭제
				sql = "DELETE FROM cart WHERE bk_num=?";
				pstmt1 = conn.prepareStatement(sql);
				pstmt1.setInt(1, bk_num);
				pstmt1.executeUpdate();
				
				//북마크 삭제
				sql = "DELETE FROM book_mark WHERE bk_num=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, bk_num);
				pstmt2.executeUpdate();

				//포스트 삭제 메서드(포스트,포스트좋아요,포스트신고,포스트댓글)
				
				
				//댓글 삭제 메서드(댓글,댓글좋아요,댓글싫어요)
				
				
				//부모 테이블 삭제
				sql = "DELETE FROM book_list WHERE bk_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, bk_num);
				pstmt3.executeUpdate();
				
				conn.commit();
			}catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt1, conn);
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
		
		
		//도서 별 책갈피 저장 개수 (몇 명의 회원이 이 책을 책갈피에 담았는지 확인)
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
		public List<BookMarkVO> getMarkList(int start, int end, String keyfield, String keyword, int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BookMarkVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//sub_sql문 작성
				if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND b.title LIKE ? ";
				if(keyfield.equals("2")) sub_sql += "AND b.author LIKE ? ";
				}
				//SQL문 작성
				sql = "SELECT * "
					+ "FROM (SELECT a.*, rownum rnum "
						  + "FROM (SELECT * "
						  		+ "FROM member m JOIN book_mark bm USING(mem_num) JOIN book_list b USING(bk_num) "
						  		+ "WHERE mem_num = ? "+sub_sql+"ORDER BY bm.mark_num DESC)a) "
						  + "WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(++cnt, mem_num);
				if (keyword != null && !"".equals(keyword)) {
				    pstmt.setString(++cnt, "%" + keyword + "%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<BookMarkVO>();
				while(rs.next()) {
					BookMarkVO mark = new BookMarkVO();
					mark.setMark_num(rs.getInt("mark_num"));
					mark.setBk_num(rs.getInt("bk_num"));
					mark.setMem_num(rs.getInt("mem_num"));
					mark.setTitle(rs.getString("title"));
					mark.setAuthor(rs.getString("author"));
					mark.setPublisher(rs.getString("publisher"));
					mark.setThumbnail(rs.getString("thumbnail"));
					list.add(mark);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		
		
		
		//회원 별 책갈피 저장 개수 (몇 명의 회원이 이 책을 책갈피에 담았는지 확인 용)
		public int selectUserMarkCount(String keyfield, String keyword, int mem_num) throws Exception{
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
					if(keyfield.equals("1")) sub_sql += " AND bl.title LIKE ?";
					if(keyfield.equals("2")) sub_sql += " AND bl.author LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT COUNT(*) "
					+ "FROM book_mark bm JOIN book_list bl USING(bk_num) "
					+ "WHERE bm.mem_num=?" + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, mem_num);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(2, "%"+keyword+"%");
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
		
		
		
		
		
		/*--------------------한 줄 리뷰--------------------*/
		
		//댓글 등록
		public void insertReview(ReviewVO review) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO review (review_num,review_content,review_ip,mem_num,bk_num) "
					+ "VALUES (review_seq.nextval,?,?,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, StringUtil.useNoHtml(review.getReview_content()));
				pstmt.setString(2, review.getReview_ip());
				pstmt.setInt(3, review.getMem_num());
				pstmt.setInt(4, review.getBk_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//댓글 개수
		public int getReviewCount(int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM review r JOIN member m ON r.mem_num = m.mem_num WHERE r.bk_num = ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, bk_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1); //컬럼인덱스로 불러옴
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return count;
		}
		
		
		//댓글 목록
		public List<ReviewVO> getListReview(int start, int end, int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<ReviewVO> list = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * "
					+ "FROM (SELECT a.*, rownum rnum "
							+ "FROM (SELECT * "
									+ "FROM review r JOIN member_detail m USING(mem_num) "
									+ "WHERE r.bk_num=? ORDER BY r.review_num DESC)a) "
							+ "WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, bk_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<ReviewVO>();
				while(rs.next()) {
					ReviewVO review = new ReviewVO();
					review.setReview_num(rs.getInt("review_num"));
					review.setReview_date(rs.getString("review_date"));
					if(rs.getString("review_modifydate") != null) {
						review.setReview_modifydate(rs.getString("review_modifyDate"));
					}
					review.setReview_content(StringUtil.useBrNoHtml(rs.getString("review_content")));
					review.setBk_num(rs.getInt("bk_num"));
					review.setMem_num(rs.getInt("mem_num"));
					review.setName(rs.getString("name"));
					
					list.add(review);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		
		
		//댓글 목록 - 최고에요,별로에요 포함
		public List<ReviewVO> getListLike(int start, int end, int mem_num, int bk_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<ReviewVO> list = null;
			String sql = null;
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * "
					+ "FROM (SELECT a.*, rownum rnum "
						  + "FROM (SELECT * FROM review r JOIN member_detail m USING(mem_num) "
						  					+ "LEFT OUTER JOIN (SELECT COUNT(*) cnt_like, review_num FROM review_like GROUP BY review_num) l USING(review_num) "
						  					+ "LEFT OUTER JOIN (SELECT 'clicked' clicked_like, review_num FROM review_like WHERE mem_num=?) c USING(review_num) "
						  					+ "LEFT OUTER JOIN (SELECT COUNT(*) cnt_dislike, review_num FROM review_dislike GROUP BY review_num) l USING(review_num) "
						  					+ "LEFT OUTER JOIN (SELECT 'clicked' clicked_dislike, review_num FROM review_dislike WHERE mem_num=?) c USING(review_num) "
						  		+ "ORDER BY review_num DESC)a) "
					+ "WHERE bk_num=? AND rnum>=? AND rnum<=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				pstmt.setInt(2, mem_num);
				pstmt.setInt(3, bk_num);
				pstmt.setInt(4, start);
				pstmt.setInt(5, end);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<ReviewVO>();
				while(rs.next()) {
					ReviewVO review = new ReviewVO();
					review.setMem_num(rs.getInt("mem_num"));
					review.setName(rs.getString("name"));
					review.setPhoto(rs.getString("photo"));
					
					review.setReview_num(rs.getInt("review_num"));
					review.setReview_content(rs.getString("review_content"));
					review.setReview_ip(rs.getString("review_ip"));
					review.setReview_date(rs.getString("review_date"));
					review.setReview_modifydate(rs.getString("review_modifydate"));

					review.setBk_num(rs.getInt("bk_num"));
					review.setCnt_like(rs.getInt("cnt_like"));
					review.setClicked_like(rs.getString("clicked_like"));
					review.setCnt_dislike(rs.getInt("cnt_dislike"));
					review.setClicked_dislike(rs.getString("clicked_dislike"));
					
					list.add(review);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		
		
		
		
		//댓글 삭제
		public void deleteReview(int review_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				//댓글 좋아요 삭제
				sql = "DELETE FROM review_like WHERE review_num=?";
				pstmt1 = conn.prepareStatement(sql);
				pstmt1.setInt(1, review_num);
				pstmt1.executeUpdate();
				
				//댓글 별로에요 삭제
				sql = "DELETE FROM review_dislike WHERE review_num=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, review_num);
				pstmt2.executeUpdate();
				
				//부모 테이블(댓글) 삭제
				sql = "DELETE FROM review WHERE review_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, review_num);
				pstmt3.executeUpdate();
				conn.commit();
			}catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt1, conn);
			}
		}
		
		
		//댓글 상세 (댓글 수정/삭제 시 작성자 회원번호 체크 용도)
		public ReviewVO getReviewDetail(int review_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ReviewVO review = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM review WHERE review_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, review_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					review = new ReviewVO();
					review.setReview_num(rs.getInt("review_num"));
					review.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return review;
		}
		
		
		//댓글 수정
		public void updateReview(ReviewVO review) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE review SET review_content=?,review_modifydate=SYSDATE,review_ip=? WHERE review_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, review.getReview_content());
				pstmt.setString(2, review.getReview_ip());
				pstmt.setInt(3, review.getReview_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		
		
		/*--------------------댓글 최고에요&별로에요--------------------*/
		
		//최고에요 등록
		public void insertReviewLike(ReviewLikeVO likeVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO review_like (rev_like_num,review_num,mem_num) "
					+ "VALUES (review_like_seq.nextval,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, likeVO.getReview_num());
				pstmt.setInt(2, likeVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//별로에요 등록
		public void insertReviewDislike(ReviewDislikeVO dislikeVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO review_dislike (rev_dislike_num,review_num,mem_num) "
						+ "VALUES (review_dislike_seq.nextval,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, dislikeVO.getReview_num());
				pstmt.setInt(2, dislikeVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//최고에요 해제
		public void deleteReviewLike(int rev_like_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM review_like WHERE rev_like_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, rev_like_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//별로에요 해제
		public void deleteReviewDislike(int rev_dislike_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM review_dislike WHERE rev_dislike_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, rev_dislike_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//최고에요 여부 확인 (내가 이 리뷰에 최고에요를 눌렀었는지 확인 용)
		public ReviewLikeVO selectReviewLike(ReviewLikeVO likeVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ReviewLikeVO like = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM review_like WHERE review_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, likeVO.getReview_num());
				pstmt.setInt(2, likeVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					like = new ReviewLikeVO();
					like.setReview_num(rs.getInt("review_num"));
					like.setMem_num(rs.getInt("mem_num"));
					like.setRev_like_num(rs.getInt("rev_like_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return like;
		}
		
		
		//별로에요 여부 확인 (내가 이 리뷰에 최고에요를 눌렀었는지 확인 용)
		public ReviewDislikeVO selectReviewDislike(ReviewDislikeVO dislikeVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ReviewDislikeVO dislike = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM review_dislike WHERE review_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, dislikeVO.getReview_num());
				pstmt.setInt(2, dislikeVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					dislike = new ReviewDislikeVO();
					dislike.setReview_num(rs.getInt("review_num"));
					dislike.setMem_num(rs.getInt("mem_num"));
					dislike.setRev_dislike_num(rs.getInt("rev_dislike_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return dislike;
		}
		
		
		//댓글 별 최고에요 개수
		public int selectReviewLikeCount(int review_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM review_like WHERE review_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, review_num);
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
		
		
		//댓글 별 별로에요 개수
		public int selectReviewDislikeCount(int review_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM review_dislike WHERE review_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, review_num);
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
		
		
		
		/*--------------------한 줄 리뷰 피드--------------------*/
		
		//총 댓글 수 (총 레코드 수)
		public int getReCount(String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM review r JOIN member m USING(mem_num)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(1, "%" + keyword + "%");
				}
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1); //컬럼인덱스로 간단히 불러옴
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}

			return count;
		}
		
		
		//한줄기록 피드 목록
		public List<ReviewVO> getReList(int start, int end, String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<ReviewVO> list = null;
			String sql = null;
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM review r JOIN member m USING(mem_num) LEFT OUTER JOIN member_detail d USING(mem_num) LEFT OUTER JOIN book_list b USING(bk_num) ORDER BY r.review_num DESC)a) WHERE rnum>=? AND rnum<=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%" + keyword + "%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<ReviewVO>();
				while(rs.next()) {
					ReviewVO review = new ReviewVO();
					review.setReview_num(rs.getInt("review_num"));
					review.setReview_content(StringUtil.useBrNoHtml(rs.getString("review_content")));
					//날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
					review.setReview_date(DurationFromNow.getTimeDiffLabel(rs.getString("review_date")));
					if(rs.getString("review_modifydate") != null) {
						review.setReview_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("review_modifyDate")));
					}
					review.setBk_num(rs.getInt("bk_num"));
					review.setMem_num(rs.getInt("mem_num"));
					review.setName(rs.getString("name"));
					review.setPhoto(rs.getString("photo"));
					review.setTitle(rs.getString("title"));
					review.setAuthor(rs.getString("author"));
					review.setThumbnail(rs.getString("thumbnail"));
					
					//자바빈을 ArrayList에 저장
					list.add(review);
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
					
			return list;
		}	
		
}
