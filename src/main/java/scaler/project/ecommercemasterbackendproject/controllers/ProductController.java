package scaler.project.ecommercemasterbackendproject.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductAccessDeniedException;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;
import scaler.project.ecommercemasterbackendproject.services.ProductService;

import java.util.List;

@RestController()
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    ProductController(@Qualifier("SelfProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable Long id) throws ProductNotFoundException {
        return this.productService.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return this.productService.getAllProducts();
    }

    // products added by the logged in vendor/admin
    @GetMapping("/my")
    public List<Product> getMyProducts(@AuthenticationPrincipal UserDetails userDetails) {
        return productService.getProductsByOwner(userDetails.getUsername());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product, @AuthenticationPrincipal UserDetails userDetails) {
        return productService.createProduct(product, userDetails.getUsername());

    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product,
                                 @AuthenticationPrincipal UserDetails userDetails)
            throws ProductNotFoundException, ProductAccessDeniedException {
        return productService.updateProduct(id, product, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails)
            throws ProductNotFoundException, ProductAccessDeniedException {
        productService.deleteProduct(id, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/title/{id}")
    public ProductTitleAndDescriptionView getProductTitleAndDescription(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getProductTitleAndDescription(id);
    }
}
