package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.repositories.ProductRepository;

import java.util.List;

@Service
@Primary
public class SelfProductService implements ProductService {
    ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
