public class Customer{

    public int customerID;

    public String customerName;

    public String customerEmail;

    public Customer() {
        this.customerID = 999;
        this.customerName = "No Name";
        this.customerEmail = "no@email.com";
    }

    public  Customer(int id, String name, String email) {
        this.customerID = id;
        this.customerName = name;
        this.customerEmail = email;
    }
}
