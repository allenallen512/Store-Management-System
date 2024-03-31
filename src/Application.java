import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    public DataAccess dataAdapter = new SQLDataAdaptor();
    public AddSupplierView supplierView = new AddSupplierView();
    public AddOrderView orderView = new AddOrderView();
    public AddCustomerView customerView = new AddCustomerView();
    public AddProductView productView = new AddProductView();
    public AddPaymentView paymentView = new AddPaymentView();

    public AddReportClass reportView = new AddReportClass();
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private static Application instance;   // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }


    //used online resources to see how I could make everythign appear in one window instead of seperate windows.

    public static void main(String[] args) {

        System.out.println("Hello world!");
        Application application = Application.getInstance();
        application.dataAdapter.connect();

//        application.employeeView.setVisible(true);


        JFrame homepageFrame = new JFrame("Homepage");
        homepageFrame.setSize(500, 300);
        homepageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homepageFrame.setLayout(new FlowLayout());

        // Button for Customer View
        JButton jobClassButton = new JButton("Click for Customer View");
        jobClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.customerView.setVisible(true);
            }
        });
        homepageFrame.add(jobClassButton);

        JButton projectClassButton = new JButton("Click for Supplier view");
        projectClassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.supplierView.setVisible(true);
            }
        });
        homepageFrame.add(projectClassButton);

        // Button for Employee
        JButton employeeButton = new JButton("Click for Product View");
        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                application.productView.setVisible(true);
            }
        });
        homepageFrame.add(employeeButton);

        // Button for Order View
        JButton orderButton = new JButton("Click for Order View");
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.orderView.setVisible(true);
            }
        });
        homepageFrame.add(orderButton);

        JButton paymentButton = new JButton("Click for Payment View");
        paymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.paymentView.setVisible(true);
            }
        });
        homepageFrame.add(paymentButton);

        JButton reportButton = new JButton("Click for Reports View");
        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                application.reportView.setVisible(true);
            }
        });
        homepageFrame.add(reportButton);


        // Make the homepage frame visible
        homepageFrame.setVisible(true);
    }
    }
