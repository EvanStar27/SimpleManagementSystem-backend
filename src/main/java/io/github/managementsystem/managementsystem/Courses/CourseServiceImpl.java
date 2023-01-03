package io.github.managementsystem.managementsystem.Courses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMappingRepository_;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMapping_;
import io.github.managementsystem.managementsystem.Stats.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseSubjectMappingRepository_ courseSubjectMappingRepository;

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
        ObjectMapper mapper = new ObjectMapper();
        List<BigInteger> subjectIds = new ArrayList<>();

        try {
            subjectIds = mapper.readValue(courseStudentMapping.getSubjects(),
                    new TypeReference<List<BigInteger>>() {
                    });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        CourseStudentMapping csMappingObj = courseRepository.enrollToCourse(courseStudentMapping);

        List<CourseSubjectMapping_> courseSubjectMappingList = subjectIds.stream().map((id) -> CourseSubjectMapping_.builder()
                .csMappingId(csMappingObj.getCsMappingId()).subjectId(id).build()).toList();

        courseSubjectMappingList.forEach(cSubMapping -> {
            courseSubjectMappingRepository.save(cSubMapping);
        });
        return csMappingObj;
    }

    @Override
    public Integer getTotalCourses() {
        return courseRepository.getTotalCourses();
    }

    @Override
    public List<Stats> getDashboardChartStats() {
        return courseRepository.getDashboardChartStats();
    }

    @Override
    public CourseStudentMapping updateEnrolledCourse(BigInteger csMappingId, CourseStudentMapping courseStudentMapping) {
        return courseRepository.updateEnrolledCourse(csMappingId, courseStudentMapping);
    }


}
