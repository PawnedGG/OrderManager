/*
*This is the file that implements the operations happening in main window.
*From this window 3 others are being created called: "Aboutframe"(line 189), "Statsframe"(line 174), "Neworderframe(line 157)".
*Main idea is that we keep Borderlayout in our window and we use panels to achieve a better looking window via different layouts set to panels.
*/
package ordermanager;

import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Mainframe extends JFrame {
    private JMenuBar bar;
    private JMenu fileMenu, helpMenu;
    private JMenuItem openMItem, saveMItem, saveasMItem, aboutMItem;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private DefaultTableCellRenderer renderer;
    private JButton orderBtn, statsBtn, exitBtn;
    private JLabel filePath;
    private JPanel btnPanel, flowbtnPanel1, flowbtnPanel2;
    private JScrollPane textScroll;
    private ArrayList<Orders> orderlist;
    private String filename;
    private Boolean isLoaded = false, isSaved = false, isBlankFile = true;
    private int saveChoice = 0;
    String[] colNames = {"ORDERID", "DATE", "CLIENTNAME", "ITEMNAME", "QUANTITY", "NETPRICE", "TAXRATE" };

    public Mainframe() {
        super();

        orderlist = new ArrayList();

        bar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        //We use panels so we can have different layouts on different parts of the window
        btnPanel = new JPanel(new GridLayout(1, 2));
        flowbtnPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowbtnPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));   
        
        //We use a table model so we can manage table data the way we want.
        tableModel = new DefaultTableModel(colNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        //We use a renderer so we can manipulate they way data is being dispplayed in our table.
        renderer = new DefaultTableCellRenderer();

        //We use scroll pane to manage the dynamic nature of the table used to display data
        dataTable = new JTable(tableModel);
        textScroll = new JScrollPane(dataTable);

        filePath = new JLabel();

        orderBtn = new JButton("New Order");
        statsBtn = new JButton("Statistics");
        exitBtn = new JButton("Exit");

    }

    
    //Here we prepare our window. Every action performed in the window is being handled here.
    public void prepareUI(Mainframe frame) {
        //Set components in window.
        this.add(textScroll, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.SOUTH);
        this.setJMenuBar(bar);

        //Bar options placed in bar and creation of shortcuts better for better accessibility.
        fileMenu.setMnemonic('F');
        helpMenu.setMnemonic('H');
        openMItem = fileMenu.add("Open file...");
        openMItem.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
        fileMenu.addSeparator();
        saveMItem = fileMenu.add("Save");
        saveMItem.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        saveasMItem = fileMenu.add("Save as...");
        saveasMItem.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK | Event.SHIFT_MASK));
        aboutMItem = helpMenu.add("About");
        aboutMItem.setAccelerator(KeyStroke.getKeyStroke('I', Event.CTRL_MASK));
        bar.add(fileMenu);
        bar.add(helpMenu);

        //Table attributes. setFillsViewportHeight makes table use the whole space in the ScrollPane and setShowGrid hides the cell seperators.
        dataTable.setFillsViewportHeight(true);
        dataTable.setShowGrid(false);
        
        //Use of 2 panels in a panel with GridLayout to achieve the desired placement of the buttons.
        btnPanel.add(flowbtnPanel1);
        btnPanel.add(flowbtnPanel2);
        //setSize to achieve uniformallity.
        orderBtn.setPreferredSize(new Dimension(95, 26));
        statsBtn.setPreferredSize(new Dimension(95, 26));
        exitBtn.setPreferredSize(new Dimension(95, 26));

        //Button placement in their panels.
        flowbtnPanel1.add(orderBtn);
        flowbtnPanel1.add(statsBtn);   
        flowbtnPanel1.add(filePath);
        flowbtnPanel2.add(exitBtn);

        //Action when user presses the "Exit" button. The button prompts the user to save only when he has not saved his changes and detects blank files.
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                if(isSaved == true){
                    System.exit(0);
                }else if(isBlankFile == true){
                    System.exit(0);
                }else{
                    int a = JOptionPane.showConfirmDialog(flowbtnPanel2, 
                                "Save your changes to this file?", 
                                "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(a == JOptionPane.YES_OPTION){
                        //saveToFile in line 346
                        saveToFile();
                        System.exit(0);
                    }else if(a == JOptionPane.NO_OPTION){
                        System.exit(0);
                    }else{
                        return;
                    }
                }
                
            }
        });

        //Action when user presses "New Order" button. A new dialog window is being created for user to input data. Dialog is being used so we can achieve modality.(Go to Neworderframe.java for further analysis)
        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Orders order = new Orders();
                Neworderframe orderframe = new Neworderframe();
                orderframe.setModalityType(ModalityType.APPLICATION_MODAL);
                orderframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                orderframe.setSize(565, 150);
                orderframe.setResizable(false);
                //Values are given to the dialog window in order to get the user input back using existing methods.
                orderframe.prepareUI(order, frame, orderlist.size() + 1);
                orderframe.setTitle("New Order");
                orderframe.setVisible(true);
            }
        });

        //Action when user presses "Statistics" button. A new window is being created so that user can see stats about the currently displayed orders.(Go to Statsframe.java for further analysis)
        statsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statsframe statsframe = new Statsframe();
                statsframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                statsframe.setSize(550, 200);
                statsframe.setResizable(false);
                //Arraylist is being passes to the window in order to operate in the orders stored to it.
                statsframe.prepareUI(orderlist);
                statsframe.setTitle("Statistics");
                statsframe.setVisible(true);
            }
        });

        //Action when user presses "About" button. A new window is being created displaying informations about the author.(Go to Aboutframe.java for further analysis)
        aboutMItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aboutframe aboutframe = new Aboutframe();
                aboutframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                aboutframe.setSize(500, 400);
                aboutframe.setResizable(false);
                aboutframe.prepareUI();
                aboutframe.setTitle("About");
                aboutframe.setVisible(true);
            }
        });

        //Action when user presses "Save" option from the "File" menu.
        saveMItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Variable used to determine what save operation will be performed.(Save or Save as)
                saveChoice = 1;
                //Save to file in line 346
                saveToFile();
            }

        });

        //Action when user presses "Save As" option from the "File" menu.
        saveasMItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Variable used to determine what save operation will be performed.(Save or Save as)
                saveChoice = 2;
                saveToFile();
            }

        });

        //Action when user presses "Open" option from "File" menu. 
        openMItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //loadFromFile in line 397
                loadFromFile();
            }            
        });

        //Action when user attempts to close the window from top-right corner button "X". Same logic as "Exit" button.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(isSaved == true){
                    System.exit(0);
                }else if(isBlankFile == true){
                    System.exit(0);
                }else{
                    int a = JOptionPane.showConfirmDialog(flowbtnPanel2, 
                                "Save your changes to this file?", 
                                "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(a == JOptionPane.YES_OPTION){
                        //saveToFile in line 346
                        saveToFile();
                        System.exit(0);
                    }else if(a == JOptionPane.NO_OPTION){
                        System.exit(0);
                    }else{
                        return;
                    }
                }
            }           
        });
    }

    //Function to add newly created order in the arraylist.
    public void addOrder(Orders order){
        orderlist.add(order);
        //showOrders in line 415
        showOrders(order);
        //That's how our algorith recognises a change in the active file.
        isSaved = false;
        isBlankFile = false;
    }


    //Function to create a writer in order to save data in a file.
    private void saveOrder(String filename){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for(Orders order: orderlist){
                writer.write(order.toString());
                writer.newLine();
            }
            writer.close();
            //That's how our algorith recognises that the file is saved.
            isSaved = true;

            //Inform user that the orders got successfully saved.
            JOptionPane.showMessageDialog(saveMItem, orderlist.size() + " orders saved to " + filename,
                                            "Save Completed", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(saveMItem, "Could not open file: " + filename,
                                            "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Function to create a reader so that we can load data from a file.
    private void loadOrder(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = "";
            String[] token;
            Orders order;

            tableModel.setRowCount(0);
            orderlist.clear();
            filePath.setText(filename);

            while(reader.ready()){
                line = reader.readLine();
                token = line.split(";");

                if(token.length == 8){
                    order = new Orders(token[0], Integer.parseInt(token[1]), token[2], token[3], token[4],
                                        Integer.parseInt(token[5]), Double.parseDouble(token[6]), Double.parseDouble(token[7]));
                    orderlist.add(order);
                    //show orders in line 415
                    showOrders(order);
                    isBlankFile = false;
                    //Code description:
                    //String appID = token[0];
                    //int orderID = Integer.parseInt(token[1]);
                    //String date = token[2];
                    //String clientName = token[3];                  
                    //String itemName = token[4];
                    //int quantity = Integer.parseInt(token[5]);
                    //double netPrice = Double.parseDouble(token[6]);
                    //double taxRate = (Double.parseDouble(token[7]));
                    //order = new Orders(appID, orderID, date, clientName, itemName, quantity, netPrice, taxRate);
                     
                }
            }
            reader.close();
            //That's how algorith recognises that the displayed file is already saved.
            isSaved = true;
           
        } catch (FileNotFoundException ex) {          
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(saveMItem, "Could not open file: " + filename,
                                            "Error", JOptionPane.ERROR_MESSAGE);         
        }      
    }

    //Function to save changes in a file. FileChooser is created here and writer is created in a different method for easier maintenance.
    private void saveToFile(){
        //Here is determined which operation will be used in order to save displayed orders.
        //Save implemntation
        if(saveChoice == 0 || saveChoice == 1){
            //If user attempts to save no orders a message will be displayed.
            if (orderlist.isEmpty()) {
                JOptionPane.showMessageDialog(saveMItem, "Nothing to save", "Saving error",
                    JOptionPane.WARNING_MESSAGE);
            }else{
                //That's how our algorithm recognises if the user is trying to save a previously unsaved order in a file or not.(User can hit "Save" instead of "Save as" when there is currently no active file)
                if(isLoaded==true){
                    //saveOrder in line 271
                    saveOrder(filename);
                }else{
                    final JFileChooser fc = new JFileChooser();
                    int returnval = fc.showSaveDialog(saveMItem);

                    if (returnval == JFileChooser.APPROVE_OPTION){
                        filename = fc.getSelectedFile().getPath();
                        if (filename != null && !filename.isEmpty()) {
                            saveOrder(filename);
                            isSaved = true;
                        }else{
                            JOptionPane.showMessageDialog(saveMItem, "Could not open file", "Saving error",
                            JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }else{
            //Save as implementation
            if(orderlist.isEmpty()){
                JOptionPane.showMessageDialog(saveMItem, "Nothing to save", "Saving error",
                        JOptionPane.WARNING_MESSAGE);
            }else{
                final JFileChooser fc = new JFileChooser();
                int returnval = fc.showSaveDialog(saveMItem);
                if(returnval == JFileChooser.APPROVE_OPTION){
                    filename = fc.getSelectedFile().getPath();
                    if (filename != null && !filename.isEmpty()){
                        saveOrder(filename);
                    }else{
                        JOptionPane.showMessageDialog(saveMItem, "Could not open file", "Saving error",
                        JOptionPane.WARNING_MESSAGE);
                    }
                } 
            }
        }
    }

    //Function to load orders from a file.
    private void loadFromFile(){
        final JFileChooser fc = new JFileChooser();
        int returnval = fc.showOpenDialog(openMItem);
        if(returnval == JFileChooser.APPROVE_OPTION){
            filename = fc.getSelectedFile().getPath();
            if (filename != null && !filename.isEmpty()) {
                //loadOrder in line 294
                loadOrder(filename);
                //That's how our algorith know if a file is loaded.
                isLoaded = true;
            }else{
                JOptionPane.showMessageDialog(saveMItem, "Could not open file", "Saving error",
                JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Function to print data in the table component. We use tableModel's methods to achieve the desired results.
    private void showOrders(Orders order){             
        Object[] data = {order.getOrderID(), order.getDate(), order.getClientName(), 
                order.getItemName(), order.getQuantity(), order.getNetPrice(), order.getTaxRate()*100};
        tableModel.addRow(data);
        //setTableAlignment in line 418
        setTableAlignment();
    }

    //Function to center every string in table cells because they are on the leftmost edge of the cell by default.
    private void setTableAlignment(){
        renderer.setHorizontalAlignment(JLabel.CENTER);

        //We have to align every columf of teh table.
        for(int i=0; i < dataTable.getColumnCount(); i++){
            dataTable.setDefaultRenderer(dataTable.getColumnClass(i), renderer);
        }
        //To display data in the new position.
        dataTable.updateUI();
    }
}
