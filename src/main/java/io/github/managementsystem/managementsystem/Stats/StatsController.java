package io.github.managementsystem.managementsystem.Stats;

import io.github.managementsystem.managementsystem.Courses.CourseService;
import io.github.managementsystem.managementsystem.Students.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stats")
public class StatsController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDashboardStats() {
        Map<String, Integer> response = new HashMap<>();
        Integer nStudents = studentService.getTotalStudents();
        Integer nCourses = courseService.getTotalCourses();
        response.put("nStudents", nStudents);
        response.put("nCourses", nCourses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/chart")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDashboardChartStats() {
        List<Stats> statsList = courseService.getDashboardChartStats();
        return new ResponseEntity<>(statsList, HttpStatus.OK);
    }
}
