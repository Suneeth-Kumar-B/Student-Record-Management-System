package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DBConn;
import entities.Student;
import exceptions.DatabaseException;
import helpers.RollNumberGenerator;

public class StudentDAOImpl implements StudentDAO {

    private static final String INSERT_QUERY = "INSERT INTO student (rno, name, phone, addr, dob, email) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM student";
    private static final String DELETE_QUERY = "DELETE FROM student WHERE rno = ?";
    private static final String SEARCH_QUERY = "SELECT * FROM student WHERE rno = ?";
    private static final String UPDATE_QUERY = "UPDATE student SET name = ?, phone = ?, addr = ?, email = ?, dob = ? WHERE rno = ?";

    @Override
    public Student addStudent(Student student)throws DatabaseException{
        Long rollNo = RollNumberGenerator.getNextNumber();
        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_QUERY)) {
        	student.setRollno(rollNo);
            statement.setLong(1, student.getRollno());
            statement.setString(2, student.getName());
            statement.setString(3, student.getPhoneNumber());
            statement.setString(4, student.getAddress());
            // Convert and set date to SQL Date
            statement.setString(5, student.getDateOfBirth());
            statement.setString(6, student.getEmail());
            statement.executeUpdate();
            System.out.println(student.getDateOfBirth());
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new DatabaseException("Student with email or phone already registered", e);           
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Student student = mapResultSetToStudent(result);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Student deleteStudent(long rno)throws DatabaseException {
    	Student student = new Student();
        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, rno);
            student = getStudentByRno(rno);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Student cannot be deleted. Delete his marks record first", e);
        }
        return student;
    }

    @Override
    public Student getStudentByRno(long rno) {
    	Student student = null;
        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(SEARCH_QUERY)) {
            statement.setLong(1, rno);
            try (ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    student = mapResultSetToStudent(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public Student updateStudent(Long rollno, Student student) throws DatabaseException{
        try (Connection conn = DBConn.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getPhoneNumber());
            statement.setString(3, student.getAddress());
            statement.setString(4, student.getEmail());
            statement.setString(5,student.getDateOfBirth());
            statement.setLong(6, rollno);
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DatabaseException("No student found with Roll No: " + rollno);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error updating student: " + e.getMessage(), e);
        }
        return student;
    }

    // Helper method to map ResultSet to Student object
    private Student mapResultSetToStudent(ResultSet result) throws SQLException {
        Student student = new Student();
        student.setRollno(result.getLong("rno"));
        student.setName(result.getString("name"));
        student.setPhoneNumber(result.getString("phone"));
        // Convert SQL Date to Date
        student.setDateOfBirth(result.getString("dob"));
        student.setAddress(result.getString("addr"));
        student.setEmail(result.getString("email"));
        return student;
    }
}
