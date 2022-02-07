package model;

public class Product {
    private int idProduct;
    private String name;
    private int price;
    private int quantity;

    public Product(int idProduct, String name, int price, int quantity){
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.quantity=quantity;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return idProduct +
                " " + name +
                " " + price +
                " " + quantity;
    }
}
