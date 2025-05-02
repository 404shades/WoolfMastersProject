package scaler.project.ecommercemasterbackendproject.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scaler.project.ecommercemasterbackendproject.dtos.ProductNotFoundExceptionDTO;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;

@ControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDTO> handleInstanceNotFoundException(ProductNotFoundException e) {
        ProductNotFoundExceptionDTO productNotFoundExceptionDTO = new ProductNotFoundExceptionDTO();
        productNotFoundExceptionDTO.setErrorCode(e.getErrorCode());
        productNotFoundExceptionDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(productNotFoundExceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProductNotFoundExceptionDTO> handleGenericException(Exception e) {
        ProductNotFoundExceptionDTO productNotFoundExceptionDTO = new ProductNotFoundExceptionDTO();
        productNotFoundExceptionDTO.setErrorCode(500L);
        productNotFoundExceptionDTO.setMessage("An unexpected error occurred: " + e.getMessage());
        return new ResponseEntity<>(productNotFoundExceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
