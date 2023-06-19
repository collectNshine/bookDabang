package kr.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
		//전체 도서 목록 & 검색 도서 목록
		//도서 상세 (관리자)
		//도서 수정 (관리자)
		//도서 삭제 (관리자)

}
