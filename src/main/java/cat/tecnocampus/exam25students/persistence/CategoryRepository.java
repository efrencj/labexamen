package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.shop.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @EntityGraph(attributePaths = {"subcategories", "products"})
    Optional<Category> findWithRelationsByName(String name);
}
