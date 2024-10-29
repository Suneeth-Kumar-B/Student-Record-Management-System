package service;

import java.util.List;

import dao.MarksReportDAO;
import dao.MarksReportDAOImpl;
import entities.MarksReport;
import entities.StudentResult;
import exceptions.DatabaseException;


public class MarksReportServiceImpl implements MarksReportService {
	MarksReportDAO marksReportDAO = new MarksReportDAOImpl();

	@Override
	public MarksReport addReports(MarksReport report) throws DatabaseException {
		return marksReportDAO.addReports(report);
	}

	@Override
	public List<StudentResult> getAllResults() throws DatabaseException {
		return marksReportDAO.getAllResults();
	}

	@Override
	public StudentResult getResultsByRollNo(Long rollno) throws DatabaseException {
		return marksReportDAO.getResultsByRollNo(rollno);
	}

	@Override
	public List<StudentResult> getTop10() throws DatabaseException {
		return marksReportDAO.getTop10();
	}

	@Override
	public MarksReport updateReport(MarksReport report) throws DatabaseException {
		return marksReportDAO.updateReport(report);
	}

	@Override
	public MarksReport deleteReport(MarksReport report) {
		return marksReportDAO.deleteReport(report);
	}

}
