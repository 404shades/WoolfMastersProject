package scaler.project.ecommercemasterbackendproject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import scaler.project.ecommercemasterbackendproject.models.Product;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAndFetchProduct() {
        Map<String, Object> request = new HashMap<>();
        request.put("title", "Test Product");
        request.put("description", "A product for integration test");
        request.put("price", 123.45);
        Map<String, Object> category = new HashMap<>();
        category.put("title", "Electronics");
        request.put("category", category);

        ResponseEntity<Product> createRes = restTemplate.postForEntity("/products", request, Product.class);
        assertEquals(HttpStatus.OK, createRes.getStatusCode());
        Product created = createRes.getBody();
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Product", created.getTitle());
        assertNotNull(created.getCategory());
        assertNotNull(created.getCategory().getId());

        ResponseEntity<Product> getRes = restTemplate.getForEntity("/products/" + created.getId(), Product.class);
        assertEquals(HttpStatus.OK, getRes.getStatusCode());
        assertNotNull(getRes.getBody());
        assertEquals(created.getId(), getRes.getBody().getId());
    }
}
