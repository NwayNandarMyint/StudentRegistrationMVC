package StudentRegistrationMvc.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	static Connection con = null;
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String db = "jdbc:mysql://localhost:3306/mvcproject";
			String username = "root";
			String password = "root";
			con = DriverManager.getConnection(db,username,password);
		}catch(ClassNotFoundException e) {
			System.out.println("Driver class not found " + e);
		}catch(SQLException e){
			System.out.println("SQL not found " + e);
		}
		return con;
	}
	
}
