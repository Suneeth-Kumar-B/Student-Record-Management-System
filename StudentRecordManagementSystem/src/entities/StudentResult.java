package entities;

public class StudentResult {
	int rank;
	String name;
	MarksReport report;
	public StudentResult(int rank, String name, MarksReport report) {
		super();
		this.rank = rank;
		this.name = name;
		this.report = report;
	}
	public StudentResult() {
		super();
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MarksReport getReport() {
		return report;
	}
	public void setReport(MarksReport report) {
		this.report = report;
	}
}