package scaler.project.ecommercemasterbackendproject.services;

import scaler.project.ecommercemasterbackendproject.exceptions.ProductAccessDeniedException;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;

import java.util.List;

public interface ProductService {

    public Product getProductById(Long productId) throws ProductNotFoundException;
    public List<Product> getAllProducts();
    public Product createProduct(Product product, String ownerEmail);
    public Product updateProduct(Long productId, Product product, String userEmail) throws ProductNotFoundException, ProductAccessDeniedException;
    public void deleteProduct(Long productId, String userEmail) throws ProductNotFoundException, ProductAccessDeniedException;
    public List<Product> getProductsByOwner(String ownerEmail);
    public ProductTitleAndDescriptionView getProductTitleAndDescription(Long projectId);

}
