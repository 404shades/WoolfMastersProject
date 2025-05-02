package scaler.project.ecommercemasterbackendproject.models;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product extends BaseModel{
    private String description;
    private Double price;
    @ManyToOne
    @JoinColumn(columnDefinition = "category_cat_id")
    private Category category;
}
