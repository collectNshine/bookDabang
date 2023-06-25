package kr.post.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.post.dao.PostDAO;
import kr.post.vo.PostFavVO;
import kr.post.vo.PostReplyVO;
import kr.post.vo.PostReportVO;
import kr.post.vo.PostVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

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
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM post p JOIN member m USING(mem_num) LEFT OUTER JOIN member_detail d USING(mem_num) LEFT OUTER JOIN book_list b USING(bk_num) ORDER BY p.post_num DESC)a) WHERE rnum>=? AND rnum<=?";
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
					//날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
					post.setPost_date(DurationFromNow.getTimeDiffLabel(rs.getString("post_date")));
					if(rs.getString("post_modifydate") != null) {
						post.setPost_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("post_modifyDate")));
					}
					post.setPost_photo(rs.getString("post_photo"));
					post.setPost_ip(rs.getString("post_ip"));
					post.setMem_num(rs.getInt("mem_num"));
					post.setBk_num(rs.getInt("bk_num"));
					post.setName(rs.getString("name"));
					post.setPhoto(rs.getString("photo"));
					
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
		
		//총 서평 수
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
					post.setPost_date(rs.getString("post_date"));
					post.setPost_modifydate(rs.getString("post_modifydate"));
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
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//auto commit 해제
				conn.setAutoCommit(false);
				
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
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}

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
		
		//좋아요 개수
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
					fav.setPost_fav_num(rs.getInt("post_fav_num"));
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
		
		//댓글 등록
		public void insertReplyPost(PostReplyVO postReply) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO post_reply (re_num,re_content,re_ip,mem_num,post_num) VALUES (post_reply_seq.nextval,?,?,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, postReply.getRe_content());
				pstmt.setString(2, postReply.getRe_ip());
				pstmt.setInt(3, postReply.getMem_num());
				pstmt.setInt(4, postReply.getPost_num());
				//SQL문 실행
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
			
		}
		
		//댓글 개수
		public int getReplyPostCount(int post_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM post_reply p JOIN member m ON p.mem_num=m.mem_num WHERE p.post_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, post_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
								//컬럼 index
					count = rs.getInt(1);
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return count;
		}
		
		//댓글 목록
		public List<PostReplyVO> getListReplyPost(int start, int end, int post_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<PostReplyVO> list = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM post_reply p JOIN member m USING(mem_num) WHERE p.post_num=? ORDER BY p.re_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, post_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<PostReplyVO>();
				while(rs.next()) {
					PostReplyVO reply = new PostReplyVO();
					reply.setRe_num(rs.getInt("re_num"));
					reply.setRe_date(rs.getString("re_date"));
					reply.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));
					reply.setPost_num(rs.getInt("post_num"));
					reply.setMem_num(rs.getInt("mem_num"));
					reply.setId(rs.getString("id"));
					
					list.add(reply);
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}

		//댓글 상세
		public PostReplyVO getReplyPost(int re_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			PostReplyVO reply = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM post_reply WHERE re_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, re_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					reply = new PostReplyVO(); //자바빈 객체 생성
					reply.setRe_num(rs.getInt("re_num"));
					reply.setMem_num(rs.getInt("mem_num"));
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return reply;
		}
		
		//댓글 수정
		public void updateReplyPost(PostReplyVO reply) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE post_reply SET re_content=?, re_modifydate=SYSDATE, re_ip=? WHERE re_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, reply.getRe_content());
				pstmt.setString(2, reply.getRe_ip());
				pstmt.setInt(3, reply.getRe_num());
				//SQL문 실행
				pstmt.executeUpdate();
			} catch(Exception e){
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//댓글 삭제
		public void deleteReplyPost(int re_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM post_reply WHERE re_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, re_num);
				//SQL문 실행
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//신고 등록
		public void insertReportPost(PostReportVO postReport) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO post_report (repo_num,repo_content,repo_ip,repo_category,mem_num,post_num) VALUES (post_report_seq.nextval,?,?,?,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, postReport.getRepo_content());
				pstmt.setString(2, postReport.getRepo_ip());
				pstmt.setInt(3, postReport.getRepo_category());
				pstmt.setInt(4, postReport.getMem_num());
				pstmt.setInt(5, postReport.getPost_num());
				//SQL문 실행
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
			
		}
		
		//신고 목록
		//public List<PostReportVO> getReportList(int start, int end, String repoKeyfield, String repoKeyword) throws Exception{
		public List<PostReportVO> getReportList(int start, int end, String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<PostReportVO> list = null;
			String sql = null;
			int cnt = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM post_report ORDER BY repo_date DESC)a) WHERE rnum>=? AND rnum<=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%" + keyword + "%");
				}
				/*
				if(repoKeyword!=null && !"".equals(repoKeyword)) {
					pstmt.setString(++cnt, "%" + repoKeyword + "%");
				}
				 */
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<PostReportVO>();
				while(rs.next()) {
					PostReportVO prepo = new PostReportVO();
					prepo.setRepo_num(rs.getInt("repo_num"));
					prepo.setRepo_date(rs.getDate("repo_date"));
					prepo.setRepo_ip(rs.getString("repo_ip"));
					prepo.setRepo_content(rs.getString("repo_content"));
					prepo.setRepo_ip(rs.getString("repo_ip"));
					prepo.setRepo_category(rs.getInt("repo_category"));
					prepo.setMem_num(rs.getInt("mem_num"));
					//자바빈을 ArrayList에 저장
					list.add(prepo);
				}
			}catch(Exception e){
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		//총 신고 수(검색 레코드 수)
		//public int getReportCount(String repoKeyfield, String repoKeyword) throws Exception{
		public int getReportCount(String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "WHERE pr.repo_category LIKE '%' || ? || '%'";
					if(keyfield.equals("2")) sub_sql += "WHERE mem_num LIKE '%' || ? || '%'";
				}
				/*
				if(repoKeyword != null && !"".equals(repoKeyword)) {
					if(repoKeyfield.equals("1")) sub_sql += "WHERE pr.repo_category LIKE '%' || ? || '%'";
					if(repoKeyfield.equals("2")) sub_sql += "WHERE mem_num LIKE '%' || ? || '%'";
				}
				*/
				sql = "SELECT COUNT(*) FROM post_report pr JOIN member m USING(mem_num)" + sub_sql;
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(1, "%" + keyword + "%");
				}
				/*
				if(repoKeyword!=null && !"".equals(repoKeyword)) {
					pstmt.setString(1, "%" + repoKeyword + "%");
				}
				 */
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
		
		//신고 상세
		public PostReportVO getReport(int repo_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			PostReportVO repo = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성 (탈퇴시에도 누락되지 않도록 LEFT OUTER JOIN함)
				sql = "SELECT * FROM post_report pr JOIN member m USING(mem_num) LEFT OUTER JOIN member_detail d USING(mem_num) WHERE pr.repo_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, repo_num);
				//SQL문을 실행해서 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();
				if(rs.next()) {
					repo = new PostReportVO();
					repo.setRepo_num(rs.getInt("repo_num"));
					repo.setRepo_content(rs.getString("repo_content"));
					repo.setRepo_category(rs.getInt("repo_category"));
					repo.setRepo_date(rs.getDate("repo_date"));
					repo.setRepo_ip(rs.getString("repo_num"));
					repo.setMem_num(rs.getInt("mem_num"));
					repo.setPost_num(rs.getInt("post_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return repo;
		}
		
		//신고 삭제
		public void deleteReport(int repo_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			try {
				//커넥션 풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				sql = "DELETE FROM post_report WHERE repo_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, repo_num);
				pstmt.executeUpdate();		
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
}
