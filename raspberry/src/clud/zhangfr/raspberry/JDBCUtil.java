package clud.zhangfr.raspberry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url ="jdbc:mysql://192.168.0.105:3306/raspberry";
	private static String userName = "root";
	private static String password = "wszfr";
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException  {
		return  DriverManager.getConnection(url,userName,password);
	}
}
