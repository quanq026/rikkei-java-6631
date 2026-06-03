package model;

import java.math.BigDecimal;

public class Enrollment {
    private int studentId;
    private int courseId;
    private BigDecimal grade;

    public Enrollment() {
    }

    public Enrollment(int studentId, int courseId, BigDecimal grade) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public BigDecimal getGrade() {
        return grade;
    }
}
