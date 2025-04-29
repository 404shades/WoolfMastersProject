package scaler.project.ecommercemasterbackendproject.dtos;

import lombok.Data;

@Data
public class FakeStoreProductDTO {
    Long id;
    String title;
    Double price;
    String category;
    String description;
    String image;
}
