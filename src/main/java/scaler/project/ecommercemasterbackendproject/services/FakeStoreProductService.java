package scaler.project.ecommercemasterbackendproject.services;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import scaler.project.ecommercemasterbackendproject.dtos.FakeStoreProductDTO;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;
import scaler.project.ecommercemasterbackendproject.models.Category;
import scaler.project.ecommercemasterbackendproject.models.Product;
import scaler.project.ecommercemasterbackendproject.projections.ProductTitleAndDescriptionView;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.stream.Stream;

@Data
@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Product convertToProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        if (fakeStoreProductDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());
        Category category = new Category();
        category.setTitle(fakeStoreProductDTO.getCategory());
        product.setCategory(category);
        return product;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        FakeStoreProductDTO fakeStoreProductDTO =  restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, FakeStoreProductDTO.class);
        if (fakeStoreProductDTO == null){
            throw new ProductNotFoundException(
                    (long) (Math.random() * 1_000_000_000L), "Product with id " + productId + " not found in the fake store API.",
                    productId
            );
        }
        return convertToProduct(fakeStoreProductDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        // returning an empty list for now
        FakeStoreProductDTO[] fakeStoreProductDTOs = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);
        if (fakeStoreProductDTOs == null) {
            return List.of();
        }
        return Stream.of(fakeStoreProductDTOs)
                .map(this::convertToProduct)
                .toList();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public ProductTitleAndDescriptionView getProductTitleAndDescription(Long projectId) {
        return null;
    }
}
