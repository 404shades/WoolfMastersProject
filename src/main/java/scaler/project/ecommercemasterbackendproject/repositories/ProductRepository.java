package scaler.project.ecommercemasterbackendproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scaler.project.ecommercemasterbackendproject.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
