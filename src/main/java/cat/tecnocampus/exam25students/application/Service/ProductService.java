package cat.tecnocampus.exam25students.application.Service;

import cat.tecnocampus.exam25students.domain.shop.Category;
import cat.tecnocampus.exam25students.domain.shop.Product;
import cat.tecnocampus.exam25students.persistence.CategoryRepository;
import cat.tecnocampus.exam25students.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Product findProduct(Long productId) {
        return productRepository.findWithCategoriesById(productId).orElse(null);
    }

    @Transactional
    public Product addCategory(Long productId, String categoryName) {
        Product product = productRepository.findWithCategoriesById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryName).orElse(null);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        product.addCategory(category);
        return productRepository.save(product);
    }
}
