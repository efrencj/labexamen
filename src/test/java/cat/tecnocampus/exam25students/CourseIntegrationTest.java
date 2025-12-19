package cat.tecnocampus.exam25students;

import cat.tecnocampus.exam25students.domain.college.Course;
import cat.tecnocampus.exam25students.domain.college.Lesson;
import cat.tecnocampus.exam25students.persistence.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CourseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();

        Course course = new Course("Programming");
        Lesson lesson1 = new Lesson("Introduction to Programming");
        Lesson lesson2 = new Lesson("Variables and Data Types");
        Lesson lesson3 = new Lesson("Control Structures");

        course.addLesson(lesson1, 1);
        course.addLesson(lesson2, 2);
        course.addLesson(lesson3, 3);

        courseRepository.save(course);
    }

    @Test
    void testGetCourse() throws Exception {
        Long courseId = courseRepository.findAll().get(0).getId();

        mockMvc.perform(get("/courses/" + courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId))
                .andExpect(jsonPath("$.name").value("Programming"))
                .andExpect(jsonPath("$.lessons[0].order").value(1))
                .andExpect(jsonPath("$.lessons[0].title").value("Introduction to Programming"))
                .andExpect(jsonPath("$.lessons[1].order").value(2))
                .andExpect(jsonPath("$.lessons[2].order").value(3));
    }

    @Test
    void testGetCourseNotFound() throws Exception {
        mockMvc.perform(get("/courses/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Course not found"));
    }

    @Test
    void testAddLesson() throws Exception {
        Long courseId = courseRepository.findAll().get(0).getId();

        String requestBody = "{\"title\":\"New Lesson\",\"position\":3}";

        mockMvc.perform(post("/courses/" + courseId + "/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testAddLessonPositionOutOfBounds() throws Exception {
        Long courseId = courseRepository.findAll().get(0).getId();

        String requestBody = "{\"title\":\"New Lesson\",\"position\":10}";

        mockMvc.perform(post("/courses/" + courseId + "/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Lesson position out of bounds"));
    }

    @Test
    void testAddLessonInvalidTitle() throws Exception {
        Long courseId = courseRepository.findAll().get(0).getId();

        String requestBody = "{\"title\":\"abc\",\"position\":1}";

        mockMvc.perform(post("/courses/" + courseId + "/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray());
    }

    @Test
    void testAddLessonTitleNotCapitalized() throws Exception {
        Long courseId = courseRepository.findAll().get(0).getId();

        String requestBody = "{\"title\":\"lowercase title\",\"position\":1}";

        mockMvc.perform(post("/courses/" + courseId + "/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray());
    }
}