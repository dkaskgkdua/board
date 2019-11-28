package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;

class TableInOut extends JFrame {

	ArrayList<Board> firstData; 
	
	TableInOut() {
		super("참가자 명단 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setPreferredSize(new Dimension(1200, 300));
		setLocation(100, 100);
		Container contentPane = getContentPane();
		
		String colName[] = { "글번호" , "작성자", "비밀번호", "제목", "내용", "파일명", "원본파일명", "참조번호", "답변 레벨", "답변순서", "조회수", "날짜" };
		
		ArrayList<Object> al = new ArrayList<Object>();
		
		DefaultTableModel model = new DefaultTableModel(colName, 0);
		JTable table = new JTable(model);
		
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
		
		
		
		Panel p = new Panel();
		FlowLayout flowLayout = (FlowLayout) p.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(p, BorderLayout.SOUTH);
		
		JButton InButton = new JButton("추가");
		JButton OutButton = new JButton("삭제");
		JButton UpdateButton = new JButton("변경");
		JButton AnsButton = new JButton("답변");
		
		JLabel label1 = new JLabel("작성자");
		JLabel label2 = new JLabel("비밀번호");
		JLabel label3 = new JLabel("제목");
		JLabel label4 = new JLabel("내용");
		
		JTextField tf1 = new JTextField();
		tf1.setPreferredSize(new Dimension(70, 20));
		JTextField tf2 = new JTextField();
		tf2.setPreferredSize(new Dimension(50, 20));
		JTextField tf3 = new JTextField();
		tf3.setPreferredSize(new Dimension(200, 20));
		JTextField tf4 = new JTextField();
		tf4.setPreferredSize(new Dimension(300, 20));
		
		p.add(label1);
		p.add(tf1);
		p.add(label2);
		p.add(tf2);
		p.add(label3);
		p.add(tf3);
		p.add(label4);
		p.add(tf4);
		p.add(InButton);
		p.add(OutButton);
		p.add(UpdateButton);
		p.add(AnsButton);
		
		if (selectAll().size() != 0) {
 			firstData = selectAll();
 			
 			for(Board g : firstData) {
 				Object[] b = {g.getNo(), g.getName(), g.getPw(), g.getTitle(), g.getContent(),
 				g.getFileName(), g.getParentFileName(), g.getReferenceNo(),
 				g.getAnsLevel(), g.getAnsSeq(), g.getClickNo(), g.getDate()};
				model.addRow(b);
			}
		} else {
			System.out.println("현재 입력된 값이 없습니다.");
		}
		
		
		// �Է� ��ư
		InButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// db�� �� �Է�
				insert(tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText());
				// ArrayList�� db�� ���� �޾ƿ���
				
				// ���� ���̺� �ִ� ���� �� �����
				for(int i = model.getRowCount()-1; i >= 0; i--) {
					model.removeRow(i);
				}
				
				ArrayList<Board> data = selectAll();
				// ������ ���� ���̺� ä���
				if(data.size() != 0) {
						for(Board g : data) {
							Object[] b = {g.getNo(), g.getName(), g.getPw(), g.getTitle(), g.getContent(),
					 				g.getFileName(), g.getParentFileName(), g.getReferenceNo(),
					 				g.getAnsLevel(), g.getAnsSeq(), g.getClickNo(), g.getDate()};
							model.addRow(b);
						}
					} else {
						System.out.println("테이블에 데이터가 없다.");
					}
						
			}
		});
		//����
		AnsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row =  table.getSelectedRow();
				
				String no = table.getValueAt(row, 9).toString();
				String ansLevel = table.getValueAt(row, 8).toString();
				String ansSeq = table.getValueAt(row, 7).toString();
				int referenceNo = Integer.parseInt(no);
				int ansLevelInt = Integer.parseInt(ansLevel);
				int ansSeqInt = Integer.parseInt(ansSeq);
				
				insert2(referenceNo, ansLevelInt, ansSeqInt, tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText());
				
				// ���� ���̺� �ִ� ���� �� �����
				for(int i = model.getRowCount()-1; i >= 0; i--) {
					model.removeRow(i);
				}
				
				ArrayList<Board> data = selectAll();
				// ������ ���� ���̺� ä���
				if(data.size() != 0) {
						for(Board g : data) {
							Object[] b = {g.getNo(), g.getName(), g.getPw(), g.getTitle(), g.getContent(),
					 				g.getFileName(), g.getParentFileName(), g.getReferenceNo(),
					 				g.getAnsLevel(), g.getAnsSeq(), g.getClickNo(), g.getDate()};
							model.addRow(b);
						}
					} else {
						System.out.println("테이블에 데이터가 없다.");
					}
						
			}
		});
		/*
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				try {
					int row = e.getFirstRow();
					int col = e.getColumn();
					
					// ������Ʈ �÷�����
					String colName = table.getColumnName(col);
					// ������Ʈ �÷���
					String val = table.getValueAt(row, col).toString();
					
					// �⺻Ű�� �޾ƿ���
					String no = table.getValueAt(row, 0).toString();
					int editno = Integer.parseInt(no);
					if(colName.equals("�ۼ���")) {
						colName = "board_name";
					} else if(colName.equals("��й�ȣ")) {
						colName = "board_pass";
					} else if(colName.equals("����")) {
						colName = "board_subject";
					} else if(colName.equals("����")) {
						colName = "board_content";
					} else {
						System.out.println("�÷����� �߸� ������");
					}
						
					// ������Ʈ ���� �����ϰ� � ����ƴ��� ��ȯ����
					int a = update(editno, colName, val);
					
					if (a == -1) {
						System.out.println("���� ����");
					} else {
						System.out.println("���� ����");
					}
					
					System.out.println("���õ� �� = " + row + "���õ� �� = " + col + "�� = " + val);
					} catch(Exception eee) {
						System.out.println("������Ʈ���� �ʵ尪 ����");
					}
			}
		});
		*/
		
		UpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row =  table.getSelectedRow();
				
				String no = table.getValueAt(row, 0).toString();
				int editno = Integer.parseInt(no);
				int a = -1;
				if(!tf1.getText().equals("")) {
					a = update(editno, "board_name" , tf1.getText());
					if ( a == -1) {
						System.out.println("작성자 변경 실패");
					} else {
						System.out.println("작성자 변경 성공");
					}
				}
				if(!tf2.getText().equals("")) {
					a = update(editno, "board_pass", tf2.getText());
					if ( a == -1) {
						System.out.println("비밀번호 변경 실패");
					} else {
						System.out.println("비밀번호 변경 성공");
					}
				}
				if(!tf3.getText().equals("")) {
					a = update(editno, "board_subject", tf3.getText());
					if ( a == -1) {
						System.out.println("제목 변경 실패");
					} else {
						System.out.println("제목 변경 성공");
					}
				}
				if(!tf4.getText().equals("")) {
					a = update(editno, "board_content", tf4.getText());
					if ( a == -1) {
						System.out.println("내용 변경 실패");
					} else {
						System.out.println("내용 변경 성공");
					}
				}
				if(a == -1) {
					System.out.println("텍스트박스에 값을 입력해주세요.");
				}
				
				ArrayList<Board> data = selectAll();
				// ���� ���̺� �ִ� ���� �� �����
				for(int i = model.getRowCount()-1; i >= 0; i--) {
					model.removeRow(i);
				}
				// ������ ���� ���̺� ä���
				if(data.size() != 0) {
						for(Board g : data) {
							Object[] b = {g.getNo(), g.getName(), g.getPw(), g.getTitle(), g.getContent(),
					 				g.getFileName(), g.getParentFileName(), g.getReferenceNo(),
					 				g.getAnsLevel(), g.getAnsSeq(), g.getClickNo(), g.getDate()};
			 				
							model.addRow(b);
						}
					} else {
						System.out.println("테이블에 데이터가 없다.");
					}
			}
		});
		// ���� ��ư Ŭ����
		ActionListener all = new RemoveActionListener(table);
		OutButton.addActionListener(all);
		//button.addActionListener(listener);
		pack();
	}

	public static void main(String args[]) {
		TableInOut in = new TableInOut();
	}
	
	public int update(int editno, String colName, String editValue) {
		Statement stmt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sql= new StringBuilder();

		sql.append("update board2 set "+ colName+ "= ? where board_num = ? ");
		
		
		
		int result = -1;
		try {
			conn = ConnUtil2.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, editValue);
			pstmt.setInt(2, editno);
			result = pstmt.executeUpdate();
		} catch (SQLException se) {
			System.out.println("sql오류");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println("pstmt오류");
			}
			try {
				if(conn != null)
					conn.close();
			} catch(SQLException e) {
				System.out.println("conn 오류");
			}
		}

		return result;
	}
	/*
	public Board select(int no) {
		Statement stmt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from BOARD where board_num = ?");
		Board st = null;
		
				
		try {
			conn = ConnUtil2.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1,  no);
			
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				st = new Board();
				st.setNo(rs.getInt("board_num"));
				st.setName(rs.getString("board_name"));
				st.setPw(rs.getString("board_pass"));
				st.setTitle(rs.getString("board_subject"));
				st.setContent(rs.getString("board_content"));
				st.setFileName(rs.getString("board_file"));
				st.setParentFileName(rs.getString("board_original"));
				st.setReferenceNo(rs.getInt("board_re_ref"));
				st.setAnsLevel(rs.getInt("board_re_lev"));
				st.setAnsSeq(rs.getInt("board_re_seq"));
				st.setClickNo(rs.getInt("board_readcount"));
				st.setDate(rs.getDate("board_date"));
			}
			
		}  catch(SQLException se) {
			System.out.println("SQL����");
		} finally {
			
			try {
				if(stmt != null)
					stmt.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if(conn != null)
					conn.close();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return st;
	}
	
	*/
	public static ArrayList<Board> selectAll() {
		Statement stmt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from board2 order by board_re_ref desc, board_re_seq asc ");
		ArrayList<Board> arrst = new ArrayList<Board>();
		
		try {
			conn = ConnUtil2.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			String Retitle;
			String gong;
			
			while(rs.next()) {
				Board st = new Board();
				Retitle = "";
				gong = "";
				st.setNo(rs.getInt("board_num"));
				st.setName(rs.getString("board_name"));
				st.setPw(rs.getString("board_pass"));
				
				for(int i = 0; i <= rs.getInt("board_re_lev"); i++) {
					gong = "  " + gong;
				}
				if(rs.getInt("board_re_lev") >= 1) {
					Retitle = Retitle = "Re:";
				}
				Retitle = gong + Retitle + rs.getString("board_subject") ;
				st.setTitle(Retitle);
				st.setContent(rs.getString("board_content"));
				st.setFileName(rs.getString("board_file"));
				st.setParentFileName(rs.getString("board_original"));
				st.setReferenceNo(rs.getInt("board_re_ref"));
				st.setAnsLevel(rs.getInt("board_re_lev"));
				st.setAnsSeq(rs.getInt("board_re_seq"));
				st.setClickNo(rs.getInt("board_readcount"));
				st.setDate(rs.getDate("board_date"));
				arrst.add(st);
			}	
		} catch(SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return arrst;
	}
	
// file, original�� null,  
private static void insert(String name, String pw, String title, String content) {
	Statement stmt = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	StringBuilder sql = new StringBuilder();
	sql.append("insert into board2(board_num, board_name, board_pass, board_subject, board_content, board_re_ref, board_re_lev, board_re_seq, board_readcount, board_date) ");
	sql.append("values(board_seq.nextval, ?, ?, ?, ?, board_seq.nextval, 0, 0, 0, sysdate )");
			
	int result = 0;
	try {
		conn = ConnUtil2.getConnection();
		
		pstmt = conn.prepareStatement(sql.toString());
		
		pstmt.setString(1, name);
		pstmt.setString(2, pw);
		pstmt.setString(3 , title);
		pstmt.setString(4 , content);
		
		result = pstmt.executeUpdate();
		System.out.println("db�� �ݿ��� . . . . . ");
		
		
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		try { 
			if(pstmt != null)
				pstmt.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(conn != null)
				conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	if(result == 1)
		System.out.println("���� �Ǿ����ϴ�.");
	else {
		System.out.println("���� ���е�");
	}
}

public  static int updateSeq(int no, int referenceNo) {
	Statement stmt = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	StringBuilder sql= new StringBuilder();
	sql.append("update board2 set board_re_seq = board_re_seq+1 where board_re_ref = ? and board_re_seq >? ");
	
	
	
	int result = -1;
	try {
		conn = ConnUtil2.getConnection();
		pstmt = conn.prepareStatement(sql.toString());
		
		pstmt.setInt(1, no);
		pstmt.setInt(2, referenceNo);
		result = pstmt.executeUpdate();
		
	}  catch(SQLException se) {
		System.out.println("SQL����");
	} finally {
		
		try {
			if(stmt != null)
				stmt.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(conn != null)
				conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	return result;
}
private static void insert2(int referenceNo, int ansLevel, int ansSeq, String name, String pw, String title, String content) {
	Statement stmt = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	StringBuilder sql = new StringBuilder();
	int addAns = ansLevel + 1;
	sql.append("insert into board2(board_num, board_name, board_pass, board_subject, board_content, board_re_ref, board_re_lev, board_re_seq, board_readcount, board_date) ");
	sql.append("values(board_seq.nextval, ?, ?, ?, ?, ?, ?, ?, 0, sysdate )");
	int a = updateSeq(ansSeq, referenceNo);
	if (a == -1) {
		System.out.println("Seq 업뎃 실패");
	} else {
		System.out.println("Seq 업뎃 성공");
	}
	int result = 0;
	try {
		conn = ConnUtil2.getConnection();
		
		pstmt = conn.prepareStatement(sql.toString());
		
		pstmt.setString(1, name);
		pstmt.setString(2, pw);
		pstmt.setString(3 , title);
		pstmt.setString(4 , content);
		pstmt.setInt(5, ansSeq);
		pstmt.setInt(6,  addAns);
		pstmt.setInt(7, referenceNo+1);
		//if(select max(board_re_seq) from board where board_re_ref = )
		result = pstmt.executeUpdate();
		System.out.println("db에 반영됨 . . . . . ");
		
		
	} catch (Exception e) {
		System.out.println(e);
	} finally {
		try { 
			if(pstmt != null)
				pstmt.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		try {
			if(conn != null)
				conn.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	if(result == 1)
		System.out.println("삽입 되었습니다.");
	else {
		System.out.println("삽입 실패됨");
	}

}

}