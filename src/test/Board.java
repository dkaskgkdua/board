package test;

import java.sql.Date;

public class Board {
	private int no;					//번호
	private String name;			//작성자
	private String pw;					//비밀번호
	private String title;			//제목
	private String content;			//내용
	private String fileName;		//파일명
	private String parentFileName;	//원본파일명
	private int referenceNo;		// 참조번호
	private int ansLevel;			// 답변 레벨
	private int ansSeq;				//답변순서
	private int clickNo;			//조회횟수
	private Date date;				//날짜

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getParentFileName() {
		return parentFileName;
	}
	public void setParentFileName(String parentFileName) {
		this.parentFileName = parentFileName;
	}
	public int getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(int referenceNo) {
		this.referenceNo = referenceNo;
	}
	public int getAnsLevel() {
		return ansLevel;
	}
	public void setAnsLevel(int ansLevel) {
		this.ansLevel = ansLevel;
	}
	public int getAnsSeq() {
		return ansSeq;
	}
	public void setAnsSeq(int ansSeq) {
		this.ansSeq = ansSeq;
	}
	public int getClickNo() {
		return clickNo;
	}
	public void setClickNo(int clickNo) {
		this.clickNo = clickNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
	public String toString() {
		return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t"
				, getNo(), getName(), getPw(), getTitle(), getContent(),
				getFileName(), getParentFileName(), getReferenceNo(),
				getAnsLevel(), getAnsSeq(), getClickNo(), getDate());
	}

}
