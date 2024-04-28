package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.stereotype.Service;
import scaler.project.ecommercemasterbackendproject.models.Category;
import scaler.project.ecommercemasterbackendproject.models.Product;

@Service
public class FakeStoreProductService implements ProductService {
    @Override
    public Product getProductById(Long productId) {
       return new Product();
    }
}
