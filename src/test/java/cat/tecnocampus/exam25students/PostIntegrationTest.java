package cat.tecnocampus.exam25students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCommentsAfterDate() throws Exception {
        mockMvc.perform(get("/posts/1/comments").param("after", "2025-12-17"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postTitle").value("Lab internet"))
                .andExpect(jsonPath("$.comments.length()").value(2));
    }

    @Test
    void getCommentsAfterDateFilters() throws Exception {
        mockMvc.perform(get("/posts/1/comments").param("after", "2025-12-18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments.length()").value(1))
                .andExpect(jsonPath("$.comments[0].dateOfCreation").value("2025-12-19"));
    }

    @Test
    void getCommentsForMissingPostReturns404() throws Exception {
        mockMvc.perform(get("/posts/99/comments").param("after", "2025-12-18"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Post not found"));
    }
}
