package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class RemoveActionListener implements ActionListener {
	JTable table;
	Statement stmt = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	RemoveActionListener(JTable table) {
		this.table = table;
	}
	
	public void actionPerformed(ActionEvent e) {
		StringBuilder sql = new StringBuilder();
		
		

		int result = -1;
		int no= 0;
		int le = 0;
		int re = 0;
		int se = 0;
		//String sel="";
		try {
		int row = table.getSelectedRow();
		
		String val = table.getValueAt(row, 0).toString();	    // ���õ� ���� NUM��
		String ref = table.getValueAt(row, 7).toString();		// ���õ� ���� REF��
		String level = table.getValueAt(row, 8).toString();		// ���õ� ���� LEV��
		String seq = table.getValueAt(row, 9).toString();       // ���õ� ���� SEQ��
		
		no = Integer.parseInt(val);
		re = Integer.parseInt(ref);
		le = Integer.parseInt(level);
		se = Integer.parseInt(seq);
		
		System.out.println("���õ� �� = " + row + "�� = " + val+ "�⺻Ű =" + no + "���� =" + le);
		//sel = select(le,se);
		
		} catch (Exception ee) {
			System.out.println("���̺��� �� �޾ƿ��°� �߸� ��");
		}
		
		try {
			
		conn = ConnUtil2.getConnection();
		sql.append("delete from board where BOARD_RE_REF = ? and BOARD_RE_LEV >=? and BOARD_RE_SEQ >= ? and BOARD_RE_SEQ < (select nvl(min(board_re_seq), 99999999999) from board where board_re_lev <= ? and board_re_seq > ?)");
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setInt(1,  re);
		pstmt.setInt(2,  le);
		pstmt.setInt(3,  se);
		pstmt.setInt(4,  le);
		pstmt.setInt(5,  se);
		
		result = pstmt.executeUpdate();
		
		if (result != -1) {
			System.out.println("���� �Ϸ�");
		} else {
			System.out.println("���� ����");
		}
		} catch (SQLException see) {
			System.out.println("��� ������ ����");
		}
		
		ArrayList<Board> data = TableInOut.selectAll();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		// ���̺� ������ ���� ����
		for(int i = model.getRowCount()-1; i >= 0; i--) {
			model.removeRow(i);
		}
		// ��񿡼� �� �ҷ��ͼ� �ٽ� ����
		if(data.size() != 0) {
			for(Board g : data) {
				Object[] b = {g.getNo(), g.getName(), g.getPw(), g.getTitle(), g.getContent(),
				g.getFileName(), g.getParentFileName(), g.getReferenceNo(),
				g.getAnsLevel(), g.getAnsSeq(), g.getClickNo(), g.getDate()};
				model.addRow(b);
			}
		} else {
			System.out.println("���̺� �����Ͱ� ����.");
		}
	}
	
	// �����ϴ� ����
	public static String select(int lev, int seq) {
		Statement stmt2 = null;
		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		StringBuilder sql2 = new StringBuilder();
		sql2.append("select min(BOARD_RE_SEQ) from board where BOARD_RE_LEV <= ? and BOARD_RE_SEQ > ?");
		String answer = "";
		
		try {
			
			conn2 = ConnUtil2.getConnection();
			
			pstmt2 = conn2.prepareStatement(sql2.toString());
			pstmt2.setInt(1,  lev);
			pstmt2.setInt(2,  seq);
			
			
			ResultSet rs = pstmt2.executeQuery();
			while(rs.next()) {
				answer = rs.getString(1);
			}
			
			
		}  catch(SQLException se) {
			System.out.println("SQL����");
		} finally {
			
			try {
				if(stmt2 != null)
					stmt2.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				if(conn2 != null)
					conn2.close();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return answer;
	}
}
