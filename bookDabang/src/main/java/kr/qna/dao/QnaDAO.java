package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.qna.dto.QnaDTO;
import kr.util.DBUtil;
 
public class QnaDAO {
	//싱글턴 
	private static QnaDAO instance = new QnaDAO();
	public static QnaDAO getInstance() {
		return instance;
	}
	private QnaDAO(){};
	
	//글 목록 조회하기 
	public List<QnaDTO> selectList() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<QnaDTO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM qna_board ORDER BY refer,step";
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<QnaDTO>();
			
			while(rs.next()) {
				
				QnaDTO dto = new QnaDTO();
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setRefer(rs.getInt("refer"));
				dto.setStep(rs.getInt("step"));
				dto.setDepth(rs.getInt("depth"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_content(rs.getString("qna_content"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMem_num(rs.getInt("mem_num"));
				dto.setDelflag(rs.getInt("delflag"));
				list.add(dto);
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//새글 추가하기 
	public void insertQna(QnaDTO dto) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="INSERT INTO qna_board VALUES (qna_board_seq.nextval,(SELECT NVL(MAX(refer),0)+1 FROM qna_board), 0,0,?,?,SYSDATE,?,0)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,dto.getQna_title());
			pstmt.setString(2, dto.getQna_content());
			pstmt.setInt(3, dto.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//글 상세보기 
	public QnaDTO selectDetail(int qna_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		QnaDTO dto =  new QnaDTO();
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM qna_board WHERE qna_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setQna_num(rs.getInt("qna_num"));
				dto.setRefer(rs.getInt("refer"));
				dto.setStep(rs.getInt("step"));
				dto.setDepth(rs.getInt("depth"));
				dto.setQna_title(rs.getString("qna_title"));
				dto.setQna_content(rs.getString("qna_content"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMem_num(rs.getInt("mem_num"));
				dto.setDelflag(rs.getInt("delflag"));
			}
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return dto;
	}
	//글 수정하기
	public void updateQna(QnaDTO dto) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="UPDATE qna_board SET qna_title=?, qna_content=?, reg_date=SYSDATE WHERE qna_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,dto.getQna_title());
			pstmt.setString(2, dto.getQna_content());
			pstmt.setInt(3, dto.getQna_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//글 삭제하기(여러개 삭제하기)
	public void deleteQna(String[]qna_nums) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql="UPDATE qna_board SET delflag = 1 WHERE qna_num IN("+ String.join(",",qna_nums) +")";
																//qna_nums[1,2,3,4] ==> IN(1,2,3,4)
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//답글 추가하기 : 계층형 게시판의 꽃.
	public void replyQna(QnaDTO dto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql="UPDATE qna_board SET step = step+1 "
					+ "WHERE refer = (SELECT refer FROM qna_board WHERE qna_num = ?) "
					+ "AND step > (SELECT step FROM qna_board WHERE qna_num = ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getQna_num());
			pstmt.setInt(2, dto.getQna_num());
			pstmt.executeUpdate();
			
			sql="INSERT INTO qna_board "
					+ "VALUES (qna_board_seq.nextval,(SELECT refer FROM qna_board WHERE qna_num=?),"
					+ "(SELECT step FROM qna_board WHERE qna_num=?)+1,"
					+ "(SELECT depth FROM qna_board WHERE qna_num=?)+1,?,?,SYSDATE,?,0)";
					
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1, dto.getQna_num());
			pstmt2.setInt(2, dto.getQna_num());
			pstmt2.setInt(3, dto.getQna_num());
			pstmt2.setString(4, dto.getQna_title());
			pstmt2.setString(5, dto.getQna_content());
			pstmt2.setInt(6, dto.getMem_num());
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}

