package exercise.controller;

import exercise.model.Course;
import exercise.service.CourseService;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "")
    public Iterable<Course> getCourses() {
        return courseService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseService.findById(id);
    }

    @GetMapping(path = "/{id}/previous")
    public Iterable<Course> findPreviousCourses(@PathVariable Long id) {
        return courseService.findParentCourses(id);
    }
}
