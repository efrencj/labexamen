package cat.tecnocampus.exam25students.application.Service;


import cat.tecnocampus.exam25students.domain.college.Course;
import cat.tecnocampus.exam25students.domain.college.Lesson;
import cat.tecnocampus.exam25students.domain.exceptions.CourseNotFoundException;
import cat.tecnocampus.exam25students.domain.exceptions.LessonPositionOutOfBoundsException;
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
        return courseRepository.findWithLessonsById(id).orElse(null);
    }

    @Transactional
    public void addLessonToCourse(Long courseId, Lesson lesson, int position) {
        Course course = courseRepository.findWithLessonsById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        try {
            course.addLesson(lesson, position);
        } catch (LessonPositionOutOfBoundsException e) {
            throw e;
        }

        courseRepository.save(course);
    }
}