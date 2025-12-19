package cat.tecnocampus.exam25students.api;

import cat.tecnocampus.exam25students.application.DTO.CourseDTO;
import cat.tecnocampus.exam25students.application.DTO.CreateLessonRequest;
import cat.tecnocampus.exam25students.application.DTO.LessonDTO;
import cat.tecnocampus.exam25students.application.Service.CourseService;
import cat.tecnocampus.exam25students.domain.college.Course;
import cat.tecnocampus.exam25students.domain.college.Lesson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourse(courseId);

        if (course == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Course not found");
        }

        List<LessonDTO> lessonDTOs = course.getLessons().stream()
                .sorted(Comparator.comparing(Lesson::getPosition))
                .map(lesson -> new LessonDTO(lesson.getPosition(), lesson.getTitle()))
                .collect(Collectors.toList());


        CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName(), lessonDTOs);
        return ResponseEntity.ok(courseDTO);
    }

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<?> addLesson(@PathVariable Long courseId,
                                       @RequestBody CreateLessonRequest request) {
        try {
            Lesson lesson = new Lesson(request.getTitle());
            courseService.addLessonToCourse(courseId, lesson, request.getPosition());

            String location = "http://localhost:8080/courses/" + courseId + "/lessons/" + request.getPosition();
            return ResponseEntity.created(URI.create(location)).build();

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Lesson position out of bounds")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Lesson position out of bounds");
            } else if (e.getMessage().equals("Course not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Course not found");
            } else {
                Map<String, Object> response = new HashMap<>();
                List<Map<String, String>> violations = new ArrayList<>();

                Map<String, String> violation = new HashMap<>();
                violation.put("field", "title");
                violation.put("message", e.getMessage());
                violations.add(violation);

                response.put("violations", violations);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
    }
}

