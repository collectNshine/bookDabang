package kr.chat.vo;

import java.sql.Date;

public class ChatVO {
	private int chat_num;		// 채팅방 번호
	private int mem_num;		// 채팅방을 생성한 회원번호
	private String chat_title;	// 제목
	private String chat_img;	// 썸네일 사진
	private int limit;			// 입장제한 인원수 지정
	private Date reg_date;		// 생성일
	
	private ChatMessageVO messageVo;
	
	// Getters and Setters
	public int getChat_num() { return chat_num; }
	public void setChat_num(int chat_num) { this.chat_num = chat_num; }
	
	public int getMem_num() { return mem_num; }
	public void setMem_num(int mem_num) { this.mem_num = mem_num; }
	
	public String getChat_title() { return chat_title; }
	public void setChat_title(String chat_title) { this.chat_title = chat_title; }
	
	public String getChat_img() { return chat_img; }
	public void setChat_img(String chat_img) { this.chat_img = chat_img; }
	
	public int getLimit() { return limit; }
	public void setLimit(int limit) { this.limit = limit; }
	
	public Date getReg_date() { return reg_date; }
	public void setReg_date(Date reg_date) { this.reg_date = reg_date; }
	
	public ChatMessageVO getMessageVo() { return messageVo; }
	public void setMessageVo(ChatMessageVO messageVo) { this.messageVo = messageVo; }
}
