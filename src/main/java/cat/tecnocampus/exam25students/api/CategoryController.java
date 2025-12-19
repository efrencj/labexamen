package cat.tecnocampus.exam25students.api;

import cat.tecnocampus.exam25students.application.Service.CategoryService;
import cat.tecnocampus.exam25students.domain.shop.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryName}/products")
    public ResponseEntity<List<Map<String, Object>>> getProductsByCategory(@PathVariable String categoryName) {
        List<Product> products = categoryService.findProductsByCategoryHierarchy(categoryName);

        List<Map<String, Object>> body = products.stream()
                .map(product -> Map.of(
                        "name", product.getName(),
                        "price", product.getPrice(),
                        "categories", product.getCategories().stream()
                                .map(cat -> cat.getName())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }
}
