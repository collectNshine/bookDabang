package kr.post.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.post.dao.PostDAO;
import kr.post.vo.PostVO;
import kr.util.DBUtil;

	public class PostDAO {
		//싱글턴 패턴
		private static PostDAO instance = new PostDAO();
		public static PostDAO getInstance() {
			return instance;
		}
		private PostDAO() {}
		
		//서평 등록
		public void insertPost(PostVO post) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당받음
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO post (post_num, post_title, post_content, post_photo, post_ip, mem_num, bk_num) VALUES (post_seq.nextval,?,?,?,?,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, post.getPost_title());
				pstmt.setString(2, post.getPost_content());
				pstmt.setString(3, post.getPost_photo());
				pstmt.setString(4, post.getPost_ip());
				pstmt.setInt(5, post.getMem_num());
				pstmt.setInt(6, post.getBk_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally{
				DBUtil.executeClose(null, pstmt, conn);
			}
			
		}
		
		//서평 목록
		public List<PostVO> getPostList(int start, int end, String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<PostVO> list = null;
			String sql = null;
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM post p JOIN member m USING(mem_num) ORDER BY p.post_num DESC)a) WHERE rnum>=? AND rnum<=?";
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
				list = new ArrayList<PostVO>();
				while(rs.next()) {
					PostVO post = new PostVO();
					post.setPost_num(rs.getInt("post_num"));
					post.setPost_title(rs.getString("post_title"));
					post.setPost_content(rs.getString("post_content"));
					post.setPost_date(rs.getDate("post_date"));
					post.setPost_modifydate(rs.getDate("post_modifydate"));
					post.setPost_photo(rs.getString("post_photo"));
					post.setPost_ip(rs.getString("post_ip"));
					post.setMem_num(rs.getInt("mem_num"));
					post.setBk_num(rs.getInt("bk_num"));
					//자바빈을 ArrayList에 저장
					list.add(post);
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		//총 레코드 수
		public int getPostCount(String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM post p JOIN member m USING(mem_num)";
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
		
		//서평 상세
		public PostVO getPost(int post_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			PostVO post = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성 (탈퇴시에도 누락되지 않도록 LEFT OUTER JOIN함)
				sql = "SELECT * FROM post p JOIN member m USING(mem_num) LEFT OUTER JOIN member_detail d USING(mem_num) WHERE p.post_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, post_num);
				//SQL문을 실행해서 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				if(rs.next()) {
					post = new PostVO();
					post.setPost_num(rs.getInt("post_num"));
					post.setPost_title(rs.getString("post_title"));
					post.setPost_content(rs.getString("post_content"));
					post.setPost_date(rs.getDate("post_date"));
					post.setPost_modifydate(rs.getDate("post_modifydate"));
					post.setPost_photo(rs.getString("post_photo"));
					post.setMem_num(rs.getInt("mem_num"));
					post.setBk_num(rs.getInt("bk_num"));
					post.setName(rs.getString("name"));
					post.setPhoto(rs.getString("photo"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return post;
		}
		
		//서평 수정
		public void updatePost(PostVO post) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//조건 체크 - 파일을 업로드한 경우
				if(post.getPost_photo() != null) {
					sub_sql += ",post_photo=?";
				}
				//SQL문 작성
				sql = "UPDATE post SET post_title=?,post_content=?,post_modifydate=SYSDATE" + sub_sql + ",post_ip=? WHERE post_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(++cnt, post.getPost_title());
				pstmt.setString(++cnt, post.getPost_content());
				if(post.getPost_photo() != null) {
					pstmt.setString(++cnt, post.getPost_photo());
				}
				pstmt.setString(++cnt, post.getPost_ip());
				pstmt.setInt(++cnt, post.getPost_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//서평 삭제
		public void deleteBoard(int post_num) throws Exception{
			Connection conn = null;
			//PreparedStatement pstmt = null;
			//PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//auto commit 해제
				conn.setAutoCommit(false);
				/*
				//좋아요 삭제
				sql = "DELETE FROM post_fav WHERE post_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, post_num);
				pstmt.executeUpdate();
				
				//댓글 삭제
				sql = "DELETE FROM post_reply WHERE post_num=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, post_num);
				pstmt2.executeUpdate();
				*/
				//부모글 삭제
				sql = "DELETE FROM post WHERE post_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, post_num);
				pstmt3.executeUpdate();
				
				//예외 발생 없이 정상적으로 SQL문 실행 시 커밋
				conn.commit();
			}catch(Exception e) {
				//SQL문이 하나라도 실패할 시 롤백
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt3, null);
				//DBUtil.executeClose(null, pstmt2, null);
				//DBUtil.executeClose(null, pstmt, conn);
			}
		}
/*
		//좋아요 등록
		public void insertFav(PostFavVO favVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO post_fav (post_fav_num,post_num,mem_num) VALUES (post_fav_seq.nextval,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, favVO.getPost_num());
				pstmt.setInt(2, favVO.getMem_num());
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//좋아요 수
		public int selectFavCount(int post_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM post_fav WHERE post_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, post_num);
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
		
		//회원번호와 게시물번호를 이용한 좋아요 정보
		//(회원이 게시물을 호출할 때 좋아요 선택 여부 표시)
		public PostFavVO selectFav(PostFavVO favVO) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			PostFavVO fav = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM post_fav WHERE post_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, favVO.getPost_num());
				pstmt.setInt(2, favVO.getMem_num());
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					fav = new PostFavVO();
					fav.setPost_fav_num(0);
					fav.setPost_num(rs.getInt("post_num"));
					fav.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return fav;
		}
		
		//좋아요 삭제
		public void deleteFav(int post_fav_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM post_fav WHERE post_fav_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, post_fav_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
*/		
		//댓글 등록
		//댓글 수
		//댓글 목록
		//댓글 상세
		//댓글 수정
		//댓글 삭제
		
}
