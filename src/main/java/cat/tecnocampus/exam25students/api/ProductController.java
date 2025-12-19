package cat.tecnocampus.exam25students.api;

import cat.tecnocampus.exam25students.application.Service.ProductService;
import cat.tecnocampus.exam25students.domain.shop.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PutMapping("/{productId}/categories/{categoryName}")
    public ResponseEntity<?> addCategoryToProduct(@PathVariable Long productId,
                                                  @PathVariable String categoryName) {
        try {
            Product product = productService.addCategory(productId, categoryName);
            Map<String, Object> body = Map.of(
                    "name", product.getName(),
                    "price", product.getPrice(),
                    "categories", product.getCategories().stream()
                            .map(cat -> cat.getName())
                            .collect(Collectors.toList())
            );
            return ResponseEntity.ok(body);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        }
    }
}
