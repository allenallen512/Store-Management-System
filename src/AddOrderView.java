import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
public class AddOrderView extends JFrame{
    private JTextField EntryID;

    private JTextField OrderIDField;
    private JTextField OrderDateField;
    private JTextField OrderPriceField;
    private JTextField orderCustomerIDField;
    private JTextField orderProductField;
    private JButton addOrder;
    private JButton updateOrder;
    private JButton readButton;
    private JButton deleteButton;

    private void displayOrderDetails(List<Orders> ordersList) {
        JFrame details = new JFrame("Order Details");

        details.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        details.setLayout(new GridLayout(ordersList.size() * 2 + 3, 1)); // Increase the grid size

        Orders firstOrder = ordersList.get(0);
        JLabel idLabel = new JLabel("Order ID: " + firstOrder.OrderID);
        JLabel dateLabel = new JLabel("Order Date: " + firstOrder.OrderDate);
        JLabel customerLabel = new JLabel("Ordered by: " + firstOrder.customerID);

        details.add(idLabel);
        details.add(dateLabel);
        details.add(customerLabel);
        for (Orders order : ordersList) {
            // get product
            Products current_product = Application.getInstance().dataAdapter.readProduct(order.productID);
            JLabel prod_label = new JLabel("Product: " + current_product.productName + ", Price: " + current_product.productPrice);
            details.add(prod_label);

        }

        details.pack(); // Pack the frame to fit the content
        details.setVisible(true);
    }

    public AddOrderView() {

        setTitle("Add Order Class");
        this.setSize(500, 300);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel("Order View"));

        //        JPanel main = new JPanel(new SpringLayout());
        JPanel main = new JPanel(new GridLayout(6, 2, 5, 5));

        main.add(new JLabel("Order ID: "));
        OrderIDField = new JTextField();
        main.add(OrderIDField);

        main.add(new JLabel("Order Date: "));
        OrderDateField = new JTextField();
        main.add(OrderDateField);

        main.add(new JLabel("Order Price: "));
        OrderPriceField= new JTextField();
        main.add(OrderPriceField);

        main.add(new JLabel("Customer ID:  "));
        orderCustomerIDField = new JTextField();
        main.add(orderCustomerIDField);

        main.add(new JLabel("Product ID: "));
        orderProductField = new JTextField();
        main.add(orderProductField);

        addOrder = new JButton("Add Order");
        addOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int OrderID;
                String inputOrderID = OrderIDField.getText().trim();
                if (inputOrderID.isEmpty()) {
                    OrderID = Application.getInstance().dataAdapter.getNextOrderID();
                } else {
                    try {
                        OrderID = Integer.parseInt(inputOrderID);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Order ID! Please provide a valid ID!");
                        return;
                    }
                }

                String orderDateinput = OrderDateField.getText().trim();
                if (orderDateinput.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter a valid date");
                }
                Timestamp orderDate = Timestamp.valueOf(orderDateinput);

                Double orderPrice;
                try {
                    orderPrice =  Double.parseDouble(OrderPriceField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid order price");
                    return;
                }

                int orderCustomer;
                try {
                    orderCustomer = Integer.parseInt(orderCustomerIDField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid customer id");
                    return;
                }
                int orderProduct;
                try {
                    orderProduct = Integer.parseInt(orderProductField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid product id");
                    return;
                }

                int nextID = Application.getInstance().dataAdapter.getNextID();

                Orders newOrder = new Orders(nextID, OrderID, orderDate, orderPrice, orderCustomer, orderProduct);
                boolean res = Application.getInstance().dataAdapter.insertOrder(newOrder);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "Order is NOT saved!");
                } else {
                    JOptionPane.showMessageDialog(null, "Order created!");
                }

            }
        });

        updateOrder = new JButton("Update Order");
        updateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int OrderID;
                try {
                    OrderID = Integer.parseInt(OrderIDField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Order ID! Please provide a valid ID!");
                    return;
                }

                String orderDateinput = OrderDateField.getText().trim();
                if (orderDateinput.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter a valid date");
                }
                Timestamp orderDate = Timestamp.valueOf(orderDateinput);

                Double orderPrice;
                try {
                    orderPrice =  Double.parseDouble(OrderPriceField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid order price");
                    return;
                }

                int orderCustomer;
                try {
                    orderCustomer = Integer.parseInt(orderCustomerIDField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid customer id");
                    return;
                }
                int orderProduct;
                try {
                    orderProduct = Integer.parseInt(orderProductField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid product id");
                    return;
                }

                int nextID = Application.getInstance().dataAdapter.getNextID();

                Orders updatedOrder = new Orders(nextID, OrderID, orderDate, orderPrice, orderCustomer, orderProduct);

                boolean res = Application.getInstance().dataAdapter.updateOrder(updatedOrder);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "Order is NOT updated!");
                } else {
                    JOptionPane.showMessageDialog(null, "Order updated!");
                }

            }
        });

        readButton = new JButton("Read Order");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(OrderIDField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Order ID! Please provide a valid ID!");
                    return;
                }

                List<Orders> result = Application.getInstance().dataAdapter.getOrdersByOrderID(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Order with id: " + id + " was not found");
                } else {
                    displayOrderDetails(result);
                }

            }
        });

        deleteButton = new JButton("Delete Order");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(OrderIDField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Order ID! Please provide a valid ID!");
                    return;
                }

                int orderProduct;
                try {
                    orderProduct = Integer.parseInt(orderProductField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid product id");
                    return;
                }

                Orders result = Application.getInstance().dataAdapter.deleteOrder(id, orderProduct);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Order with id: " + id + " was deleted");
                } else {
                    JOptionPane.showMessageDialog(null, "Order was not deleted");
                }

            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        SpringUtilities.makeCompactGrid(main,
//                3, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
        buttonPanel.add(addOrder);
        buttonPanel.add(updateOrder);
        buttonPanel.add(readButton);
        buttonPanel.add(deleteButton);

        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }
}
