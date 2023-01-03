package io.github.managementsystem.managementsystem.Subjects;

import io.github.managementsystem.managementsystem.Courses.CourseStudentMapping;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMapping_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> fetchAllSubjects() {
        return new ResponseEntity<>(subjectService.fetchAllSubjects(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewSubject(
            @RequestBody Subject subject,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(fieldError -> {
                        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Subject savedSubject = subjectService.createNewSubject(subject);
        return new ResponseEntity<>(savedSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSubject(
            @PathVariable("id") BigInteger id,
            @RequestBody Subject subject,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(fieldError -> {
                        errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Subject updatedSubject = subjectService.updateSubject(subject);
        return new ResponseEntity<>(updatedSubject, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubjectById(@PathVariable("id") BigInteger id) {
        Map<String, String> error = new HashMap<>();
        Subject deletedSubject = subjectService.deleteSubjectById(id);

        if (deletedSubject == null) {
            error.put("message", "Subject Not Found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedSubject, HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public List<Subject> fetchSubjectsByCourseId(
            @PathVariable("id") BigInteger id
    ) {
        return subjectService.fetchSubjectsByCourseId(id);
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public List<CourseSubjectMapping_> fetchSubjectsByStudentId(
            @PathVariable("id") BigInteger id
    ) {
        return subjectService.fetchSubjectsByStudentId(id);
    }
}
