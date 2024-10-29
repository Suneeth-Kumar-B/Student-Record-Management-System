package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DBConn {
	static String CREATEDATABASE = "CREATE DATABASE IF NOT EXISTS SRMS";
	
	static String USEDB = "USE SRMS";
	
	static String STUDRESTABLE = "CREATE TABLE IF NOT EXISTS StudentResults "
			+ "(RollNumber decimal(10,0), "
			+ "Standard int, "
			+ "Mathematics DOUBLE, "
			+ "Computers DOUBLE, "
			+ "Astronomy DOUBLE, "
			+ "GeneralScience DOUBLE,"
			+ "SocialStudies DOUBLE, TotalMarks INT, "
			+ "AverageMarks DOUBLE, "
			+ "Percentage DOUBLE, "
			+ "FOREIGN KEY (RollNumber) REFERENCES student(rno))";
	
	static String ADMINTABLE = "CREATE TABLE IF NOT EXISTS admin ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "name VARCHAR(50),"
            + "username VARCHAR(50) UNIQUE, "
            + "password VARCHAR(50)"
            + ")";
	
	static String STUDRTABLE = "CREATE TABLE IF NOT EXISTS student("
			+ "rno decimal(10,0) primary key,"
			+ "name varchar(50),"
			+ "phone varchar(50),"
			+ "addr varchar(50),"
			+ "dob varchar(50),"
			+ "email varchar(50),"
			+ "CONSTRAINT CompositeKey UNIQUE (phone, email))";
	
	static private final String URL="jdbc:mysql://localhost:3306/srms";
	static private final String USERNAME="root";
	static private final String PASSWORD="gaya3";
	static Connection conn=null;
	
	public static Connection getConnection() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement stmt = conn.prepareStatement(CREATEDATABASE);
			PreparedStatement stmt1 = conn.prepareStatement(STUDRTABLE);
			PreparedStatement stmt2 = conn.prepareStatement(STUDRESTABLE);
			PreparedStatement stmt3 = conn.prepareStatement(ADMINTABLE);
			stmt.execute();
			conn.prepareStatement(USEDB).execute();
;			stmt1.execute();
			stmt2.execute();
			stmt3.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}