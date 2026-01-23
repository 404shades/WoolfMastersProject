package scaler.project.ecommercemasterbackendproject.exceptions;

public class ProductAccessDeniedException extends Exception {
    private final Long productId;

    public ProductAccessDeniedException(Long productId) {
        super("You can only change products that you have added");
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
