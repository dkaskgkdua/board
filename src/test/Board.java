package test;

import java.sql.Date;

public class Board {
	private int no;					//��ȣ
	private String name;			//�ۼ���
	private String pw;					//��й�ȣ
	private String title;			//����
	private String content;			//����
	private String fileName;		//���ϸ�
	private String parentFileName;	//�������ϸ�
	private int referenceNo;		// ������ȣ
	private int ansLevel;			// �亯 ����
	private int ansSeq;				//�亯����
	private int clickNo;			//��ȸȽ��
	private Date date;				//��¥

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
