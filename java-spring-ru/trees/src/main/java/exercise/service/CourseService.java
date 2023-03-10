package exercise.service;

import exercise.model.Course;

import java.util.List;

public interface CourseService {

    Course findById(Long id);

    Iterable<Course> findAllByIds(List<Long> ids);

    Iterable<Course> findAll();

    Iterable<Course> findParentCourses(Long id);
}
