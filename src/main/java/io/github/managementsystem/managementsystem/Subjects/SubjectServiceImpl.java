package io.github.managementsystem.managementsystem.Subjects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.managementsystem.managementsystem.Courses.CourseStudentMapping;
import io.github.managementsystem.managementsystem.Exceptions.NotFoundException;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMappingRepository_;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMapping_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseSubjectMappingRepository_ courseSubjectMappingRepository;

    @Override
    public List<CourseSubjectMapping> fetchAllSubjects() {
        return subjectRepository.fetchAllSubjects();
    }

    @Override
    public Subject createNewSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Subject subject) {
        return subjectRepository.update(subject);
    }

    @Override
    public Subject deleteSubjectById(BigInteger id) {
        return subjectRepository.delete(id);
    }

    @Override
    public List<Subject> fetchSubjectsByCourseId(BigInteger id) {
        return subjectRepository.fetchSubjectsByCourseId(id);
    }

    @Override
    public List<CourseSubjectMapping_> fetchSubjectsByStudentId(BigInteger id) {
        return subjectRepository.fetchSubjectsByStudentId(id);
    }
}
