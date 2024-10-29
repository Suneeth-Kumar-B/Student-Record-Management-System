package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DBConn;
import entities.Marks;
import entities.MarksReport;
import entities.StudentResult;
import exceptions.DatabaseException;

public class MarksReportDAOImpl implements MarksReportDAO {
	
	String insert_query = "insert into StudentResults values(?,?,?,?,?,?,?,?,?,?)";
	
	String getresults_query = "SELECT ROW_NUMBER() OVER (ORDER BY studentresults.Percentage DESC) AS Ranking,"
			+ " student.name, studentresults.*"
			+ " FROM student"
			+ " JOIN studentresults ON student.rno = studentresults.RollNumber"
			+ " ORDER BY studentresults.Percentage DESC";
	
	String getreultsbyrno_query = "SELECT student.name, studentresults.*, "
			+ "(SELECT COUNT(*) FROM studentresults AS sr"
			+ " WHERE sr.Percentage > studentresults.Percentage) + 1 AS Ranking FROM student "
			+ "JOIN studentresults ON student.rno = studentresults.RollNumber "
			+ "WHERE studentresults.RollNumber = ?";
	
	String top10_query = "SELECT ROW_NUMBER() OVER (ORDER BY studentresults.Percentage DESC) AS Ranking, student.name, studentresults.* "
			+ "FROM student "
			+ "JOIN studentresults ON student.rno = studentresults.RollNumber "
			+ "ORDER BY studentresults.Percentage DESC "
			+ "LIMIT 10";
	
	String updatereport_query = "update StudentResults "
			+ "SET Mathematics = ?, "
			+ "Computers = ?, "
			+ "Astronomy = ?, "
			+ "GeneralScience = ?, "
			+ "SocialStudies = ?, "
			+ "TotalMarks = ?, "
			+ "AverageMarks = ?, "
			+ "Percentage = ?"
			+ " where RollNumber = ?";
	
	String deletereport_query = "delete from StudentResults where RollNumber = ?";
	
	@Override
	public MarksReport addReports(MarksReport report) throws DatabaseException{
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement statement = conn.prepareStatement(insert_query);
			statement.setLong(1, report.getRollno());
			statement.setInt(2, report.getStd());
			statement.setDouble(3,report.getMarks().getMaths());
			statement.setDouble(4,report.getMarks().getComputers());
			statement.setDouble(5,report.getMarks().getAstronomy());
			statement.setDouble(6,report.getMarks().getGenscience());
			statement.setDouble(7,report.getMarks().getSocial());
			statement.setDouble(8, report.getTotal());
			statement.setDouble(9,report.getAverage());
			statement.setDouble(10, report.getPercentage());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("No Student with Roll Number Specified");
		}
		return report;
	}


	@Override
	public List<StudentResult> getAllResults() throws DatabaseException {
		List<StudentResult> res = new ArrayList<>();
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement statement = conn.prepareStatement(getresults_query);
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				Marks marks = new Marks(results.getDouble(5),results.getDouble(6),results.getDouble(7),results.getDouble(8),results.getDouble(9));
				MarksReport report = new MarksReport(results.getLong(3),results.getInt(4),marks,results.getDouble(10),results.getDouble(11),results.getDouble(12));
				StudentResult studentReport = new StudentResult(results.getInt(1),results.getString(2),report);
				res.add(studentReport);			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unexpected Error Occurred");
		}
		return res;
	}


	@Override
	public StudentResult getResultsByRollNo(Long rollno) throws DatabaseException {
	    StudentResult studReport = null;
	    try (Connection conn = DBConn.getConnection();
	         PreparedStatement statement = conn.prepareStatement(getreultsbyrno_query)) {
	         statement.setLong(1, rollno);
	        try (ResultSet results = statement.executeQuery()) {
	            if(results.next()) {
	            	Marks marks = new Marks(results.getDouble(4),results.getDouble(5),results.getDouble(6),results.getDouble(7),results.getDouble(8));
	            	MarksReport report = new MarksReport(results.getLong(2),results.getInt(3),marks,results.getDouble(9),results.getDouble(10),results.getDouble(11));
	                studReport = new StudentResult(results.getInt(12),results.getString(1),report);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new DatabaseException("Unexpected Error Occurred");
	    }
	    return studReport;
	}


	@Override
	public List<StudentResult> getTop10() throws DatabaseException {
		List<StudentResult> res = new ArrayList<>();
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement statement = conn.prepareStatement(top10_query);
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				Marks marks = new Marks(results.getDouble(5),results.getDouble(6),results.getDouble(7),results.getDouble(8),results.getDouble(9));
				MarksReport report = new MarksReport(results.getLong(3),results.getInt(4),marks,results.getDouble(10),results.getDouble(11),results.getDouble(12));
				StudentResult studReport =  new StudentResult(results.getInt(1),results.getString(2),report);
				res.add(studReport);			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unexpected Error Occurred");
		}
		return res;
	}


	@Override
	public MarksReport updateReport(MarksReport report) throws DatabaseException {
		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement statement = conn.prepareStatement(updatereport_query);
			statement.setDouble(1, report.getMarks().getMaths());
			statement.setDouble(2, report.getMarks().getComputers());
			statement.setDouble(3, report.getMarks().getAstronomy());
			statement.setDouble(4, report.getMarks().getGenscience());
			statement.setDouble(5, report.getMarks().getSocial());
			statement.setDouble(6, report.getTotal());
			statement.setDouble(7, report.getAverage());
			statement.setDouble(8, report.getPercentage());
			statement.setLong(9, report.getRollno());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Unable to Update Marks");
		}
		return report;		
	}


	@Override
	public MarksReport deleteReport(MarksReport report) {
		try(Connection conn = DBConn.getConnection()){
			PreparedStatement statement = conn.prepareStatement(deletereport_query);
			statement.setLong(1, report.getRollno());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return report;
	}
}
