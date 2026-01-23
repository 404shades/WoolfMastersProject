package scaler.project.ecommercemasterbackendproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByVendor_Email(String email);

    @Query("Select p.title as title, p.description as description from Product p where p.id = :productId")
    ProductTitleAndDescriptionView findTitleAndDescriptionByProductId(@Param("productId") Long productId);
}
