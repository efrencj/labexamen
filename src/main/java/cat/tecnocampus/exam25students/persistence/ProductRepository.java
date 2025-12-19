package cat.tecnocampus.exam25students.persistence;

import cat.tecnocampus.exam25students.domain.shop.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "categories")
    Optional<Product> findWithCategoriesById(Long id);
}
