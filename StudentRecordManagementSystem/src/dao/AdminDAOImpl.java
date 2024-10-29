package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import entities.Admin;
import exceptions.DatabaseException;
import helpers.LoginIdGenerator;

public class AdminDAOImpl implements AdminDAO {
	static Connection conn = null;
	
	static String login_query = "select * from admin where username = ? and password = ?";
	static String register_query = "insert into admin "
			+ "(id, name, username,password)"
			+ "values (?,?,?,?)";
	
	@Override
	public boolean login(Admin admin) {
		int rows=0;
		try {
			conn=DBConn.getConnection();
			PreparedStatement statement = conn.prepareStatement(login_query);
			statement.setString(1, admin.getUsername());
			statement.setString(2, admin.getPassword());
			
			ResultSet result=statement.executeQuery();
			while(result.next())
				rows++;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rows==1?true:false;
	}

	@Override
	public Admin register(Admin admin) throws DatabaseException{
		int id = LoginIdGenerator.getNextNumber();
		try {
			conn=DBConn.getConnection();
			PreparedStatement statement = conn.prepareStatement(register_query);
			admin.setLoginId(id);
			statement.setInt(1,admin.getLoginId());
			statement.setString(2, admin.getName());
			statement.setString(3, admin.getUsername());
			statement.setString(4, admin.getPassword());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Admin with Details Already Exist");
		}
		return admin;
	}
}
