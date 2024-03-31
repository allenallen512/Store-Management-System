import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;


public class Orders {

    public int ID;
    public int OrderID;
    public Timestamp OrderDate;
    public Double OrderPrice;
    public int customerID;
    public int productID;



    public Orders() {
        this.ID = 999;
        this.OrderID = 999;
        this.OrderDate = null;
        this.OrderPrice = 99.99;
        this.customerID = 999;
        this.productID = 999;
    }

    public Orders(int ID, int order, Timestamp time, double price, int customer, int product){
        this.ID = ID;
        this.OrderID = order;
        this.OrderDate = time;
        this.OrderPrice = price;
        this.customerID = customer;
        this.productID = product;
    }

}
