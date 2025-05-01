package scaler.project.ecommercemasterbackendproject.dtos;

import lombok.Data;

@Data
public class ProductNotFoundExceptionDTO {
     private Long errorCode;
     private String message;
}
