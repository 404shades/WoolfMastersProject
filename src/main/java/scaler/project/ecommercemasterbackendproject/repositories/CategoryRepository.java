package scaler.project.ecommercemasterbackendproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scaler.project.ecommercemasterbackendproject.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
