import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerView extends JFrame {
    private JTextField CustomerIDField;
    private JTextField CustomerNameField;
    private JTextField CustomerEmailField;
    private JButton addCustomer;
    private JButton updateCustomer;
    private JButton readButton;
    private JButton deleteButton;

    private void displayCustomerDetails(Customer Customer){
        JFrame details = new JFrame("Job Details");

        details.setSize(300,200);
        details.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        details.setLayout(new GridLayout(3,1));

        JLabel idLabel = new JLabel("ID: " + Customer.customerID);
        JLabel nameLabel = new JLabel("Customer Name: " + Customer.customerName);
        JLabel classLabel = new JLabel("Customer Email: " + Customer.customerEmail);

        details.add(idLabel);
        details.add(nameLabel);
        details.add(classLabel);

        details.setVisible(true);
    }

    public AddCustomerView() {
        setTitle("Add Customer Class");
        this.setSize(500, 300);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel ("Customer View"));

//        JPanel main = new JPanel(new SpringLayout());
        JPanel main = new JPanel(new GridLayout(4,2,5,5));

        main.add(new JLabel("Customer ID: "));
        CustomerIDField= new JTextField();
        main.add(CustomerIDField);

        main.add(new JLabel("Customer Name: "));
        CustomerNameField = new JTextField();
        main.add(CustomerNameField);

        main.add(new JLabel("Email:  "));
        CustomerEmailField = new JTextField();
        main.add(CustomerEmailField);

        addCustomer = new JButton("Add Customer");
        addCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CustomerID;
                String inputCustomerID = CustomerIDField.getText().trim();
                if (inputCustomerID.isEmpty()) {
                    CustomerID = Application.getInstance().dataAdapter.getNextCustomerID();
                } else {
                    try {
                        CustomerID = Integer.parseInt(inputCustomerID);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "invalid customer ID");
                        return;
                    }
                }

                String CustomerName = CustomerNameField.getText().trim();
                if (CustomerName.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter a valid Customer name");
                }

                String customerEmail = CustomerEmailField.getText().trim();
                if (customerEmail.length() == 0) {
                    JOptionPane.showMessageDialog(null, "invalid email");
                }

                Customer Customer = new Customer(CustomerID, CustomerName, customerEmail);

                boolean res = Application.getInstance().dataAdapter.createCustomer(Customer);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "Customer is NOT saved!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Customer created!");
                }

            }
        });

        updateCustomer = new JButton("Update Customer");
        updateCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CustomerID;
                try {
                    CustomerID = Integer.parseInt(CustomerIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid customer ID");
                    return;
                }

                String CustomerName = CustomerNameField.getText().trim();
                if (CustomerName.length() == 0) {
                    JOptionPane.showMessageDialog(null, "invalid customer name");
                }

                String inputEmail = CustomerEmailField.getText().trim();

                Customer Customer = new Customer(CustomerID, CustomerName, inputEmail);

                boolean res = Application.getInstance().dataAdapter.updateCustomer(Customer);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "Customer is NOT updated!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Customer updated!");
                }

            }
        });

        readButton = new JButton("Read Customer");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(CustomerIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Customer ID! Please provide a valid ID!");
                    return;
                }

                Customer result = Application.getInstance().dataAdapter.readCustomer(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "1 Customer with id: " + id + " was not found");
                }
                else {
                    displayCustomerDetails(result);
                }

            }
        });

        deleteButton = new JButton("Delete Customer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(CustomerIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Customer ID! Please provide a valid ID!");
                    return;
                }

                Customer result = Application.getInstance().dataAdapter.deleteCustomer(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Customer with id: " + id + " was not found");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Customer Deleted");
                }

            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        SpringUtilities.makeCompactGrid(main,
//                3, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
        buttonPanel.add(addCustomer);
        buttonPanel.add(updateCustomer);
        buttonPanel.add(readButton);
        buttonPanel.add(deleteButton);

        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


    }

}