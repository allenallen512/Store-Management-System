import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSupplierView extends JFrame {
    private JTextField supplierIDField;
    private JTextField supplierNameField;
    private JTextField supplierEmailField;
    private JButton addButton;
    private JButton updateButton;
    private JButton readButton;
    private JButton deleteButton;

    private void displaysupplierDetails(Supplier supplier){
        JFrame details = new JFrame("supplier Details");

        details.setSize(300,200);
        details.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        details.setLayout(new GridLayout(3,1));

        JLabel idLabel = new JLabel("Supplier ID: " + supplier.SupplierID);
        JLabel titleLabel = new JLabel("supplier Name: " + supplier.SupplierName);
        JLabel wageLabel = new JLabel("supplier Email: " + supplier.SupplierEmail);

        details.add(idLabel);
        details.add(titleLabel);
        details.add(wageLabel);

        details.setVisible(true);
    }

    public AddSupplierView() {

        setTitle("Add supplier Class");
        this.setSize(500, 300);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel ("supplier View! :)"));

//        JPanel main = new JPanel(new SpringLayout());
        JPanel main = new JPanel(new GridLayout(4,2,5,5));

        main.add(new JLabel("supplierID:"));
        supplierIDField = new JTextField();
        main.add(supplierIDField);

        main.add(new JLabel("Supplier Name:"));
        supplierNameField = new JTextField();
        main.add(supplierNameField);

        main.add(new JLabel("Supplier Email"));
        supplierEmailField = new JTextField();
        main.add(supplierEmailField);

        addButton = new JButton("Add Supplier");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String inputSupplierID = supplierIDField.getText().trim();
                if (inputSupplierID.isEmpty()) { //if empty , it will call function to get thenext ID
                    id = Application.getInstance().dataAdapter.getNextSupplierID();
                } else {
                    try {
                        id = Integer.parseInt(inputSupplierID);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid supplier ID! Please provide a valid ID!");
                        return;
                    }
                }
                String inputName = supplierNameField.getText().trim();

                String inputEmail= supplierEmailField.getText().trim();

                if (inputEmail.length() == 0) {
                    JOptionPane.showMessageDialog(null, "invalid email!");
                    return;
                }

                Supplier model = new Supplier();
                model.SupplierID = id;
                model.SupplierName = inputName;
                model.SupplierEmail = inputEmail;

                // Store the model to the database

                boolean res = Application.getInstance().dataAdapter.createSupplier(model);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "supplier is NOT saved!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "supplier created!");
                }

            }
        });

        updateButton = new JButton("Update supplier");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(supplierIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid supplier ID! Please provide a valid ID!");
                    return;
                }
                String inputName = supplierNameField.getText().trim();
                if (inputName.length() == 0) {
                    JOptionPane.showMessageDialog(null, "the name section cannot be empty");
                }

                String inputEmail = supplierEmailField.getText().trim();

                if (inputEmail.length() == 0) {
                    JOptionPane.showMessageDialog(null, "invalid email");
                    return;
                }

                Supplier newSupplier = new Supplier(id, inputName, inputEmail);


                boolean res = Application.getInstance().dataAdapter.updateSupplier(newSupplier);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "supplier is NOT updated!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "supplier is Updated");
                }
            }
        });

        readButton = new JButton("Read supplier");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(supplierIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid supplier ID! Please provide a valid ID!");
                    return;
                }

                Supplier result = Application.getInstance().dataAdapter.readSupplier(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "supplier with id: " + id + " was not found");
                }
                else {
                    displaysupplierDetails(result);
                }

            }
        });

        deleteButton = new JButton("Delete Supplier");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(supplierIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid supplier ID! Please provide a valid ID!");
                    return;
                }

                Supplier result = Application.getInstance().dataAdapter.deleteSupplier(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "supplier with id: " + id + " was not found");
                }
                else {
                    JOptionPane.showMessageDialog(null, "supplier Class Deleted");
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
