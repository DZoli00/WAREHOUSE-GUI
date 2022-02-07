package BusinessLayer;

import model.Orders;
import model.Product;

public class OrdersQuantityValidator implements Validator<Orders>{
    private static final int MIN_QUANTITY = 1;
    @Override
    public void validate(Orders order) {
        if(order.isQuantity() < MIN_QUANTITY){
            throw new IllegalArgumentException("The product quantiy must be 1 or more");
        }
    }
}
