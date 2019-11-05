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
		
		String val = table.getValueAt(row, 0).toString();	    // 선택된 것의 NUM값
		String ref = table.getValueAt(row, 7).toString();		// 선택된 것의 REF값
		String level = table.getValueAt(row, 8).toString();		// 선택된 것의 LEV값
		String seq = table.getValueAt(row, 9).toString();       // 선택된 것의 SEQ값
		
		no = Integer.parseInt(val);
		re = Integer.parseInt(ref);
		le = Integer.parseInt(level);
		se = Integer.parseInt(seq);
		
		System.out.println("선택된 행 = " + row + "값 = " + val+ "기본키 =" + no + "레벨 =" + le);
		//sel = select(le,se);
		
		} catch (Exception ee) {
			System.out.println("테이블에서 값 받아오는게 잘못 됨");
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
			System.out.println("삭제 완료");
		} else {
			System.out.println("삭제 실패");
		}
		} catch (SQLException see) {
			System.out.println("디비 삭제중 오류");
		}
		
		ArrayList<Board> data = TableInOut.selectAll();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		// 테이블 데이터 전부 삭제
		for(int i = model.getRowCount()-1; i >= 0; i--) {
			model.removeRow(i);
		}
		// 디비에서 값 불러와서 다시 삽입
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
	
	// 삭제하는 범위
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
			System.out.println("SQL오류");
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
