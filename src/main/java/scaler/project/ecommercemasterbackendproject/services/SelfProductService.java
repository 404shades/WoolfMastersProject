package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Category;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;
import scaler.project.ecommercemasterbackendproject.repositories.CategoryRepository;
import scaler.project.ecommercemasterbackendproject.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service("SelfProductService")
public class SelfProductService implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        if (categoryRepository.findById(product.getCategory().getId()).isPresent()) {
            return productRepository.save(product);
        }
        Category category = categoryRepository.save(product.getCategory());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public ProductTitleAndDescriptionView getProductTitleAndDescription(Long productId) {
        return productRepository.findTitleAndDescriptionByProductId(productId);
    }
}
