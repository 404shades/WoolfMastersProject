package scaler.project.ecommercemasterbackendproject.exceptions;

public class ProductNotFoundException extends Exception{
private final Long errorCode;
private final String errorMessage;
private final Long productId;

    public ProductNotFoundException(Long errorCode, String errorMessage, Long productId) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.productId = productId;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Long getProductId() {
        return productId;
    }
}
