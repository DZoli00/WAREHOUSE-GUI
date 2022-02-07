package model;

import javax.swing.*;

public class Orders {
    private int idOrder;
    private int idClient;
    private int idProduct;
    private String nameClient;
    private String nameProduct;
    private int quantity;
    private int priceAll;

    public Orders(int idOrder, int idClient, int idProduct, String nameClient, String nameProduct, int quantityOrdered, int priceAll){
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.nameClient = nameClient;
        this.nameProduct = nameProduct;
        this.quantity = quantityOrdered;
        this.priceAll = priceAll;
    }


    public int getIdOrder() {
        return idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getNameClient() {
        return nameClient;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public int isQuantity() {
        return quantity;
    }

    public int getPriceAll() {
        return priceAll;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setQuantity(int quantityOrdered) {
        this.quantity = quantityOrdered;
    }

    public void setPriceAll(int priceAll) {
        this.priceAll = priceAll;
    }

    @Override
    public String toString() {
        return + idOrder +
                " " + idClient +
                " " + idProduct +
                " " + nameClient +
                " " + nameProduct +
                " " + quantity +
                " " + priceAll ;
    }
}
