import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductView extends JFrame {

    private JTextField ProductsIDField;
    private JTextField ProductsNameField;
    private JTextField ProductsPriceField;
    private JTextField ProductsSupplierField;
    private JButton addProducts;
    private JButton updateProducts;
    private JButton readButton;
    private JButton deleteButton;

    private void displayProductsDetails(Products Products){
        JFrame details = new JFrame("Job Details");

        details.setSize(300,200);
        details.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        details.setLayout(new GridLayout(3,1));

        JLabel idLabel = new JLabel("ID: " + Products.productID);
        JLabel nameLabel = new JLabel("Product Name: " + Products.productName);
        JLabel priceLabel = new JLabel("Product Price: " +  Products.productPrice);
        JLabel supplierLabel = new JLabel("Product Supplier: " + Products.supplierName);


        details.add(idLabel);
        details.add(nameLabel);
        details.add(priceLabel);
        details.add(supplierLabel);

        details.setVisible(true);
    }

    public AddProductView() {
        setTitle("Add Products Class");
        this.setSize(500, 300);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel ("Products View"));

//        JPanel main = new JPanel(new SpringLayout());
        JPanel main = new JPanel(new GridLayout(4,2,5,5));

        main.add(new JLabel("Product ID: "));
        ProductsIDField= new JTextField();
        main.add(ProductsIDField);

        main.add(new JLabel("Product Name: "));
        ProductsNameField = new JTextField();
        main.add(ProductsNameField);

        main.add(new JLabel("Product Price  "));
        ProductsPriceField = new JTextField();
        main.add(ProductsPriceField);

        main.add(new JLabel("Supplier ID"));
        ProductsSupplierField = new JTextField();
        main.add(ProductsSupplierField);

        addProducts = new JButton("Add Product");
        addProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int inputProductsID;
                String productsIDText = ProductsIDField.getText().trim();
                if (productsIDText.isEmpty()) {
                    inputProductsID = Application.getInstance().dataAdapter.getNewProductID();
                    System.out.println("the new ID is: " + inputProductsID);
                } else {
                    try {
                        inputProductsID = Integer.parseInt(productsIDText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Products ID! Please provide a valid ID!");
                        return;
                    }
                }

                String inputProductName = ProductsNameField.getText().trim();
                if (inputProductName.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter a valid Products name");
                }

                double inputPrice;
                try {
                    inputPrice = Double.parseDouble(ProductsPriceField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid price entered");
                    return;
                }
                int inputSupplierID;
                try {
                    inputSupplierID = Integer.parseInt(ProductsSupplierField.getText());
                }         catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "invalid supplier ID");
                    return;
                }

               String supplierName = Application.getInstance().dataAdapter.getSupplierName(inputSupplierID);


                Products Product = new Products(inputProductsID, inputProductName, inputPrice, inputSupplierID, supplierName);

                boolean res = Application.getInstance().dataAdapter.createProduct(Product);
                if (!res) {
                    JOptionPane.showMessageDialog(null, "Products is NOT saved!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Products created!");
                }

            }
        });

        updateProducts = new JButton("Update Products");
        updateProducts.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
//                 product ID is the only field that has to be filled
                 int inputProductsID;
                 try {
                     inputProductsID = Integer.parseInt(ProductsIDField.getText());
                 } catch (NumberFormatException ex) {
                     JOptionPane.showMessageDialog(null, "Invalid Products ID! Please provide a valid ID!");
                     return;
                 }

                 String inputProductName = ProductsNameField.getText().trim();

                 double inputPrice = -1; // Default value
                 if (!ProductsPriceField.getText().trim().isEmpty()) {
                     try {
                         inputPrice = Double.parseDouble(ProductsPriceField.getText());
                     } catch (NumberFormatException ex) {
                         JOptionPane.showMessageDialog(null, "Invalid price entered");
                         return;
                     }
                 }

                 int inputSupplierID = -1; // Default value
                 if (!ProductsSupplierField.getText().trim().isEmpty()) {
                     try {
                         inputSupplierID = Integer.parseInt(ProductsSupplierField.getText());
                     } catch (NumberFormatException ex) {
                         JOptionPane.showMessageDialog(null, "Invalid supplier ID");
                         return;
                     }
                 }

                 Products toBeUpdated = Application.getInstance().dataAdapter.readProduct(inputProductsID);

                 // use the current ID's value if the field is left blank
                 if (inputProductName.isEmpty()) {
                     inputProductName = toBeUpdated.productName;
                 }
                 if (inputPrice == -1) {
                     inputPrice = toBeUpdated.productPrice;
                 }
                 if (inputSupplierID == -1) {
                     inputSupplierID = toBeUpdated.supplierID;
                 }

                 String supplierName = Application.getInstance().dataAdapter.getSupplierName(inputSupplierID);
                 Products product = new Products(inputProductsID, inputProductName, inputPrice, inputSupplierID, supplierName);

                 boolean res = Application.getInstance().dataAdapter.updateProduct(product);
                 if (!res) {
                     JOptionPane.showMessageDialog(null, "Products is NOT updated!");
                 } else {
                     JOptionPane.showMessageDialog(null, "Products updated!");
                 }
             }
         });

        readButton = new JButton("Read Products");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(ProductsIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Products ID! Please provide a valid ID!");
                    return;
                }

                Products result = Application.getInstance().dataAdapter.readProduct(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Products with id: " + id + " was not found");
                }
                else {
                    displayProductsDetails(result);
                }

            }
        });

        deleteButton = new JButton("Delete Products");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(ProductsIDField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Products ID! Please provide a valid ID!");
                    return;
                }

                Products result = Application.getInstance().dataAdapter.deleteProduct(id);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Products with id: " + id + " was not found");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Products Deleted");
                }

            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        SpringUtilities.makeCompactGrid(main,
//                3, 2, //rows, cols
//                6, 6,        //initX, initY
//                6, 6);       //xPad, yPad
        buttonPanel.add(addProducts);
        buttonPanel.add(updateProducts);
        buttonPanel.add(readButton);
        buttonPanel.add(deleteButton);

        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


    }
}
