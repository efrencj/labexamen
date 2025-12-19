package cat.tecnocampus.exam25students;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCourseWithLessonsInOrder() throws Exception {
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Programming"))
                .andExpect(jsonPath("$.lessons.length()").value(3))
                .andExpect(jsonPath("$.lessons[0].order").value(1))
                .andExpect(jsonPath("$.lessons[1].order").value(2))
                .andExpect(jsonPath("$.lessons[2].order").value(3));
    }

    @Test
    void addLessonInValidPosition() throws Exception {
        String body = """
            {
              "title": "New Lesson",
              "position": 2
            }
            """;

        mockMvc.perform(post("/courses/1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/courses/1/lessons/2"));
    }

    @Test
    void addLessonWithInvalidPositionReturns400() throws Exception {
        String body = """
            {
              "title": "New Lesson",
              "position": 10
            }
            """;

        mockMvc.perform(post("/courses/1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addLessonWithInvalidTitleReturns400() throws Exception {
        String body = """
            {
              "title": "bad",
              "position": 2
            }
            """;

        mockMvc.perform(post("/courses/1/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}

