public class Payments {

    public int paymentID;
    public int customerID;
    public Double paymentTotal;
    public String paymentType;
    public int orderID;

    Payments(int id, int customer_ID, Double total, String type, int Order) {
        this.paymentID = id;
        this.customerID = customer_ID;
        this.paymentTotal = total;
        this.paymentType = type;
        this.orderID = Order;
    }
}
