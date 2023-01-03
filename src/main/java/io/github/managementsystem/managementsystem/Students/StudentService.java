package io.github.managementsystem.managementsystem.Students;

import java.math.BigInteger;
import java.util.List;

public interface StudentService {
    List<Student> fetchAll(String name);

    Student findById(BigInteger studentId);

    Student saveStudent(Student student);

    Student updateStudent(Student student);

    Student deleteStudent(BigInteger studentId);

    Integer getTotalStudents();

    Student findByUserId(BigInteger userId);
}
