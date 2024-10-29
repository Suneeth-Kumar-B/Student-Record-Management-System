package entities;

public class Student extends Person{
	Long rollno;
	public Student(String name, String phoneNumber, String address,String dateOfBirth, String email) {
		super(name, phoneNumber, address, dateOfBirth, email);
	}
	public Student(Long rollno, String name, String phoneNumber, String address, String dateOfBirth, String email) {
		super(name, phoneNumber, address, dateOfBirth, email);
		this.rollno = rollno;
	}
	public Student() {}
	public void setRollno(Long rollno) {
		this.rollno=rollno;
	}
	public Long getRollno() {
		return rollno;
	}
}
