package scaler.project.ecommercemasterbackendproject.services;

import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Product;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface ProductService {

    public Product getProductById(Long productId) throws ProductNotFoundException;
    public List<Product> getAllProducts();

}
