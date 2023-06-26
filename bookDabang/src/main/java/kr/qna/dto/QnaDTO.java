package kr.qna.dto;

import java.io.Serializable;
import java.sql.Date;

public class QnaDTO implements Serializable {
	//데이터를 직렬화 하여 전달한다.
	private static final long serialVersionUID = 1L;
	
	private int qna_num;
	private int refer;
	private int step;
	private int depth;
	private String qna_title;
	private String qna_content;
	private Date reg_date;
	private int mem_num;
	private int delflag;
	
	

	public QnaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//초기화를 해줄 수 있는 생성자
	public QnaDTO(int qna_num, int refer, int step, int depth, String qna_title, String qna_content, Date reg_date,
			int mem_num) {
		super();
		this.qna_num = qna_num;
		this.refer = refer;
		this.step = step;
		this.depth = depth;
		this.qna_title = qna_title;
		this.qna_content = qna_content;
		this.reg_date = reg_date;
		this.mem_num = mem_num;
	}
	
	//getter & setter
	public int getQna_num() {
		return qna_num;
	}

	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}

	public int getRefer() {
		return refer;
	}

	public void setRefer(int refer) {
		this.refer = refer;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getQna_title() {
		return qna_title;
	}

	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}

	public String getQna_content() {
		return qna_content;
	}

	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int delflag) {
		this.delflag = delflag;
	}

	//쉽게 값을 뽑아서 내용 확인해볼 수 있다. 
	@Override
	public String toString() {
		return "QnaDTO [qna_num=" + qna_num + ", refer=" + refer + ", step=" + step + ", depth=" + depth
				+ ", qna_title=" + qna_title + ", qna_content=" + qna_content + ", reg_date=" + reg_date + ", mem_num="
				+ mem_num + "]";
	}
	
	
}
