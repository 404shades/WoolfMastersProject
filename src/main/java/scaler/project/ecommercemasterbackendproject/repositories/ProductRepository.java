package scaler.project.ecommercemasterbackendproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("Select p.title as title, p.description as description from Product p where p.id = :productId")
    ProductTitleAndDescriptionView findTitleAndDescriptionByProductId(Long productId);
}
