package cat.tecnocampus.exam25students.api;

import cat.tecnocampus.exam25students.application.DTO.CourseDTO;
import cat.tecnocampus.exam25students.application.DTO.CreateLessonRequest;
import cat.tecnocampus.exam25students.application.DTO.LessonDTO;
import cat.tecnocampus.exam25students.application.Service.CourseService;
import cat.tecnocampus.exam25students.domain.college.Course;
import cat.tecnocampus.exam25students.domain.college.Lesson;
import cat.tecnocampus.exam25students.domain.exceptions.CourseNotFoundException;
import cat.tecnocampus.exam25students.domain.exceptions.LessonPositionOutOfBoundsException;
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
        List<Map<String, String>> violations = new ArrayList<>();

        if (request.getTitle() == null || request.getTitle().length() < 5) {
            violations.add(Map.of("field", "title", "message", "The title must be at least 5 characters long"));
        }

        Lesson lesson = new Lesson(request.getTitle());
        if (!lesson.startsWithUppercaseLetter()) {
            violations.add(Map.of("field", "title", "message", "The title must start with a capital letter"));
        }

        if (!violations.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("violations", violations);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            courseService.addLessonToCourse(courseId, lesson, request.getPosition());

            String location = "http://localhost:8080/courses/" + courseId + "/lessons/" + request.getPosition();
            return ResponseEntity.created(URI.create(location)).build();

        } catch (LessonPositionOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Lesson position out of bounds");
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Course not found");
        }
    }
}

