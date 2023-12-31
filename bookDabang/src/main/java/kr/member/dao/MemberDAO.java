package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴 만들기 
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	};
	private MemberDAO(){}; 

	//로그인 : 활성 유저 검색
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
				vo.setSalt(rs.getString("salt"));
				vo.setPasswd(rs.getString("passwd"));
				vo.setAuth(rs.getInt("auth"));
				vo.setState(rs.getInt("state"));
				//세션에 담을 내용이 있다면 아래에 추가한다.
				vo.setName(rs.getString("name"));
				vo.setNickname(rs.getString("nickname"));
				vo.setPhoto(rs.getString("photo"));
				//주문폼을 위해 추가 
				vo.setZipcode(rs.getString("zipcode"));
				vo.setAddress1(rs.getString("address1"));
				vo.setAddress2(rs.getString("address2"));
				vo.setPhone(rs.getString("phone"));
				vo.setEmail(rs.getString("email"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return vo;
	}
	//로그인 : 휴면계정 유저 검색
		public MemberVO selectSleepUser(String id) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			MemberVO vo = null;
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM member b LEFT OUTER JOIN member_sleep s ON b.mem_num = s.mem_num WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					vo = new MemberVO();
					vo.setMem_num(rs.getInt("mem_num"));
					vo.setId(rs.getString("id"));
					vo.setSalt(rs.getString("ssalt"));
					vo.setPasswd(rs.getString("spasswd"));
					vo.setAuth(rs.getInt("auth"));
					vo.setState(rs.getInt("state"));

					//세션에 담을 내용이 있다면 아래에 추가한다.
					vo.setName(rs.getString("sname"));
					vo.setNickname(rs.getString("snickname"));
					vo.setPhoto(rs.getString("sphoto"));
					vo.setSsleep_date(rs.getDate("ssleep_date"));
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
			sql = "INSERT INTO member_detail (mem_num,name,nickname,salt,passwd,"
					+ "sex,birthday,phone,zipcode,address1,address2,email,reg_date) "
					+ "VALUES (?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,?,?,?,?,SYSDATE)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1,mem_seq);
			pstmt3.setString(2,vo.getName());
			pstmt3.setString(3,vo.getNickname());
			pstmt3.setString(4, vo.getSalt());
			pstmt3.setString(5,vo.getPasswd());
			pstmt3.setInt(6, vo.getSex());
			pstmt3.setString(7, vo.getBirthday());
			pstmt3.setString(8, vo.getPhone());
			pstmt3.setString(9, vo.getZipcode());
			pstmt3.setString(10, vo.getAddress1());
			pstmt3.setString(11, vo.getAddress2());
			pstmt3.setString(12, vo.getEmail());
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
	
	//활성계정 최근 로그인 일수 업데이트 
	public void updateLatestLoginDate(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql="UPDATE (SELECT * FROM member m JOIN member_detail d USING(mem_num) where id = ?) SET latest_login = SYSDATE";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//휴면계정을 활성 계정으로 변경
	public void updateSleepMember(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn=DBUtil.getConnection();
			sql=" UPDATE member SET state = 0 where id = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally{
			DBUtil.executeClose(null, pstmt, conn);
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
	public void passwdChange(String name, String email,String salt, String newPasswd) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql="UPDATE (SELECT * FROM member m JOIN member_detail d USING(mem_num)) SET salt =?, passwd = ? WHERE name=? AND email=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,salt);
			pstmt.setString(2, newPasswd);
			pstmt.setString(3, name); 
			pstmt.setString(4, email);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return;
	}
	//닉네임 중복검사 
	public boolean checkNick(String nickname) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		boolean check = false;
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM member_detail WHERE nickname = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			rs = pstmt.executeQuery();
			check = rs.next(); 
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return check;
	}
}