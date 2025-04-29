package scaler.project.ecommercemasterbackendproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.services.ProductService;

import java.util.List;

@RestController()
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable Long id) {
        return this.productService.getProductById(id);
    }

    @GetMapping("")
    public List<Product> getAllProducts() {
        return this.productService.getAllProducts();
    }
}
