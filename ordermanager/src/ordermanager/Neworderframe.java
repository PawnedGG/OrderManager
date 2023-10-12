/*
*This is the file that implements the operations happening in "New Order" window.
*/
package ordermanager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

public class Neworderframe extends JDialog {
    private JButton submitBtn, cancelBtn, clearBtn;
    private JLabel nameLbl, itemLbl, netPLbl, quantityLbl, taxRLbl;
    private JTextField nameTxf, quantityTxf, netPTxf, taxRTxf;
    private JComboBox itemCBox;
    private String items[] = { "", "TV", "MOTHERBOARD", "GPU", "CPU", "RAM", "CASE" };
    private JPanel mainPanel, namePanel, itemPanel, sumPanel, flowbtnPanel1, flowbtnPanel2;

    private double tvPrice = 220, motherboardPrice = 110, gpuPrice = 250, cpuPrice = 200, ramPrice = 80, casePrice = 70;

    public Neworderframe() {
        super();

        submitBtn = new JButton("Submit");
        cancelBtn = new JButton("Cancel");
        clearBtn = new JButton("Clear");

        nameLbl = new JLabel("Name:\t");
        itemLbl = new JLabel("Item:\t");
        quantityLbl = new JLabel("Quantity:\t");
        netPLbl = new JLabel("Net Price:\t");
        taxRLbl = new JLabel("Tax Rate:\t");

        nameTxf = new JTextField(20);
        quantityTxf = new JTextField(3);
        netPTxf = new JTextField(10);
        taxRTxf = new JTextField(3);

        //We use a combo box so that the user has an easier time choosing the item he wants to buy.
        itemCBox = new JComboBox(items);

        mainPanel = new JPanel();
        namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sumPanel = new JPanel(new GridLayout(1, 2));
        flowbtnPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowbtnPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    }

    //Here we prepare our window. Every action performed in the window is being handled here.
    public void prepareUI(Orders order, Mainframe frame, int counter) {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        this.add(sumPanel, BorderLayout.SOUTH);

        netPTxf.setEditable(false);        
        taxRTxf.setEditable(false);

        mainPanel.add(namePanel);
        mainPanel.add(itemPanel);

        namePanel.add(nameLbl);
        namePanel.add(nameTxf);

        itemPanel.add(itemLbl);
        itemPanel.add(itemCBox);
        itemPanel.add(quantityLbl);
        itemPanel.add(quantityTxf);


        sumPanel.add(flowbtnPanel1);
        sumPanel.add(flowbtnPanel2);

        flowbtnPanel1.add(netPLbl);
        flowbtnPanel1.add(netPTxf);
        flowbtnPanel1.add(taxRLbl);
        flowbtnPanel1.add(taxRTxf);
        flowbtnPanel2.add(clearBtn);
        flowbtnPanel2.add(cancelBtn);
        flowbtnPanel2.add(submitBtn);

        //Action when user is selecting an item. Item prices and taxrate are hardcoded.
        itemCBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> itemComb = (JComboBox<String>) e.getSource();
                String selectedItem = (String) itemComb.getSelectedItem();

                if(selectedItem.equals("TV")){
                    netPTxf.setText(String.valueOf(tvPrice));
                    taxRTxf.setText("24");
                }
                if(selectedItem.equals("MOTHERBOARD")){
                    netPTxf.setText(String.valueOf(motherboardPrice));
                    taxRTxf.setText("24");
                }
                if(selectedItem.equals("GPU")){
                    netPTxf.setText(String.valueOf(gpuPrice));
                    taxRTxf.setText("24");
                }
                if(selectedItem.equals("CPU")){
                    netPTxf.setText(String.valueOf(cpuPrice));
                    taxRTxf.setText("24");
                }
                if(selectedItem.equals("RAM")){
                    netPTxf.setText(String.valueOf(ramPrice));
                    taxRTxf.setText("24");
                }
                if(selectedItem.equals("CASE")){
                    netPTxf.setText(String.valueOf(casePrice));
                    taxRTxf.setText("24");
                }
                //updateNetPrice in line 221
                updateNetPrice();
            }        
        });

        //Action when user is selecting quantity. For every action the user performs the netPrice property is updated.
        quantityTxf.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateNetPrice();
            }
        
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateNetPrice();
            }
        
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        //Action when user presses the "Cancel" button.
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }); 

        //Action when user presses the "Clear" button.
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameTxf.setText("");
                quantityTxf.setText("");
                netPTxf.setText("");
            }
        }); 

        //Action when user presses the "Submit" button
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cName = nameTxf.getText();
                String qnt = quantityTxf.getText();
                String item = (String)itemCBox.getSelectedItem();
                boolean valid = true;

                //We check every possible input from the user so that he can not add an incomplete order.
                if((cName != null && !cName.isEmpty()) && (qnt != null && !qnt.isEmpty()) && !item.equals("")){
                    try{
                        Integer.parseInt(quantityTxf.getText());
                    }catch(NumberFormatException event){
                        JOptionPane.showMessageDialog(submitBtn, "Quantity must be an integer", "Wrong Input", JOptionPane.INFORMATION_MESSAGE);
                        valid = false;
                    }
                    if(valid == true){
                        order.setAppID("711171060");
                        order.setOrderID(counter);
                        order.setDate(LocalDate.now(ZoneId.systemDefault()).toString());
                        order.setClientName(cName);
                        order.setItemName(item);
                        order.setQuantity(Integer.parseInt(qnt));
                        order.setNetPrice(Double.parseDouble(netPTxf.getText()));
                        order.setTaxRate((Double.parseDouble(taxRTxf.getText())) / 100.0);
                        frame.addOrder(order);
                        dispose();

                        //Code description:
                        //LocalDate now = LocalDate.now(ZoneId.systemDefault());
                        //String appID = "711171060";
                        //int orderID = counter;                     
                        //String clientName = cName;
                        //String date = now.toString();                    
                        //String itemName = item;
                        //int quantity = (Integer.parseInt(qnt));
                        //double netPrice = (Double.parseDouble(netPTxf.getText()));
                        //double taxRate = (Double.parseDouble(taxRTxf.getText())) / 100.0;
                        
                        
                    }
                }
                else{
                    JOptionPane.showMessageDialog(submitBtn, "All boxes must be filled", "Wrong Input", JOptionPane.INFORMATION_MESSAGE);
                }

                                                            
            }
        });
    }

    //updateNetPrice is constantly updating the netPrice attribute with every change in the quantity variable.
    private void updateNetPrice(){
        String qnt = quantityTxf.getText();
        boolean valid = true;

        if(qnt != null && !qnt.isEmpty()){
            try{
                Integer.parseInt(quantityTxf.getText());
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(submitBtn, "Quantity must be an integer", "Wrong Input", JOptionPane.INFORMATION_MESSAGE);
                valid = false;
            }
            //This code segment is executed only when the quantity variable contains an integer.
            if(valid == true){
                if(itemCBox.getSelectedItem().equals("")){
                netPTxf.setText("0.0");
                }else if(itemCBox.getSelectedItem().equals("TV")){
                    netPTxf.setText(String.valueOf(tvPrice * Integer.parseInt(quantityTxf.getText())));
                }else if(itemCBox.getSelectedItem().equals("MOTHERBOARD")){
                    netPTxf.setText(String.valueOf(motherboardPrice * Integer.parseInt(quantityTxf.getText())));
                }else if(itemCBox.getSelectedItem().equals("GPU")){
                    netPTxf.setText(String.valueOf(gpuPrice * Integer.parseInt(quantityTxf.getText())));
                }else if(itemCBox.getSelectedItem().equals("CPU")){
                    netPTxf.setText(String.valueOf(cpuPrice * Integer.parseInt(quantityTxf.getText())));
                }else if(itemCBox.getSelectedItem().equals("RAM")){
                    netPTxf.setText(String.valueOf(ramPrice * Integer.parseInt(quantityTxf.getText())));
                }else{
                    netPTxf.setText(String.valueOf(casePrice * Integer.parseInt(quantityTxf.getText())));
                }
            }else{
                netPTxf.setText("0.0");
            }                
        }else{
            netPTxf.setText("0.0");
        }
    }  
} 