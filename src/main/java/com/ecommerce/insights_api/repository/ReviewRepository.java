package com.ecommerce.insights_api.repository;
import com.ecommerce.insights_api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Spring Data JPA crea automáticamente la consulta a partir del nombre del metodo
    List<Review> findByProductId(Long productId);
}