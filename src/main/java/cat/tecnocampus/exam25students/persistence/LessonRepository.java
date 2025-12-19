package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.college.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}