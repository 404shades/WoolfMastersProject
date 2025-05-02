package scaler.project.ecommercemasterbackendproject.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scaler.project.ecommercemasterbackendproject.dtos.ProductNotFoundExceptionDTO;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;
import scaler.project.ecommercemasterbackendproject.services.ProductService;

import javax.management.InstanceNotFoundException;
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

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);

    }

    @GetMapping("/title/{id}")
    public ProductTitleAndDescriptionView getProductTitleAndDescription(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getProductTitleAndDescription(id);
    }
}
