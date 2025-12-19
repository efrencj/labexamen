package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.college.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = "lessons")
    Optional<Course> findWithLessonsById(Long id);
}
