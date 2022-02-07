package BusinessLayer;

import DataAccessLayer.ClientDAO;
import DataAccessLayer.OrdersDAO;
import model.Client;
import model.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private List<Validator<Orders>> validatorList;
    private OrdersDAO ordersDAO;

    public OrderBLL(){
        validatorList = new ArrayList<Validator<Orders>>();
        validatorList.add(new OrdersQuantityValidator());

        ordersDAO = new OrdersDAO();
    }

    public Orders checkOrder(Orders order){
        for(Validator<Orders> validator: validatorList){
            validator.validate(order);
        }
        return order;
    }

    public Orders findById(int id){
        Orders cl = ordersDAO.findById(id);
        if(cl == null){
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return cl;
    }

    public Orders checkClient(Orders cl){
        for (Validator<Orders> validator : validatorList)
            validator.validate(cl);
        return cl;
    }

    public void insert(Orders cl) throws IllegalAccessException {
        checkClient(cl);
        ordersDAO.insert(cl);
    }

    public void delete(Orders cl) {
       // checkClient(cl);
        ordersDAO.delete(cl);
    }

    public void update(Orders cl) throws IllegalAccessException {
        checkClient(cl);
        ordersDAO.update(cl);
    }

    public List<Orders> findAll() {
        //checkClient(cl);
        return ordersDAO.findAll();
    }


}
