package SERVER;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sql extends Thread {
	
	public Sql () throws Exception{
		this.start();
	}
	
	public void run () {
		
//		String user = "root";
//		String pwd = "";
//		String schema = "chatjava";
//
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		    String urlDriver = "jdbc:mysql://localhost:3306/" + schema + "?" + "user="
//		                                                 + user + "&password=" + pwd + "";
//		    Connection conn = DriverManager.getConnection(urlDriver);
//		}
//		catch(SQLException | ClassNotFoundException ex) {
//		    ex.printStackTrace();
//		}
	}

}
