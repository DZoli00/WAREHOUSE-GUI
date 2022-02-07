package BusinessLayer;
import DataAccessLayer.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validatorList;
    private ClientDAO clientDAO;

    public ClientBLL(){
        validatorList = new ArrayList<Validator<Client>>();
        validatorList.add(new EmailValidator());
        validatorList.add(new ClientAgeValidator());

        clientDAO = new ClientDAO();
    }

    public Client findById(int id){
        Client cl = clientDAO.findById(id);
        if(cl == null){
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return cl;
    }

    public Client checkClient(Client cl){
        for (Validator<Client> validator : validatorList)
            validator.validate(cl);
        return cl;
    }

    public void insert(Client cl) throws IllegalAccessException {
        checkClient(cl);
        clientDAO.insert(cl);
    }

    public void delete(Client cl) {
        //checkClient(cl);
        clientDAO.delete(cl);
    }

    public void update(Client cl) throws IllegalAccessException {
        checkClient(cl);
        clientDAO.update(cl);
    }

    public List<Client> findAll() {
        //checkClient(cl);
        return clientDAO.findAll();
    }
}
