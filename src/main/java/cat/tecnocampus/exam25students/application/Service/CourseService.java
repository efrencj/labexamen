package cat.tecnocampus.exam25students.application.Service;


import cat.tecnocampus.exam25students.domain.college.Course;
import cat.tecnocampus.exam25students.domain.college.Lesson;
import cat.tecnocampus.exam25students.persistence.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addLessonToCourse(Long courseId, Lesson lesson, int position) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        lesson.validateTitle();
        course.addLesson(lesson, position);
        courseRepository.save(course);
    }
}