package dao;

import java.util.List;

import entities.MarksReport;
import entities.StudentResult;
import exceptions.DatabaseException;

public interface MarksReportDAO {
	MarksReport addReports(MarksReport report) throws DatabaseException;
	List<StudentResult> getAllResults() throws DatabaseException; 
	StudentResult getResultsByRollNo(Long rollno) throws DatabaseException;
	List<StudentResult> getTop10() throws DatabaseException; 
	MarksReport updateReport(MarksReport report) throws DatabaseException;
	MarksReport deleteReport(MarksReport report);
}
