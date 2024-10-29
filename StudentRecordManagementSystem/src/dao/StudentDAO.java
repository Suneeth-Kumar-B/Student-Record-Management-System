package dao;

import java.util.List;

import entities.Student;
import exceptions.DatabaseException;

public interface StudentDAO {
	Student addStudent(Student student) throws DatabaseException;
	List<Student> getAllStudents();
	Student deleteStudent(long rno) throws DatabaseException;
	Student getStudentByRno(long rno);
	Student updateStudent(Long rollno,Student student) throws DatabaseException;
}
