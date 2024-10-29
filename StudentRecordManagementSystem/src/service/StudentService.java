package service;

import java.util.List;

import entities.Student;
import exceptions.DatabaseException;

public interface StudentService {
	public Student addStudent(Student student) throws DatabaseException;
	public List<Student> getAllStudents();
	Student deleteStudent(long rno) throws DatabaseException;
	Student getStudentByRno(long rno);
	Student updateStudent(Long rollno,Student student)throws DatabaseException;
}
