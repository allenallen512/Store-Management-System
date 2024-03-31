import java.sql.Timestamp;
import java.util.List;

public interface DataAccess {
    public int connect();

    public int disconnect();


//    CRUD for job class

    public boolean  createProduct(Products products);
    public int getNextPaymentID();
    public Products readProduct(int productID);

    public boolean  updateProduct(Products product);

    public Products deleteProduct(int productID);

//    CRUD for employee models

    public boolean createCustomer (Customer customer);
    public int getNewProductID();
    public Customer readCustomer(int customerID);
    public int getNextCustomerID();

    public boolean updateCustomer(Customer customer);

    public Customer deleteCustomer(int customerID);

    public List<Orders> getOrdersByOrderID(int orderID);

    public boolean insertOrder(Orders order);

    public boolean updateOrder(Orders order);
    public int getNextID();
    public int getNextOrderID();
    public Orders deleteOrder(int orderID, int productID);
    public boolean updatePayment(Payments payment);
    public int getProductCount(int orderID);

    public String getSupplierName(int supplierID);
    public boolean createPayment(int orderID);
    public int getNextSupplierID();

    public Payments readPayment(int orderID);

    public double updatePaymentTotal(int orderID);
    public boolean createNewPayment(Payments Payment);
    public Payments deletePayment(int orderID);
    public boolean createSupplier(Supplier supplier);
    public Supplier readSupplier(int supplierID);
    public boolean updateSupplier(Supplier supplier);
    public Supplier deleteSupplier(int supplierID);


//    --------------------report section

    public boolean MonthlySales(Timestamp start, Timestamp end, boolean descending, int cutOff);
    public boolean SalePerProduct(Timestamp start, Timestamp end, boolean descending, int cutOff);
    public boolean SalePerCustomer(Timestamp start, Timestamp end, boolean descending, int cutOff);
}
