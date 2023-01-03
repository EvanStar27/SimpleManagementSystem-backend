package io.github.managementsystem.managementsystem.Courses;

import io.github.managementsystem.managementsystem.Students.Student;
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

@RestController
@CrossOrigin
@RequestMapping(path = "/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> fetchAllCourses(
            @RequestParam(required = false) String name
    ) {
        List<Course> courseList = courseService.fetchAllCourses(name);
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewCourse(
            @RequestBody Course course,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(f -> {
                        errors.put(f.getField(), f.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Course savedCourse = courseService.createNewCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCourse(
            @PathVariable("courseId") BigInteger courseId,
            @RequestBody Course course,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(f -> {
                        errors.put(f.getField(), f.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        course.setCourseId(courseId);
        Course updatedCourse = courseService.updateCourse(course);

        if (updatedCourse == null) {
            errors.put("message", "Course Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(
            @PathVariable("courseId") BigInteger courseId,
            @RequestBody Course course,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();
        Course deletedCourse = courseService.deleteCourse(courseId);

        if (deletedCourse == null) {
            errors.put("message", "Course Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedCourse, HttpStatus.OK);
    }

    @GetMapping("/applicants/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> fetchCourseApplicants(
            @PathVariable("courseId") BigInteger courseId
    ) {
        Map<String, String> errors = new HashMap<>();
        List<CourseStudentMapping> courseStudentMapping =
                courseService.fetchCourseApplicants(courseId);

        if (courseStudentMapping == null) {
            errors.put("message", "Course Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(courseStudentMapping, HttpStatus.OK);
    }

    @DeleteMapping("/applicants/remove/{csMappingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourseApplicants(
            @PathVariable("csMappingId") BigInteger csMappingId
    ) {
        Map<String, String> errors = new HashMap<>();
        CourseStudentMapping courseStudentMapping =
                courseService.deleteCourseApplicants(csMappingId);

        if (courseStudentMapping == null) {
            errors.put("message", "Course Student Relation Not Found");
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(courseStudentMapping, HttpStatus.OK);
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> enrollToCourse(
            @RequestBody CourseStudentMapping courseStudentMapping,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();
        CourseStudentMapping savedCourseStudentMapping = courseService.enrollToCourse(courseStudentMapping);

        if (savedCourseStudentMapping == null) {
            errors.put("message", "Invalid Post Request");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedCourseStudentMapping, HttpStatus.CREATED);
    }

    @PutMapping("/enroll/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> updateEnrolledCourse(
            @PathVariable("id") BigInteger csMappingId,
            @RequestBody CourseStudentMapping courseStudentMapping,
            BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();
        CourseStudentMapping updatedCourseStudentMapping = courseService.updateEnrolledCourse(
                csMappingId, courseStudentMapping);

//        System.out.println(csMappingId + ": " + courseStudentMapping);
        if (updatedCourseStudentMapping == null) {
            errors.put("message", "Invalid Request");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedCourseStudentMapping, HttpStatus.OK);
//        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }
}
