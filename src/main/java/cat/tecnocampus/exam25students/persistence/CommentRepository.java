package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.social.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdAndDateOfCreationAfter(Long postId, LocalDate date);
}
