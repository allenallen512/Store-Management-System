import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPaymentView extends JFrame {

    private JTextField PaymentIDField;
    private JTextField CustomerIDField;
    private JTextField PaymentTotalField;

    private JTextField PaymentTypeField;
    private JTextField PaymentOrderID;
    private JButton addButton;
    private JButton updateButton;
    private JButton readButton;
    private JButton deleteButton;

    private void displayPaymentDetails(Payments payment) {
        JFrame details = new JFrame("Payment Details");

        details.setSize(300, 200);
        details.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        details.setLayout(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("Payment ID: " + payment.paymentID);
        JLabel customerIDLabel = new JLabel("Customer ID: " + payment.customerID);
        JLabel totalLabel = new JLabel("Payment Total: " + payment.paymentTotal);
        JLabel typeLabel = new JLabel("Payment Type: " + payment.paymentType);
        JLabel orderIDLabel = new JLabel("Order ID: " + payment.orderID);

        details.add(idLabel);
        details.add(customerIDLabel);
        details.add(totalLabel);
        details.add(typeLabel);
        details.add(orderIDLabel);

        details.setVisible(true);
    }


    public AddPaymentView() {

        setTitle("Add Payment View");
        this.setSize(500, 300);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel ("Payment View"));

//        JPanel main = new JPanel(new SpringLayout());
        JPanel main = new JPanel(new GridLayout(6,2,5,5));

        main.add(new JLabel("Payment ID:"));
        PaymentIDField = new JTextField();
        main.add(PaymentIDField);

        main.add(new JLabel("Customer ID:"));
        CustomerIDField = new JTextField();
        main.add(CustomerIDField);

        main.add(new JLabel("Payment Total:"));
        PaymentTotalField = new JTextField();
        main.add(PaymentTotalField);

        main.add(new JLabel("Payment Type:"));
        PaymentTypeField = new JTextField();
        main.add(PaymentTypeField);

        main.add(new JLabel("Order ID:"));
        PaymentOrderID = new JTextField();
        main.add(PaymentOrderID);

        addButton = new JButton("Add Payment");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String paymentIDText = PaymentIDField.getText().trim();
                if (paymentIDText.isEmpty()) {
                    id = Application.getInstance().dataAdapter.getNextPaymentID();
                } else {
                    try {
                        id = Integer.parseInt(paymentIDText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid payment ID");
                        return;
                    }
                }
                int customerID;
                try {
                    customerID = Integer.parseInt(CustomerIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid customer ID");
                    return;
                }

                double paymentTotal;
                try {
                    paymentTotal = Double.parseDouble(PaymentTotalField.getText());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "invalid payment total");
                    return;
                }

                String paymentType = PaymentTypeField.getText().trim();
                if (paymentType.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "invalid payment type");
                    return;
                }

                int orderID;
                try {
                    orderID = Integer.parseInt(PaymentOrderID.getText());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "invalid order ID");
                    return;
                }

                Payments newPayment = new Payments(id, customerID, paymentTotal, paymentType, orderID);

                boolean res = Application.getInstance().dataAdapter.createNewPayment(newPayment);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "payment is NOT saved!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "payment created!");
                }

            }
        });

        updateButton = new JButton("Update Payment");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(PaymentIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid payment ID");
                    return;
                }
                int customerID;
                try {
                    customerID = Integer.parseInt(CustomerIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid customer ID");
                    return;
                }

                double paymentTotal;
                try {
                    paymentTotal = Double.parseDouble(PaymentTotalField.getText());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "invalid payment total");
                    return;
                }

                String paymentType = PaymentTypeField.getText().trim();
                if (paymentType.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "invalid payment type");
                    return;
                }

                int orderID;
                try {
                    orderID = Integer.parseInt(PaymentOrderID.getText());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "invalid order ID");
                    return;
                }

                Payments updatePayment = new Payments(id, customerID, paymentTotal, paymentType, orderID);
                boolean res = Application.getInstance().dataAdapter.updatePayment(updatePayment);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "Payment is NOT updated!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Payment is Updated");
                }
            }
        });

        readButton = new JButton("Read Payment");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(PaymentIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid payment ID! Please provide a valid ID!");
                    return;
                }

                Payments result = Application.getInstance().dataAdapter.readPayment(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "payment with id: " + id + " was not found");
                }
                else {
                    displayPaymentDetails(result);
                }

            }
        });

        deleteButton = new JButton("Delete Payment");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(PaymentIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid payment ID! Please provide a valid ID!");
                    return;
                }

                Payments result = Application.getInstance().dataAdapter.deletePayment(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "payment with id: " + id + " was not found");
                }
                else {
                    JOptionPane.showMessageDialog(null, "payment: " + id + " Deleted");
                }

            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        SpringUtilities.makeCompactGrid(main,
//                3, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(readButton);
        buttonPanel.add(deleteButton);


        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

//        this.getContentPane().add(main);
//        this.getContentPane().add(addButton);
//        this.getContentPane().add(updateButton);

    }

}
