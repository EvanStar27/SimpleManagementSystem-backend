package io.github.managementsystem.managementsystem.Courses;

import io.github.managementsystem.managementsystem.Stats.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> fetchAllCourses(String name) {
        return courseRepository.findAll(name);
    }

    @Override
    public Course createNewCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.update(course);
    }

    @Override
    public Course deleteCourse(BigInteger courseId) {
        return courseRepository.delete(courseId);
    }

    @Override
    public List<CourseStudentMapping> fetchCourseApplicants(BigInteger courseId) {
        return courseRepository.findCourseApplicants(courseId);
    }

    @Override
    public CourseStudentMapping deleteCourseApplicants(BigInteger csMappingId) {
        return courseRepository.deleteCourseApplicants(csMappingId);
    }

    @Override
    public CourseStudentMapping enrollToCourse(CourseStudentMapping courseStudentMapping) {
        return courseRepository.enrollToCourse(courseStudentMapping);
    }

    @Override
    public Integer getTotalCourses() {
        return courseRepository.getTotalCourses();
    }

    @Override
    public List<Stats> getDashboardChartStats() {
        return courseRepository.getDashboardChartStats();
    }


}
