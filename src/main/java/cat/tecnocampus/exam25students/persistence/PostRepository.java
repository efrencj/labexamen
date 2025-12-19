package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.social.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "comments")
    Optional<Post> findWithCommentsById(Long id);
}
