package presentation;

import business.StudentManager;
import model.Course;
import model.Enrollment;
import model.Student;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentManager studentManager = new StudentManager();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = inputInteger("Lựa chọn của bạn: ");
            try {
                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: addCourse(); break;
                    case 3: enrollStudent(); break;
                    case 4: display(studentManager.listStudentsAndGrades()); break;
                    case 5: updateGrade(); break;
                    case 0: System.out.println("Thoát chương trình!"); break;
                    default: System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (SQLException e) {
                System.out.println("Lỗi thao tác cơ sở dữ liệu: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("====== MENU SINH VIÊN - KHÓA HỌC ======");
        System.out.println("1. Thêm sinh viên");
        System.out.println("2. Thêm khóa học");
        System.out.println("3. Ghi danh sinh viên");
        System.out.println("4. Hiển thị sinh viên và điểm");
        System.out.println("5. Cập nhật điểm");
        System.out.println("0. Thoát");
    }

    private static void addStudent() throws SQLException {
        String name = inputRequiredString("Nhập tên sinh viên: ");
        String email = inputRequiredString("Nhập email: ");
        System.out.println(studentManager.addStudent(new Student(0, name, email)) ? "Thêm sinh viên thành công!" : "Email đã tồn tại!");
    }

    private static void addCourse() throws SQLException {
        String title = inputRequiredString("Nhập tên khóa học: ");
        int credits = inputInteger("Nhập số tín chỉ: ");
        System.out.println(studentManager.addCourse(new Course(0, title, credits)) ? "Thêm khóa học thành công!" : "Tên khóa học đã tồn tại!");
    }

    private static void enrollStudent() throws SQLException {
        int studentId = inputInteger("Nhập ID sinh viên: ");
        int courseId = inputInteger("Nhập ID khóa học: ");
        System.out.println(studentManager.enrollStudent(studentId, courseId) ? "Ghi danh thành công!" : "Sinh viên hoặc khóa học không tồn tại!");
    }

    private static void updateGrade() throws SQLException {
        int studentId = inputInteger("Nhập ID sinh viên: ");
        int courseId = inputInteger("Nhập ID khóa học: ");
        BigDecimal grade = inputBigDecimal("Nhập điểm: ");
        System.out.println(studentManager.updateStudentGrade(new Enrollment(studentId, courseId, grade)) ? "Cập nhật điểm thành công!" : "Không tìm thấy ghi danh!");
    }

    private static void display(List<String> values) {
        if (values.isEmpty()) {
            System.out.println("Không có dữ liệu!");
            return;
        }
        values.forEach(System.out::println);
    }

    private static String inputRequiredString(String message) {
        System.out.print(message);
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) throw new IllegalArgumentException("Không được nhập trống!");
        return value;
    }

    private static int inputInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    private static BigDecimal inputBigDecimal(String message) {
        while (true) {
            try {
                System.out.print(message);
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }
}
