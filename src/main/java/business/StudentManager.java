package business;

import model.Course;
import model.Enrollment;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    public boolean addStudent(Student student) throws SQLException {
        if (existsByValue("student", "email", student.getEmail())) return false;
        String sql = "INSERT INTO student(name, email) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean addCourse(Course course) throws SQLException {
        if (existsByValue("course", "title", course.getTitle())) return false;
        String sql = "INSERT INTO course(title, credits) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getCredits());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean enrollStudent(int studentId, int courseId) throws SQLException {
        if (!existsById("student", studentId) || !existsById("course", courseId)) return false;
        String sql = "INSERT INTO enrollment(student_id, course_id, grade) VALUES (?, ?, NULL)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            return statement.executeUpdate() > 0;
        }
    }

    public List<String> listStudentsAndGrades() throws SQLException {
        List<String> rows = new ArrayList<>();
        String sql = "SELECT s.name, c.title, e.grade FROM enrollment e " +
                "JOIN student s ON e.student_id = s.id JOIN course c ON e.course_id = c.id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String grade = resultSet.getBigDecimal("grade") == null ? "Chưa có điểm" : resultSet.getBigDecimal("grade").toString();
                rows.add(resultSet.getString("name") + " - " + resultSet.getString("title") + " - " + grade);
            }
        }
        return rows;
    }

    public boolean updateStudentGrade(Enrollment enrollment) throws SQLException {
        String sql = "UPDATE enrollment SET grade = ? WHERE student_id = ? AND course_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, enrollment.getGrade());
            statement.setInt(2, enrollment.getStudentId());
            statement.setInt(3, enrollment.getCourseId());
            return statement.executeUpdate() > 0;
        }
    }

    private boolean existsByValue(String table, String column, String value) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM " + table + " WHERE " + column + " = ?")) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private boolean existsById(String table, int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM " + table + " WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
