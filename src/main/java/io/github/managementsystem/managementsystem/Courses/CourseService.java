package io.github.managementsystem.managementsystem.Courses;

import io.github.managementsystem.managementsystem.Stats.Stats;

import java.math.BigInteger;
import java.util.List;

public interface CourseService {
    List<Course> fetchAllCourses(String name);

    Course createNewCourse(Course course);

    Course updateCourse(Course course);

    Course deleteCourse(BigInteger courseId);

    List<CourseStudentMapping> fetchCourseApplicants(BigInteger courseId);

    CourseStudentMapping deleteCourseApplicants(BigInteger csMappingId);

    CourseStudentMapping enrollToCourse(CourseStudentMapping courseStudentMapping);

    Integer getTotalCourses();

    List<Stats> getDashboardChartStats();
}
