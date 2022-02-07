package BusinessLayer;

import model.Product;

public class ProductPriceValidator implements Validator<Product> {

    private static final int MIN_PRICE = 1;
    @Override
    public void validate(Product product) {
        if(product.getPrice() < 1){
            throw new IllegalArgumentException("The product's price must be greater then 1");
        }
    }
}
