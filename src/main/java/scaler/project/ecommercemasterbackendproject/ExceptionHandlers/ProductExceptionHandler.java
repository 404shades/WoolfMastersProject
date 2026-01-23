package scaler.project.ecommercemasterbackendproject.ExceptionHandlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductAccessDeniedException;
import scaler.project.ecommercemasterbackendproject.exceptions.ProductNotFoundException;

// only product related errors here, common ones are in GlobalExceptionHandler
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        pd.setTitle("Product Not Found");
        pd.setProperty("errorCode", e.getErrorCode());
        pd.setProperty("productId", e.getProductId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(ProductAccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleProductAccessDenied(ProductAccessDeniedException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        pd.setTitle("Forbidden");
        pd.setProperty("productId", e.getProductId());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(pd);
    }
}
