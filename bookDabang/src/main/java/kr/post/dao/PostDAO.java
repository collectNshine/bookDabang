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
		//서평 수정
		//서평 삭제
}
