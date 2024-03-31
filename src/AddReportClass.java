import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Timestamp;
public class AddReportClass extends JFrame {
    public AddReportClass() {
        setTitle("Sales Report");
        setSize(500, 300);
        setLayout(new GridLayout(5, 2));

        JLabel startDateLabel = new JLabel("Start Date:");
        JTextField startDateField = new JTextField();
        JLabel stopDateLabel = new JLabel("Stop Date:");
        JTextField stopDateField = new JTextField();
        this.add(startDateLabel);
        this.add(startDateField);
        this.add(stopDateLabel);
        this.add(stopDateField);


        JLabel descendingLabel = new JLabel("Descending: ");
        JCheckBox descendingCheckBox = new JCheckBox();
        add(descendingLabel);
        add(descendingCheckBox);

        JLabel cutOffLabel = new JLabel("Cutoff Number: ");
        JTextField cutoffField = new JTextField();
        add(cutOffLabel);
        add(cutoffField);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3)); // Use GridLayout for buttons
        JButton monthReportButton = new JButton("Month Report");

        monthReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputStart = startDateField.getText().trim();
                String inputEnd = stopDateField.getText().trim();

                if (inputStart.length() == 0 || inputEnd.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter proper start or end date");
                }
                Timestamp startDate = Timestamp.valueOf(inputStart);
                Timestamp endDate = Timestamp.valueOf(inputEnd);


                int cutoff;
                String cutoffText = cutoffField.getText().trim();
                if (!cutoffText.isEmpty()) {
                    try {
                        cutoff = Integer.parseInt(cutoffText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid cutoff number");
                        return;
                    }
                } else {
                    cutoff = 999; // Default cutoff value
                }

                boolean descending = descendingCheckBox.isSelected();

                boolean done = Application.getInstance().dataAdapter.MonthlySales(startDate, endDate, descending, cutoff);
                if (!done) {
                    JOptionPane.showMessageDialog(null, "monthly report not generated");
                } else {
                    JOptionPane.showMessageDialog(null, "monthly report generated!");
                }

            }
        });

        JButton productReportButton = new JButton("Product Report");
        productReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputStart = startDateField.getText().trim();
                String inputEnd = stopDateField.getText().trim();

                if (inputStart.length() == 0 || inputEnd.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter proper start or end date");
                }
                Timestamp startDate = Timestamp.valueOf(inputStart);
                Timestamp endDate = Timestamp.valueOf(inputEnd);


                int cutoff;
                String cutoffText = cutoffField.getText().trim();
                if (!cutoffText.isEmpty()) {
                    try {
                        cutoff = Integer.parseInt(cutoffText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid cutoff number");
                        return;
                    }
                } else {
                    cutoff = 999; // Default cutoff value
                }

                boolean descending = descendingCheckBox.isSelected();

                boolean done = Application.getInstance().dataAdapter.SalePerProduct(startDate, endDate, descending, cutoff);
                if (!done) {
                    JOptionPane.showMessageDialog(null, "product report not generated");
                } else {
                    JOptionPane.showMessageDialog(null, "product report generated!");
                }
            }
        });
        JButton customerReportButton = new JButton("Customer Report");
        customerReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputStart = startDateField.getText().trim();
                String inputEnd = stopDateField.getText().trim();

                if (inputStart.length() == 0 || inputEnd.length() == 0) {
                    JOptionPane.showMessageDialog(null, "enter proper start or end date");
                }
                Timestamp startDate = Timestamp.valueOf(inputStart);
                Timestamp endDate = Timestamp.valueOf(inputEnd);


                int cutoff;
                String cutoffText = cutoffField.getText().trim();
                if (!cutoffText.isEmpty()) {
                    try {
                        cutoff = Integer.parseInt(cutoffText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid cutoff number");
                        return;
                    }
                } else {
                    cutoff = 999; // Default cutoff value
                }

                boolean descending = descendingCheckBox.isSelected();

                boolean done = Application.getInstance().dataAdapter.SalePerCustomer(startDate, endDate, descending, cutoff);
                if (!done) {
                    JOptionPane.showMessageDialog(null, "customer report not generated");
                } else {
                    JOptionPane.showMessageDialog(null, "customer report generated!");
                }

            }
        });
        buttonPanel.add(monthReportButton);
        buttonPanel.add(productReportButton);
        buttonPanel.add(customerReportButton);
        add(buttonPanel);

    }

}