package service;

import java.util.List;

import dao.StudentDAO;
import dao.StudentDAOImpl;
import entities.Student;
import exceptions.DatabaseException;

public class StudentServiceImpl implements StudentService {
    static StudentDAO studentDAO = new StudentDAOImpl();

    @Override
    public Student addStudent(Student student) throws DatabaseException{
    	return studentDAO.addStudent(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public Student deleteStudent(long rno) throws DatabaseException {
        return studentDAO.deleteStudent(rno);
    }

    @Override
    public Student getStudentByRno(long rno) {
        return studentDAO.getStudentByRno(rno);
    }

    @Override
    public Student updateStudent(Long rollno, Student student) throws DatabaseException{
        return studentDAO.updateStudent(rollno, student);
    }
}
