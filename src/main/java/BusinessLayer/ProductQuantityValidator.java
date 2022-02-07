package BusinessLayer;

import model.Product;

public class ProductQuantityValidator implements Validator<Product> {
    private static final int MIN_QUANTITY = 1;

    @Override
    public void validate(Product product) {
        if(product.getQuantity() < MIN_QUANTITY){
            throw new IllegalArgumentException("The product quantiy must be 1 or more");
        }
    }
}
