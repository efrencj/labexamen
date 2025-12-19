package cat.tecnocampus.exam25students.application.Service;

import cat.tecnocampus.exam25students.domain.social.Comment;
import cat.tecnocampus.exam25students.domain.social.Post;
import cat.tecnocampus.exam25students.persistence.CommentRepository;
import cat.tecnocampus.exam25students.persistence.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Comment> findCommentsAfter(Long postId, LocalDate date) {
        return commentRepository.findByPostIdAndDateOfCreationAfter(postId, date);
    }
}
