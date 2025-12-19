package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.college.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
