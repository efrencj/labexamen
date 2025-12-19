package cat.tecnocampus.exam25students;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listProductsInCategoryHierarchy() throws Exception {
        mockMvc.perform(get("/categories/Books/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void nonExistingCategoryReturnsEmptyList() throws Exception {
        mockMvc.perform(get("/categories/Unknown/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void addCategoryToProduct() throws Exception {
        mockMvc.perform(put("/products/1/categories/Fiction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories").isNotEmpty());
    }

    @Test
    void addCategoryWhenProductMissingReturns404() throws Exception {
        mockMvc.perform(put("/products/99/categories/Fiction"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }

    @Test
    void addCategoryWhenCategoryMissingReturns404() throws Exception {
        mockMvc.perform(put("/products/1/categories/Unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }
}
