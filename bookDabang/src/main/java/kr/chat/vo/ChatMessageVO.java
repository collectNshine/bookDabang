package kr.chat.vo;

import kr.member.vo.MemberVO;

public class ChatMessageVO {
	private int message_num;
	private int mem_num;
	private int chat_num;
	private String chat_content;
	private String chat_date;
	
	private MemberVO memberVo;
	
	// Getters and Setters
	public int getMessage_num() { return message_num; }
	public void setMessage_num(int message_num) { this.message_num = message_num; }
	
	public int getMem_num() { return mem_num; }
	public void setMem_num(int mem_num) { this.mem_num = mem_num; }
	
	public int getChat_num() { return chat_num; }
	public void setChat_num(int chat_num) { this.chat_num = chat_num; }
	
	public String getChat_content() { return chat_content; }
	public void setChat_content(String chat_content) { this.chat_content = chat_content; }
	
	public String getChat_date() { return chat_date; }
	public void setChat_date(String reg_date) { this.chat_date = reg_date; }
	
	public MemberVO getMemberVo() { return memberVo; }
	public void setMemberVo(MemberVO memberVo) { this.memberVo = memberVo; }
}
