package scaler.project.ecommercemasterbackendproject.models;


import lombok.Data;

@Data
public class Product {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Category category;
}
