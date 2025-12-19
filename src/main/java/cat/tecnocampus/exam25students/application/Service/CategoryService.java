package cat.tecnocampus.exam25students.application.Service;

import cat.tecnocampus.exam25students.domain.shop.Category;
import cat.tecnocampus.exam25students.domain.shop.Product;
import cat.tecnocampus.exam25students.persistence.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findProductsByCategoryHierarchy(String categoryName) {
        return categoryRepository.findWithRelationsByName(categoryName)
                .map(this::collectProducts)
                .orElseGet(ArrayList::new);
    }

    private List<Product> collectProducts(Category root) {
        Set<Product> products = new HashSet<>(root.getProducts());
        for (Category sub : root.getSubcategories()) {
            Category loadedSub = categoryRepository.findWithRelationsByName(sub.getName()).orElse(sub);
            products.addAll(collectProducts(loadedSub));
        }
        return new ArrayList<>(products);
    }

    @Transactional(readOnly = true)
    public Category findCategory(String name) {
        return categoryRepository.findById(name).orElse(null);
    }
}
