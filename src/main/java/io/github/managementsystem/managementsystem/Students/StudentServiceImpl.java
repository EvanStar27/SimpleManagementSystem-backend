package io.github.managementsystem.managementsystem.Students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> fetchAll(String name) {
        return studentRepository.fetchAll(name);
    }

    @Override
    public Student findById(BigInteger studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.update(student);
    }

    @Override
    public Student deleteStudent(BigInteger studentId) {
        return studentRepository.delete(studentId);
    }

    @Override
    public Integer getTotalStudents() {
        return studentRepository.getTotalStudents();
    }
}
