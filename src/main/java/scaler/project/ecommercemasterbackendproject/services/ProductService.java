package scaler.project.ecommercemasterbackendproject.services;

import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;

import java.util.List;

public interface ProductService {

    public Product getProductById(Long productId) throws ProductNotFoundException;
    public List<Product> getAllProducts();
    public Product createProduct(Product product);
    public ProductTitleAndDescriptionView getProductTitleAndDescription(Long projectId);

}
