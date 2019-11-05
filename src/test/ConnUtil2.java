package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnUtil2 {

	 static {
		 try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
		 } catch(ClassNotFoundException cnfe) {
			 cnfe.printStackTrace();
			 System.out.println("해당 클래스를 찾을 수 없습니다." +cnfe.getMessage());
		 }

	 }
	 public static Connection getConnection() throws SQLException {
		  String url = "jdbc:oracle:thin:@localhost:1521:xe";
		  return DriverManager.getConnection(url, "scott", "TIGER");
	 }
}