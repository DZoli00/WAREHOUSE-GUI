package BusinessLayer;

import DataAccessLayer.ClientDAO;
import DataAccessLayer.ProductDAO;
import model.Client;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private List<Validator<Product>> validatorList;
    private ProductDAO productDAO;

    public ProductBLL(){
        validatorList = new ArrayList<Validator<Product>>();
        validatorList.add(new ProductPriceValidator());
        validatorList.add(new ProductQuantityValidator());

        productDAO = new ProductDAO();
    }

    public Product findById(int id){
        Product pr = productDAO.findById(id);
        if(pr == null){
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return pr;
    }

    public Product checkProduct(Product pr){
        for(Validator<Product> validator :validatorList){
            validator.validate(pr);
        }
        return pr;
    }

    public void insert(Product pr) throws IllegalAccessException {
        checkProduct(pr);
        productDAO.insert(pr);
    }

    public void delete(Product pr) {
       // checkProduct(pr);
        productDAO.delete(pr);
    }

    public void update(Product pr) throws IllegalAccessException {
        checkProduct(pr);
        productDAO.update(pr);
    }

    public List<Product> findAll() {
        //checkClient(cl);
        return productDAO.findAll();
    }
}
