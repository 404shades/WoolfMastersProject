package scaler.project.ecommercemasterbackendproject.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity()
public class BaseModel {
    @Id
    private Long id;
    private String title;

}
