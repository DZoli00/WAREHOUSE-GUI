package BusinessLayer;
import model.Client;

public class ClientAgeValidator implements Validator<Client>{
    private static final int MIN_AGE = 16;
    @Override
    public void validate(Client client) {
        if(client.getAge() < MIN_AGE){
            throw new IllegalArgumentException("The Client age limit is not respected");
        }
    }
}
