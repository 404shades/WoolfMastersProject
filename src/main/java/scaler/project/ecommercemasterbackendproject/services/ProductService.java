package scaler.project.ecommercemasterbackendproject.services;

import scaler.project.ecommercemasterbackendproject.models.Product;

import java.util.List;

public interface ProductService {

    public Product getProductById(Long productId);
    public List<Product> getAllProducts();

}
