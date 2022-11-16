package io.github.managementsystem.managementsystem.Students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    @Autowired
    public StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllStudents(
            @RequestParam(required = false) String name
    ) {
        List<Student> studentList = studentService.fetchAll(name);
        return ResponseEntity.ok(studentList);
    }

    @GetMapping(path = "/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getStudentById(@PathVariable("studentId") BigInteger studentId) {
        Map<String, String> errors = new HashMap<>();
        Student student = studentService.findById(studentId);

        if (student == null) {
            errors.put("message", "Student Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveStudent(@Valid @RequestBody Student student, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(f -> {
                        errors.put(f.getField(), f.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStudentById(
            @PathVariable("studentId") BigInteger studentId,
            @Valid @RequestBody Student student,
            BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(f -> {
                        errors.put(f.getField(), f.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        student.setStudentId(studentId);
        Student updatedStudent = studentService.updateStudent(student);

        if (updatedStudent == null) {
            errors.put("message", "Student Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudentById(@PathVariable("studentId") BigInteger studentId) {
        Map<String, String> errors = new HashMap<>();
        Student deletedStudent = studentService.deleteStudent(studentId);

        if (deletedStudent == null) {
            errors.put("message", "Student Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedStudent, HttpStatus.OK);
    }
}
