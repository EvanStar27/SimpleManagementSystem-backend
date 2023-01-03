package io.github.managementsystem.managementsystem.Subjects;

import io.github.managementsystem.managementsystem.Courses.CourseStudentMapping;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMapping_;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<CourseSubjectMapping> fetchAllSubjects();

    Subject createNewSubject(Subject subject);

    Subject updateSubject(Subject subject);

    Subject deleteSubjectById(BigInteger id);

    List<Subject> fetchSubjectsByCourseId(BigInteger id);

    List<CourseSubjectMapping_> fetchSubjectsByStudentId(BigInteger id);
}
