package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sun.net.httpserver.Authenticator.Result;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴 만들기 
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	};
	private MemberDAO(){}; 

	//로그인 : 유저 검색
	public MemberVO selectUser(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberVO vo = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM member b LEFT OUTER JOIN member_detail d "
					+ "ON b.mem_num = d.mem_num WHERE b.id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				vo = new MemberVO();
				vo.setMem_num(rs.getInt("mem_num"));
				vo.setId(rs.getString("id"));
				vo.setPasswd(rs.getString("passwd"));
				vo.setAuth(rs.getInt("auth"));
				vo.setState(rs.getInt("state"));

				//세션에 담을 내용이 있다면 아래에 추가한다.
				vo.setName(rs.getString("name"));
				vo.setPhoto(rs.getString("photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}

	//회원가입
	public void insertUser(MemberVO vo) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int mem_seq = 0;

		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			//member의 시퀀스 번호(mem_num)를 얻는다.
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mem_seq = rs.getInt(1);
			}

			//member테이블에 데이터를 입력한다. 
			sql = "INSERT INTO member(mem_num,id,auth,state) VALUES (?,?,1,0)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1,mem_seq);
			pstmt2.setString(2,vo.getId());
			pstmt2.executeUpdate();

			//member_테이블에 데이터를 입력한다. 
			sql = "INSERT INTO member_detail (mem_num,name,passwd,"
					+ "sex,birthday,phone,zipcode,address1,address2,email,reg_date) "
					+ "VALUES (?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,SYSDATE)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1,mem_seq);
			pstmt3.setString(2,vo.getName());
			pstmt3.setString(3,vo.getPasswd());
			pstmt3.setInt(4, vo.getSex());
			pstmt3.setString(5, vo.getBirthday());
			pstmt3.setString(6, vo.getPhone());
			pstmt3.setString(7, vo.getZipcode());
			pstmt3.setString(8, vo.getAddress1());
			pstmt3.setString(9, vo.getAddress2());
			pstmt3.setString(10, vo.getEmail());
			pstmt3.executeUpdate();

			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//[아이디/ 비밀번호 찾기 본인 인증용 이름, 이메일 검색]
	//찾는 값이 있으면 true, 없으면 false를 반환한다. 
	public boolean chackNameEmail(String name, String email) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean result = false;
		try {
			conn=DBUtil.getConnection();
			sql="SELECT * FROM member_detail WHERE name=? AND email=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			rs=pstmt.executeQuery();
			result = rs.next();
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return result; 
	}
	
	//아이디와 이메일 정보를 가지고 아이디를 검색하는 메서드
	public String idSearch(String name, String email) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String result = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM member m JOIN member_detail d USING(mem_num) WHERE name=? AND email=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name); 
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("id");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return result;
	}
	
	//아이디와 이메일 정보를 가지고 비밀번호를 변경하는 메서드 
	public void passwdChange(String name, String email,String newPasswd) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql="UPDATE (SELECT * FROM member m JOIN member_detail d USING(mem_num)) SET passwd = ? WHERE name=? AND email=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, newPasswd);
			pstmt.setString(2, name); 
			pstmt.setString(3, email);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return;
	}
	
}