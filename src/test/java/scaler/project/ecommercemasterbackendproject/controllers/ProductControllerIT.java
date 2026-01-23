package scaler.project.ecommercemasterbackendproject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import scaler.project.ecommercemasterbackendproject.dtos.LoginResponseDTO;
import scaler.project.ecommercemasterbackendproject.models.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Test
    void createAndFetchProduct() {
        Map<String, Object> request = new HashMap<>();
        request.put("title", "Test Product");
        request.put("description", "A product for integration test");
        request.put("price", 123.45);
        Map<String, Object> category = new HashMap<>();
        category.put("title", "Electronics");
        category.put("description", "Category for integration test");
        request.put("category", category);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginAsAdmin());

        ResponseEntity<Product> createRes = restTemplate.postForEntity("/products", new HttpEntity<>(request, headers), Product.class);
        assertEquals(HttpStatus.OK, createRes.getStatusCode());
        Product created = createRes.getBody();
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Product", created.getTitle());
        assertNotNull(created.getCategory());
        assertNotNull(created.getCategory().getId());

        // GET is public, so no token here
        ResponseEntity<Product> getRes = restTemplate.getForEntity("/products/" + created.getId(), Product.class);
        assertEquals(HttpStatus.OK, getRes.getStatusCode());
        assertNotNull(getRes.getBody());
        assertEquals(created.getId(), getRes.getBody().getId());
    }

    @Test
    void createProductWithoutTokenIsUnauthorized() {
        Map<String, Object> request = new HashMap<>();
        request.put("title", "No Token Product");
        request.put("description", "should not be saved");

        ResponseEntity<String> res = restTemplate.postForEntity("/products", request, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
    }

    @Test
    void vendorCanOnlyChangeOwnProducts() {
        // random emails so the test can run again on the same db
        String ownerToken = signUpVendorAndLogin("owner-" + UUID.randomUUID() + "@test.com");
        String otherToken = signUpVendorAndLogin("other-" + UUID.randomUUID() + "@test.com");

        Map<String, Object> request = new HashMap<>();
        request.put("title", "Vendor Product");
        request.put("description", "Owned by first vendor");
        request.put("price", 10.0);
        request.put("category", Map.of("title", "Test Category", "description", "for ownership test"));

        ResponseEntity<Product> createRes = restTemplate.postForEntity("/products", new HttpEntity<>(request, bearer(ownerToken)), Product.class);
        assertEquals(HttpStatus.OK, createRes.getStatusCode());
        Long productId = createRes.getBody().getId();

        ResponseEntity<String> otherDelete = restTemplate.exchange("/products/" + productId, HttpMethod.DELETE,
                new HttpEntity<>(bearer(otherToken)), String.class);
        assertEquals(HttpStatus.FORBIDDEN, otherDelete.getStatusCode());

        ResponseEntity<String> ownerDelete = restTemplate.exchange("/products/" + productId, HttpMethod.DELETE,
                new HttpEntity<>(bearer(ownerToken)), String.class);
        assertEquals(HttpStatus.NO_CONTENT, ownerDelete.getStatusCode());

        ResponseEntity<String> getRes = restTemplate.getForEntity("/products/" + productId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getRes.getStatusCode());
    }

    private String loginAsAdmin() {
        return login(adminEmail, adminPassword);
    }

    private String signUpVendorAndLogin(String email) {
        Map<String, String> signUp = Map.of("name", "Test Vendor", "email", email,
                "password", "Vendor@123", "role", "VENDOR");
        ResponseEntity<String> res = restTemplate.postForEntity("/auth/signup", signUp, String.class);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        return login(email, "Vendor@123");
    }

    private String login(String email, String password) {
        Map<String, String> login = Map.of("email", email, "password", password);
        ResponseEntity<LoginResponseDTO> res = restTemplate.postForEntity("/auth/login", login, LoginResponseDTO.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(res.getBody());
        return res.getBody().getAccessToken();
    }

    private HttpHeaders bearer(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}
