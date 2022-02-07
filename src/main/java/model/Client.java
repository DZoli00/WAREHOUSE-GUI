package model;

public class Client {
    private int idClient;
    private String name;
    private String address;
    private String email;
    private int age;

    public Client(int id, String name, String address, String email, int age){
        this.idClient = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    public int isIdClient() {
        return idClient;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setIdClient(int id) {
        this.idClient = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "" + idClient +
                " " + name +
                " " + address +
                " " + email +
                " " + age ;
    }
}
