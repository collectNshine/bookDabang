package kr.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.notice.vo.NoticeVO;
import kr.util.DBUtil;

public class NoticeDAO {
	//싱글턴 패턴
	private static NoticeDAO instance = new NoticeDAO();
	public static NoticeDAO getInstance() {
		return instance;
	}
	private NoticeDAO() {};
	
	//전체 글 목록 보여주기(SELECT)
	public List<NoticeVO> selectList(int start, int end, String keyfield, String keyword, Integer category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		ResultSet rs = null;
		int cnt = 0;
		List<NoticeVO> list = null;
		try {	
				if((keyfield!=null && !"".equals(keyfield))||category!= null && category > 0) {
					sub_sql+=" WHERE";
					
					if(keyfield!=null && !"".equals(keyfield)) {
						if(keyfield.equals("1"))sub_sql += " noti_title LIKE ?";
						else if(keyfield.equals("2")) sub_sql += " noti_content LIKE ?";
					}
					if((keyfield!=null && !"".equals(keyfield))&&category!=null && category > 0) {
						sub_sql+=" AND";
					}
					if(category!=null&& category > 0) {
						sub_sql += " noti_category=?";
					}
				}
				
			conn=DBUtil.getConnection();
			sql="SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM notice_board"+sub_sql+" ORDER BY noti_num DESC)a) WHERE rnum>=? AND rnum<=?";
			pstmt=conn.prepareStatement(sql);
			
			if(keyword != null 
				      && !"".equals(keyword)) {
			pstmt.setString(++cnt, "%" + keyword + "%");
			}
			if(category!=null&& category > 0) {
			pstmt.setInt(++cnt, category);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs=pstmt.executeQuery();
			list = new ArrayList<NoticeVO>();
			
			while(rs.next()) {
				NoticeVO vo = new NoticeVO();
				vo.setNoti_num(rs.getInt("noti_num"));
				vo.setNoti_category(rs.getInt("noti_category"));
				vo.setNoti_title(rs.getString("noti_title"));
				vo.setNoti_content(rs.getString("noti_content"));
				vo.setNoti_date(rs.getDate("noti_date"));
				list.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//글 선택
	public NoticeVO selectOneNotice(int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		NoticeVO vo = new NoticeVO();
		try {
			conn=DBUtil.getConnection();
			sql="SELECT * FROM notice_board WHERE noti_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noti_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				vo.setNoti_num(noti_num);
				vo.setNoti_category(rs.getInt("noti_category"));
				vo.setNoti_title(rs.getString("noti_title"));
				vo.setNoti_content(rs.getString("noti_content"));
				vo.setNoti_date(rs.getDate("noti_date"));	
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	
	//전체 글 수 체크(검색필드와 검색단어의 영향을 받음.)
	public int countNotice(String keyfield,String keyword,Integer category) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		 
		try {
			
			if((keyfield!=null && !"".equals(keyfield))||category!= null && category > 0) {
				sub_sql+=" WHERE";
				
				if(keyfield!=null && !"".equals(keyfield)) {
					if(keyfield.equals("1"))sub_sql += " noti_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " noti_content LIKE ?";
				}
				if((keyfield!=null && !"".equals(keyfield))&&category!=null && category > 0) {
					sub_sql+=" AND";
				}
				if(category!=null&& category > 0) {
					sub_sql += " noti_category=?";
				}
			}
			
			conn=DBUtil.getConnection();
			sql="SELECT count(*) FROM notice_board"+ sub_sql;
			pstmt=conn.prepareStatement(sql);
			
				if(keyword != null 
					      && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
				}
				if(category!=null&& category > 0) {
					pstmt.setInt(++cnt, category);
					}
				
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return cnt;
	}
	//카테고리 이름과 게시글 정보 값 얻어오기
	public List<NoticeVO> categoryNameNum() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<NoticeVO> list = null;
		try {
			conn=DBUtil.getConnection();
			
			sql="SELECT noti_category, count(*) noti_count,"
					+ "CASE WHEN noti_category = 1 THEN '회원' "
					+ "WHEN noti_category = 2 THEN '주문/주문변경' "
					+ "WHEN noti_category = 3 THEN '결제' "
					+ "WHEN noti_category = 4 THEN '증빙서류' "
					+ "WHEN noti_category = 5 THEN '공지사항'"
					+ "END cate_msg FROM notice_board group by noti_category order by noti_category asc";
			
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeVO>();
			while(rs.next()) {
				NoticeVO vo = new NoticeVO();
				vo.setNoti_category_name(rs.getString("cate_msg"));
				vo.setNoti_category_count(rs.getInt("noti_count"));
				vo.setNoti_category(rs.getInt("noti_category")); 
				
				list.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//글 작성 하기(INSERT)
	public void insertNotice(NoticeVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="INSERT INTO notice_board (noti_num,noti_date,noti_category,noti_title,noti_content,latest_ed_date,mem_num) VALUES (notice_seq.nextval,SYSDATE,?,?,?,SYSDATE,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,vo.getNoti_category());
			pstmt.setString(2, vo.getNoti_title());
			pstmt.setString(3, vo.getNoti_content());
			pstmt.setInt(4, vo.getMem_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//글 수정하기(UPDATE) 
	public void updateNotice(NoticeVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="UPDATE notice_board SET noti_category=?, noti_title=?, noti_content=?, latest_ed_date=SYSDATE WHERE noti_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getNoti_category());
			pstmt.setString(2, vo.getNoti_title());
			pstmt.setString(3, vo.getNoti_content());
			pstmt.setInt(4, vo.getNoti_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//글 삭제하기(DELETE)
	public void deleteNotice(String[] noti_nums) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="DELETE FROM notice_board WHERE noti_num IN("+ String.join(",",noti_nums)+")";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	/*
	//댓글등록
	public void insertReply(NoticeReplyVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
		
			sql="INSERT INTO notice_reply(re_num,re_content,re_ip,re_date,noti_num,mem_num) VALUES (notice_reply_seq.nextval,?,?,SYSDATE,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getRe_content());
			pstmt.setString(2, vo.getRe_ip());
			pstmt.setInt(3, vo.getNoti_num());
			pstmt.setInt(4, vo.getMem_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글개수
	public int countReply(int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			sql="SELECT COUNT(*) FROM notice_reply n JOIN member m ON n.mem_num=m.mem_num WHERE n.noti_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noti_num);
			rs=pstmt.executeQuery();
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
	//댓글목록
	public List<NoticeReplyVO> listReply(int start, int end, int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<NoticeReplyVO> list = null;
		
		try {
			conn=DBUtil.getConnection();
			sql="SELECT * FROM (SELECT a.*, "
					+ "rownum rnum FROM (SELECT * "
					+ "FROM notice_reply n JOIN member m "
					+ "USING(mem_num) "
					+ "WHERE n.noti_num = ? ORDER BY "
					+ "n.re_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, noti_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeReplyVO>();
			while(rs.next()){
				NoticeReplyVO vo = new NoticeReplyVO();
				vo.setId(rs.getString("id"));
				vo.setRe_num(rs.getInt("re_num"));
				vo.setRe_content(rs.getString("re_content"));
				vo.setRe_date(rs.getString("re_date"));
				vo.setNoti_num(rs.getInt("noti_num"));
				vo.setMem_num(rs.getInt("mem_num"));
				
				list.add(vo);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//댓글상세
	public NoticeReplyVO selectOneReply(int re_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		NoticeReplyVO vo = null;
		
		try {
			conn=DBUtil.getConnection();
			sql="SELECT * FROM notice_reply WHERE re_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, re_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new NoticeReplyVO();
				vo.setNoti_num(rs.getInt("noti_num"));
				vo.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//댓글수정
	public void updateReply(NoticeReplyVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="UPDATE notice_reply SET re_content=?, re_ip=?, re_modifydate=SYSDATE WHERE re_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, vo.getRe_content());
			pstmt.setString(2, vo.getRe_ip());
			pstmt.setInt(3, vo.getRe_num());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//댓글삭제
	public void deleteReply(int re_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="DELETE FROM notice_reply WHERE re_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,re_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	*/
}
