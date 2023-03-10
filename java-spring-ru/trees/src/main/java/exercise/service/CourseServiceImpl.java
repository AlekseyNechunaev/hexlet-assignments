package exercise.service;

import exercise.handle.CourseNotFoundException;
import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new CourseNotFoundException("course with this id not found"));
    }

    @Override
    public Iterable<Course> findAllByIds(List<Long> ids) {
        return courseRepository.findAllById(ids);
    }

    @Override
    public Iterable<Course> findParentCourses(Long id) {
        Course course = findById(id);
        List<Long> parentIds = parseParentIds(course.getPath());
        if(!parentIds.isEmpty()) {
            return findAllByIds(parentIds);
        }
        return Collections.emptyList();
    }

    @Override
    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }

    private List<Long> parseParentIds(String path) {
        if (StringUtils.hasLength(path)) {
            return Arrays.stream(path.split("\\."))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
