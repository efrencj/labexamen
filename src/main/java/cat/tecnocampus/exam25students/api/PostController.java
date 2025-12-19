package cat.tecnocampus.exam25students.api;

import cat.tecnocampus.exam25students.application.Service.PostService;
import cat.tecnocampus.exam25students.domain.social.Comment;
import cat.tecnocampus.exam25students.domain.social.Post;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getCommentsAfter(@PathVariable Long postId,
                                              @RequestParam("after") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after) {
        Post post = postService.findPost(postId);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Post not found");
        }

        List<Comment> comments = postService.findCommentsAfter(postId, after);
        Map<String, Object> response = new HashMap<>();
        response.put("postTitle", post.getTitle());
        response.put("comments", comments.stream()
                .map(comment -> Map.of(
                        "id", comment.getId(),
                        "content", comment.getContent(),
                        "dateOfCreation", comment.getDateOfCreation().toString()
                )).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
