//import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.io.FileWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.sql.Timestamp;


import java.util.Map;
public class SQLDataAdaptor implements DataAccess {

    private Connection connection = null;
    @Override
    public int connect() {
        String url = "jdbc:mysql://localhost:3306/project_1";
        String username = "root";
         String password = "lflrong1";
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);

        }
        catch (Exception e) {
            System.out.println("SQLite is not installed. System exits with error!");
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }

    @Override
    public int disconnect() {
        try {
        connection.close();
        } catch (SQLException ex) {
            System.out.println("SQLite database cannot be closed. System exits with error!" + ex.getMessage());
        }
        return 0;
    }

//    ----------------------PRODUCT SECTION----------------------------

    public int getNewProductID() {
        try {
            // Get the maximum payment ID from the products table
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(ProductID) AS MaxID FROM products");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt("MaxID");
                System.out.println("the max ID in the function is: " + maxPaymentID);
                return maxPaymentID + 1;
            }
            return 1;
        }
        catch (Exception e){
            System.out.println("error getting max int" + e.getMessage());
            return 0;
        }
    }
    @Override
    public boolean createProduct(Products product) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO products (ProductID, ProductName, ProductPrice, SupplierID) VALUES (?, ?, ?, ?)");
            statement.setInt(1, product.productID);
            statement.setString(2, product.productName);
            statement.setDouble(3, product.productPrice);
            statement.setInt(4, product.supplierID);

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            System.out.println("error in creating product" + ex.getMessage());
            return false;
        }
    }

    public String getSupplierName(int supplierID) {
        String supplierName;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM supplier WHERE SupplierID = ?");
            statement.setInt(1, supplierID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                supplierName= resultSet.getString("SupplierName");
                return supplierName;

            } else {
                System.out.println("supplier not found");
                return null;
            }

        } catch (Exception e) {
            System.out.println(" error reading product: " + e.getMessage());
            return null;
        }
    }
    @Override
    public Products readProduct(int productID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM products WHERE ProductID = ?");
            statement.setInt(1, productID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int productId = resultSet.getInt("ProductID");
                String productName = resultSet.getString("ProductName");
                double productPrice = resultSet.getDouble("ProductPrice");
                int supplierID = resultSet.getInt("SupplierID");
                String supplierName = getSupplierName(supplierID);

                return new Products(productId, productName, productPrice, supplierID, supplierName);
            } else {
                System.out.println("Product ID: " + productID + " not found.");
                return null;
            }

        } catch (Exception e) {
            System.out.println(" error reading product: " + e.getMessage());
            return null;
        }
    }


    @Override
    public boolean updateProduct(Products product) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE products SET productName = ?, productPrice = ?, supplierID = ? WHERE productID = ?");
            statement.setString(1, product.productName);
            statement.setDouble(2, product.productPrice);
            statement.setInt(3, product.supplierID);
            statement.setInt(4, product.productID);

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            System.out.println("update product does not work: " + ex.getMessage());
            return false;
        }
    }


    @Override
    public Products deleteProduct(int productID) {
        try {
            Products deleted = readProduct(productID);
            if (deleted == null) {
                System.out.println("The product with ID: " + productID + " was not found.");
                return null;
            } else {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE productID = ?");
                statement.setInt(1, productID);
                statement.executeUpdate();

                return deleted;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong in the delete product section: " + e.getMessage());
            return null;
        }
    }


    //-----------------------Customer----------------------------//

    @Override
    public boolean createCustomer(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO customers (CustomerID   , CustomerName, CustomerEmail) VALUES (?,?,?)");
            statement.setInt(1, customer.customerID);
            statement.setString(2, customer.customerName);
            statement.setString(3, customer.customerEmail);

            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("error making customer" + e.getMessage());
            return false;
        }
    }


   @Override
   public Customer readCustomer(int customerID) {
       try {
           PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE CustomerID = ?");
           statement.setInt(1, customerID);

           ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()) {
               int id = resultSet.getInt("CustomerID");
               String name = resultSet.getString("CustomerName");
               String email = resultSet.getString("CustomerEmail");
               return new Customer(id, name, email);
           } else {
               System.out.println("customer with ID: " + customerID + " not found");
               return null;
           }

       } catch (Exception e) {
           System.out.println(" error in reading customers: " + e.getMessage());
           return null;
       }
   }

    public int getNextCustomerID() {
        try {
            // Get the maximum customer ID from the orders table
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(CustomerID) AS MaxID FROM customers");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt("MaxID");
                return maxPaymentID + 1;
            }
            return 1;
        }
        catch (Exception e){
            System.out.println("error getting max int" + e.getMessage());
            return 0;
        }
    }


@Override
public boolean updateCustomer(Customer customer) {
    try {
        PreparedStatement statement = connection.prepareStatement("UPDATE customers SET CustomerName = ?, CustomerEmail = ? WHERE CustomerID = ?");
        statement.setString(1, customer.customerName);
        statement.setString(2, customer.customerEmail);
        statement.setInt(3, customer.customerID);

        int rows = statement.executeUpdate();
        return rows > 0;
    } catch (Exception e) {
        System.out.println("There was an error in updating customers: " + e.getMessage());
        return false;
    }
}



@Override
public Customer deleteCustomer(int id) {
    try {
        Customer deleted = readCustomer(id);
        if (deleted == null) {
            System.out.println("The customer with ID: " + id + " cannot be found");
            return null;
        } else {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE CustomerID = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            return deleted;
        }
    } catch (Exception e) {
        System.out.println("There was an error in deleting a customer: " + e.getMessage());
        return null;
    }
}


    //----------------------------ORDER SECTION-----------------------------


    public int getProductCount(int orderID) {
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM orders WHERE OrderID = ?");
//            get the number of products per order so that
            statement.setInt(1, orderID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("Error getting order count: " + e.getMessage());
        }
        return count;
    }

    public int getNextID() {
        try {
            // Get the maximum payment ID from the orders table
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(ID) AS MaxID FROM orders");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt("MaxID");
                return maxPaymentID + 1;
            }
            return 1;
        }
        catch (Exception e){
            System.out.println("error getting max int" + e.getMessage());
            return 0;
        }
    }

    public int getNextOrderID() {
        try {
            // Get the maximum payment ID from the orders table
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(OrderID) AS MaxID FROM orders");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt("MaxID");
                return maxPaymentID + 1;
            }
            return 1;
        }
        catch (Exception e){
            System.out.println("error getting max int" + e.getMessage());
            return 0;
        }
    }

    public List<Orders> getOrdersByOrderID(int orderID) {
        List<Orders> orders = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE OrderID = ?");
            statement.setInt(1, orderID);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                int ID = results.getInt("ID");
                Timestamp OrderDate = results.getTimestamp("OrderDate");
                double OrderPrice = results.getDouble("OrderPrice");
                int CustomerID = results.getInt("customerID");
                int productID = results.getInt("productID");
                Orders order = new Orders(ID, orderID, OrderDate, OrderPrice, CustomerID, productID);
                orders.add(order);
            }
        } catch (Exception e) {
            System.out.println("Error gettings orders: " + e.getMessage());
        }

        return orders;
    }


    public boolean insertOrder(Orders order) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders (ID, OrderID, OrderDate, OrderPrice, CustomerID, productID) VALUES (?, ?, ?, ?, ?, ?)");

            statement.setInt(1, order.ID);
            statement.setInt(2, order.OrderID);
            statement.setTimestamp(3, order.OrderDate);
            statement.setDouble(4, order.OrderPrice);
            statement.setInt(5, order.customerID);
            statement.setInt(6, order.productID);

            int rowsInserted = statement.executeUpdate();

            System.out.println("Order inserted");
            updatePaymentTotal(order.OrderID);
            createPayment(order.OrderID);
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Error inserting order: " + e.getMessage());
            return false;         }
    }

    public boolean updateOrder(Orders order) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE orders SET ID = ?, OrderDate = ?, OrderPrice = ?, CustomerID = ? WHERE productID = ? AND OrderID = ?");
            statement.setInt(1, order.ID);
            statement.setTimestamp(2, order.OrderDate);
            statement.setDouble(3, order.OrderPrice);
            statement.setInt(4, order.customerID);
            statement.setInt(5, order.productID);
            statement.setInt(6, order.OrderID);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println(" updated");
                updatePaymentTotal(order.OrderID);

                return true;
            } else {
                System.out.println("No order found" + order.ID + " and OrderID " + order.OrderID);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
            return false;
        }
    }

    public Orders readOrder(int orderID, int productID) {

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE OrderID = ? AND productID = ?");
            statement.setInt(1, orderID);
            statement.setInt(2, productID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                Timestamp orderDate = resultSet.getTimestamp("Order Date");
                double orderPrice = resultSet.getDouble("Order Price");
                int customerID = resultSet.getInt("CustomerID");
                return new Orders(ID, orderID, orderDate, orderPrice, customerID, productID);
            }
            else {
                System.out.println("cannot find order");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error reading order: " + e.getMessage());
            return null;
        }

    }

    public Orders deleteOrder(int orderID, int productID) {
        Orders deleted = readOrder(orderID, productID);
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE OrderID = ? AND productID = ?");

            statement.setInt(1, orderID);
            statement.setInt(2, productID);

            statement.executeUpdate();

            System.out.println("Order deleted successfully.");
            updatePaymentTotal(orderID);
            return deleted;
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
            return null;
        }
    }

//---------------------PAYMENT STUFF
    public double updatePaymentTotal(int orderID) {
        double totalCharge = 0.0;
        try {
            // sum the orderPrice for all orders with the same ID
            PreparedStatement total = connection.prepareStatement("SELECT SUM(OrderPrice) AS total FROM orders WHERE OrderID = ?");
            total.setInt(1, orderID);
            ResultSet result = total.executeQuery();

            if (result.next()) {
                totalCharge = result.getDouble("total");
            }


//            now update the payment table
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE payments SET PaymentTotal = ? WHERE OrderID = ?");
            updateStatement.setDouble(1, totalCharge);
            updateStatement.setInt(2, orderID);
            updateStatement.executeUpdate();

            return totalCharge;
        } catch (Exception e) {
            System.out.println(" error updating the project total: " + e.getMessage());
            return 0;
        }
    }

    public boolean updatePayment(Payments payment) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE payments SET CustomerID = ?, PaymentTotal = ?, PaymentType = ?, OrderID = ? WHERE PaymentID = ?");
            statement.setInt(1, payment.customerID);
            statement.setDouble(2, payment.paymentTotal);
            statement.setString(3, payment.paymentType);
            statement.setInt(4, payment.orderID);
            statement.setInt(5, payment.paymentID);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Payment updated");
                return true;
            } else {
                System.out.println("No payment found with orderID: " + payment.orderID);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error updating payment: " + e.getMessage());
            return false;
        }
    }

    public int getNextPaymentID() {

        try {
            // Get the maximum payment ID from the payments table
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(PaymentID) AS MaxID FROM payments");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt("MaxID");
                return maxPaymentID + 1;
            }
            return 1;
        }
        catch (Exception e){
            System.out.println("error getting max int" + e.getMessage());
            return 0;
        }
    }
    public boolean createPayment(int orderID) {
        try {
            List<Orders> orders = getOrdersByOrderID(orderID);
            //get list of orders

            int custID = 0;

            int PaymentID = getNextPaymentID();
            // get the total price
            double totalOrderPrice = 0.0;
            for (Orders order : orders) {
                custID = order.customerID;
                totalOrderPrice += order.OrderPrice;
            }

            // insert a payment
            PreparedStatement statement = connection.prepareStatement("INSERT INTO payments (PaymentID, CustomerID, paymentTotal, paymentType, OrderID) VALUES (?, ?, ?,?,?)");
            statement.setInt(1, PaymentID);
            statement.setInt(2, custID);
            statement.setInt(5, orderID);
            statement.setDouble(3, totalOrderPrice);
            statement.setString(4, "debit");

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error creating payment: " + e.getMessage());
            return false;
        }
    }

    public boolean createNewPayment(Payments payment) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO payments (PaymentID, CustomerID, PaymentTotal, PaymentType, OrderID) VALUES (?, ?, ?, ?, ?)");

            statement.setInt(1, payment.paymentID);
            statement.setInt(2, payment.customerID);
            statement.setDouble(3, payment.paymentTotal);
            statement.setString(4, payment.paymentType);
            statement.setInt(5, payment.orderID);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Error inserting payment: " + e.getMessage());
            return false;
        }
    }


    public Payments readPayment(int paymentID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM payments WHERE PaymentID = ?");
            statement.setInt(1, paymentID);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                double paymentTotal = result.getDouble("PaymentTotal");
                String paymentType = result.getString("PaymentType");
                int customerID = result.getInt("CustomerID");
                int orderID = result.getInt("OrderID");
                return new Payments(paymentID, customerID, paymentTotal, paymentType, orderID);
            } else {
                System.out.println("No payment: " + paymentID);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error reading in read payment " + e.getMessage());
            return null;
        }
    }



    public Payments deletePayment(int paymentID) {
        try {
            Payments deleted = readPayment(paymentID);
            if (deleted == null) {
                System.out.println("cannot find the payment to delete");
                return null;
            }
            else {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM payments WHERE paymentID = ?");
                statement.setInt(1, paymentID);
                int rowsAffected = statement.executeUpdate();

                return deleted;
            }

        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
            return null;
        }
    }
//-------------------SUPPLIER SECTION---------------------

    public int getNextSupplierID() {
        try {
            // Get the maximum customer ID from the orders table
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(SupplierID) AS MaxID FROM supplier");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxPaymentID = resultSet.getInt("MaxID");
                return maxPaymentID + 1;
            }
            return 1;
        }
        catch (Exception e){
            System.out.println("error getting max int" + e.getMessage());
            return 0;
        }
    }
public boolean createSupplier(Supplier supplier) {
    try {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO supplier (SupplierID, SupplierName, Email) VALUES (?,?,?)");
        statement.setInt(1, supplier.SupplierID);
        statement.setString(2, supplier.SupplierName);
        statement.setString(3, supplier.SupplierEmail);

        int rows = statement.executeUpdate();
        return rows > 0;
    } catch (Exception e) {
        System.out.println("Error creating supplier: " + e.getMessage());
        return false;
    }
}

    public Supplier readSupplier(int supplierID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM supplier WHERE SupplierID = ?");
            statement.setInt(1, supplierID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int suppID = resultSet.getInt("SupplierID");
                String supplierName = resultSet.getString("SupplierName");
                String supplierEmail = resultSet.getString("Email");

                return new Supplier(suppID, supplierName, supplierEmail);
            } else {
                System.out.println("Supplier not found with ID:  " + supplierID);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error reading supplier: " + e.getMessage());
            return null;
        }
    }

    public boolean updateSupplier(Supplier supplier) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE supplier SET SupplierName = ?, Email = ? WHERE SupplierID = ?");
            statement.setString(1, supplier.SupplierName);
            statement.setString(2, supplier.SupplierEmail);
            statement.setInt(3, supplier.SupplierID);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("could not update supplier: " + e.getMessage());
            return false;
        }
    }

    public Supplier deleteSupplier(int supplierID) {
        try {

            Supplier deleted = readSupplier(supplierID);
            if (deleted == null) {
                System.out.println("cannot find the supplier to delete");
                return null;
            }
            else {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM supplier WHERE SupplierID = ?");
                statement.setInt(1, supplierID);

                int rowsAffected = statement.executeUpdate();
                return deleted;
            }

        } catch (Exception e) {
            System.out.println("error in delete supplier: " + e.getMessage());
            return null;
        }
    }

//    -----------------------------reports portion--------------------------

public boolean MonthlySales(Timestamp start, Timestamp end, boolean descending, int cutOff) {
        try {
//            used online and AI to help write the folllwing query
            PreparedStatement statement = connection.prepareStatement("SELECT MONTH(OrderDate) AS Month, YEAR(OrderDate) AS Year, SUM(OrderPrice) AS TotalSale " +
                    "FROM orders " +  "WHERE OrderDate BETWEEN ? AND ? " +  "GROUP BY MONTH(OrderDate), YEAR(OrderDate) " +
                    "ORDER BY TotalSale " + (descending ? "DESC" : "ASC") + " " +
                    "LIMIT ?");

            statement.setTimestamp(1, start);
            statement.setTimestamp(2, end);
            statement.setInt(3, cutOff);
            ResultSet result = statement.executeQuery();

            FileWriter writer = new FileWriter("Monthly_Sales_Report.txt");
            while (result.next()) {
                int month = result.getInt("Month");
                int year = result.getInt("Year");
                double saleTotal = result.getDouble("TotalSale");
                writer.write("Month: " + month + " Year: " + year + " - Total Sale: " + saleTotal + "\n");
            }
            writer.close();
            System.out.println("Montly sales report was generated");
            return true;
        }
        catch (Exception e) {
            System.out.println("error in monthly sales part: " + e.getMessage());
            return false;
        }
}

    public boolean SalePerProduct(Timestamp start, Timestamp end, boolean descending, int cutOff) {
        try {
//added coalesce to have products with 0 sales also included
            PreparedStatement statement = connection.prepareStatement("SELECT productID, COALESCE(SUM(OrderPrice), 0) AS TotalSale " +
                    "FROM orders " +  "WHERE OrderDate BETWEEN ? AND ? " +  "GROUP BY productID " + "ORDER BY TotalSale " + (descending ? "DESC" : "ASC") +
                    " LIMIT ?");
            statement.setTimestamp(1, start);
            statement.setTimestamp(2, end);
            statement.setInt(3, cutOff);

            ResultSet resultSet = statement.executeQuery();

            FileWriter writer = new FileWriter("Product_Sales_Report.txt");
            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                double totalSale = resultSet.getDouble("TotalSale");
                writer.write("ProductID: " + productID + ", Total Sale: " + totalSale + "\n");
            }
            writer.close();

            System.out.println("product report has been generated.");
            return true;
        } catch (Exception e) {
            System.out.println("error in product report portion " + e.getMessage());
            return false;
        }
    }
public boolean SalePerCustomer(Timestamp start, Timestamp end, boolean descending, int cutOff) {
    try {

        PreparedStatement statement = connection.prepareStatement("SELECT customerID, SUM(OrderPrice) AS TotalSale " +
                "FROM orders " + "WHERE OrderDate BETWEEN ? AND ? " +  "GROUP BY customerID " +
                "ORDER BY TotalSale " + (descending ? "DESC" : "ASC") + // Make sure to add a space before LIMIT
                " LIMIT ?");
        statement.setTimestamp(1, start);
        statement.setTimestamp(2, end);
        statement.setInt(3, cutOff);

        ResultSet resultSet = statement.executeQuery();


        FileWriter writer = new FileWriter("Customer_Sales_Report.txt");
        while (resultSet.next()) {
            int customerID = resultSet.getInt("CustomerID");
            double totalSale = resultSet.getDouble("TotalSale");
            writer.write("CustomerID: " + customerID + ", Total Sale: " + totalSale + "\n");
        }
        writer.close();

        System.out.println("the customer report is all made");
        return true;
    } catch (Exception e) {
        System.out.println("error in customer report section " + e.getMessage());
        return false; // Operation failed
    }

}

}


