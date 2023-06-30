package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatMessageVO;
import kr.chat.vo.ChatVO;
import kr.member.vo.MemberVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class ChatDAO {
	// Singleton Pattern
	private static ChatDAO instance = new ChatDAO();
	public static ChatDAO getInstance() { return instance; }
	private ChatDAO() {}
	
	// 채팅방 생성
	public void insertChat(ChatVO chat) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO chat (chat_num, mem_num, chat_title, chat_img) VALUES (chat_seq.nextval, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat.getMem_num());
			pstmt.setString(2, chat.getChat_title());
			pstmt.setString(3, chat.getChat_img());
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 채팅방 삭제
	public void deleteChat(int chat_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			// 대화내용 삭제
			sql = "DELETE FROM chat_message WHERE chat_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			pstmt.executeUpdate();
			
			// 참여정보 삭제
			sql = "DELETE FROM chat_into WHERE chat_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, chat_num);
			pstmt2.executeUpdate();
			
			// 채팅방 삭제
			sql = "DELETE FROM chat WHERE chat_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, chat_num);
			pstmt3.executeUpdate();
			
			conn.commit();
		} catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 채팅방 개수
	public int getChatCount(String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) { if(keyfield.equals("1")) sub_sql += "WHERE chat_title LIKE ?"; }
			
			sql = "SELECT COUNT(*) FROM chat " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) { pstmt.setString(1, "%" + keyword + "%"); }
			
			rs = pstmt.executeQuery();
			if(rs.next()) { count = rs.getInt(1) ;}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return count;
	}
	
	// 채팅 목록
	public List<ChatVO> getListChat(int start, int end, String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ChatVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) { if(keyfield.equals("1")) sub_sql += "WHERE chat_title LIKE ?"; }
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM chat " + sub_sql + " ORDER BY reg_date DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) { pstmt.setString(++cnt, "%" + keyword + "%"); }
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ChatVO>();
			while(rs.next()) {
				ChatVO chat = new ChatVO();
				chat.setChat_num(rs.getInt("chat_num"));
				chat.setMem_num(rs.getInt("mem_num"));
				chat.setChat_title(StringUtil.useNoHtml(rs.getString("chat_title")));
				chat.setChat_img(rs.getString("chat_img"));
				chat.setReg_date(rs.getDate("reg_date"));
				
				list.add(chat);
			}
		} catch(Exception e) { throw new Exception(e);}
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	// 채팅방 입장 여부 검색
	public int selectMem_numIntoChat(int chat_num, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int auth = 0;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM chat_into WHERE mem_num = (SELECT mem_num FROM chat_into WHERE chat_num=? AND mem_num=?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			pstmt.setInt(2, mem_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) { auth = 1; }	// 검색값이 존재할 경우 참
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return auth;
	}
	
	// 채팅방 입장 등록
	public void insertChatInto(int chat_num, int mem_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO chat_into (chat_num, mem_num) VALUES (?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			pstmt.setInt(2, mem_num);
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 채팅방 상세
	public List<ChatVO> getChat(int chat_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ChatVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM chat WHERE chat_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ChatVO>();
			if(rs.next()) {
				ChatVO chat = new ChatVO();
				chat.setChat_num(rs.getInt("chat_num"));
				chat.setChat_title(rs.getString("chat_title"));
				chat.setChat_img(rs.getString("chat_img"));
				
				list.add(chat);
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	// 대화 입력
	public void insertChatContent(ChatMessageVO message) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO chat_message (message_num, mem_num, chat_num, chat_content) VALUES (chat_message_seq.nextval, ? ,?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, message.getMem_num());
			pstmt.setInt(2, message.getChat_num());
			pstmt.setString(3, message.getChat_content());
			
			pstmt.executeUpdate();
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(null, pstmt, conn); }
	}
	
	// 대화 개수
	public int getChatContentCount(int chat_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT COUNT(*) FROM chat_message WHERE chat_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) { count = rs.getInt(1); }
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return count;
	}
	
	// 대화 출력
	public List<ChatMessageVO> getListChatContent(int chat_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ChatMessageVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM chat_message c JOIN member_detail m ON c.mem_num=m.mem_num WHERE chat_num=? ORDER BY chat_date ASC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<ChatMessageVO>();
			while(rs.next()) {
				ChatMessageVO message = new ChatMessageVO();
				message.setMessage_num(rs.getInt("message_num"));
				message.setChat_num(rs.getInt("chat_num"));
				message.setMem_num(rs.getInt("mem_num"));
				message.setChat_content(StringUtil.useNoHtml(rs.getString("chat_content")));
				message.setChat_date(DurationFromNow.getTimeDiffLabel(rs.getString("chat_date")));
				
				// 회원 정보를 담기위해 MemberVO 객체 생성
				MemberVO member = new MemberVO();
				member.setName(rs.getString("name"));
				member.setPhoto(rs.getString("photo"));
				
				// MemberCO를 ChatMessageVO에 저장
				message.setMemberVo(member);
				
				list.add(message);
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return list;
	}
	
	// 대화정보
	public ChatVO getChatInfo(int chat_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ChatVO chat = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM chat WHERE chat_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chat_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chat = new ChatVO();
				chat.setMem_num(rs.getInt("mem_num"));
			}
		} catch(Exception e) { throw new Exception(e); }
		finally { DBUtil.executeClose(rs, pstmt, conn); }
		
		return chat;
	}
}
