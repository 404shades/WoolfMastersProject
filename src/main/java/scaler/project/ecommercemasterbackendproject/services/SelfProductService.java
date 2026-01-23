package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductAccessDeniedException;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Category;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.models.User;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;
import scaler.project.ecommercemasterbackendproject.repositories.CategoryRepository;
import scaler.project.ecommercemasterbackendproject.repositories.ProductRepository;
import scaler.project.ecommercemasterbackendproject.repositories.UserRepository;

import java.util.List;

@Service("SelfProductService")
public class SelfProductService implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(404L, "Product not found", productId));
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product createProduct(Product product, String ownerEmail) {
        product.setCategory(resolveCategory(product.getCategory()));
        // owner always comes from the token, never from the request body
        product.setVendor(findUser(ownerEmail));
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, Product product, String userEmail) throws ProductNotFoundException, ProductAccessDeniedException {
        Product existing = getProductById(productId);
        checkCanModify(existing, findUser(userEmail));

        existing.setTitle(product.getTitle());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setCategory(resolveCategory(product.getCategory()));
        return productRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId, String userEmail) throws ProductNotFoundException, ProductAccessDeniedException {
        Product existing = getProductById(productId);
        checkCanModify(existing, findUser(userEmail));
        productRepository.delete(existing);
    }

    @Override
    public List<Product> getProductsByOwner(String ownerEmail) {
        return productRepository.findAllByVendor_Email(ownerEmail);
    }

    @Override
    public ProductTitleAndDescriptionView getProductTitleAndDescription(Long productId) {
        return productRepository.findTitleAndDescriptionByProductId(productId);
    }

    // admin can change any product, a vendor only the ones they own
    private void checkCanModify(Product product, User user) throws ProductAccessDeniedException {
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleName.ADMIN);
        if (isAdmin) {
            return;
        }
        User owner = product.getVendor();
        if (owner == null || !owner.getId().equals(user.getId())) {
            throw new ProductAccessDeniedException(product.getId());
        }
    }

    // uses the existing category if an id is sent, otherwise creates a new one
    private Category resolveCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category is required");
        }
        if (category.getId() == null) {
            return categoryRepository.save(category);
        }
        Category existing = categoryRepository.findById(category.getId()).orElse(null);
        if (existing == null) {
            return categoryRepository.save(category);
        }
        return existing;
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
    }
}
