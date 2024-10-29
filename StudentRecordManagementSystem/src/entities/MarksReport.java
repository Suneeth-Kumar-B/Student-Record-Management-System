package entities;

public class MarksReport {
	Long rollno;
	int std;
	Marks marks;
	double total;
	double average;
	double percentage;
	public Long getRollno() {
		return rollno;
	}
	public void setRollno(Long rollno) {
		this.rollno = rollno;
	}
	public int getStd() {
		return std;
	}
	public void setStd(int std) {
		this.std = std;
	}
	public Marks getMarks() {
		return marks;
	}
	public void setMarks(Marks marks) {
		this.marks = marks;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public MarksReport(Long rollno, int std, Marks marks, double total, double average, double percentage) {
		super();
		this.rollno = rollno;
		this.std = std;
		this.marks = marks;
		this.total = total;
		this.average = average;
		this.percentage = percentage;
	}
	public MarksReport() {
		super();
	}
	
}
