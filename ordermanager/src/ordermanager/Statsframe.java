/*
*This is the file that implements the operations happening in "Statistics" window.
*/
package ordermanager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Statsframe extends JFrame{
    private JLabel orderammountLbl, totalcostLbl, totalnetcostLbl, cheapestLbl, expensiveLbl;
    private JTextField orderammountTxf, totalcostTxf, totalnetcostTxf, cheapestTxf, expensiveTxf;
    private JButton closeBtn;
    private JPanel amountPanel, amountPanel11, amountPanel12, midPanel, midPanel11, midPanel12, midPanel21, midPanel22, btnPanel;
    private double totalcost = 0, totalnetcost = 0;
    private int cheapestID, expensiveID, cheapestIndex, expensiveIndex;

    public Statsframe(){
        super();

        orderammountLbl = new JLabel("Number of orders:\t");
        totalcostLbl = new JLabel("Total cost:\t");
        totalnetcostLbl = new JLabel("Total net cost:\t");
        cheapestLbl = new JLabel("Cheapest order ID:\t");
        expensiveLbl = new JLabel("Most expensive order ID:\t");

        orderammountTxf = new JTextField(10);
        totalcostTxf = new JTextField(10);
        totalnetcostTxf = new JTextField(10);
        cheapestTxf = new JTextField(10);
        expensiveTxf = new JTextField(10);

        closeBtn = new JButton("Close");

        amountPanel = new JPanel(new GridLayout(1, 2));
        amountPanel11 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        amountPanel12 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        midPanel = new JPanel(new GridLayout(2, 2));
        midPanel11 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        midPanel12 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        midPanel21 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        midPanel22 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    }

    //Here we prepare our window. Every action performed in the window is being handled here. 
    //We pass the orderlist from main window in order to perform actions with its data.
    public void prepareUI(ArrayList<Orders> orderlist){
        this.add(amountPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.SOUTH);

        orderammountTxf.setEditable(false);
        expensiveTxf.setEditable(false);
        cheapestTxf.setEditable(false);
        totalcostTxf.setEditable(false);
        totalnetcostTxf.setEditable(false);

        amountPanel.add(amountPanel11);
        amountPanel.add(amountPanel12);
        amountPanel11.add(orderammountLbl);
        amountPanel11.add(orderammountTxf);

        midPanel.add(midPanel11);
        midPanel.add(midPanel12);
        midPanel.add(midPanel21);
        midPanel.add(midPanel22);
        midPanel11.add(expensiveLbl);
        midPanel11.add(expensiveTxf);
        midPanel12.add(totalcostLbl);
        midPanel12.add(totalcostTxf);
        midPanel21.add(cheapestLbl);
        midPanel21.add(cheapestTxf);
        midPanel22.add(totalnetcostLbl);
        midPanel22.add(totalnetcostTxf);

        btnPanel.add(closeBtn);

        String size = String.valueOf(orderlist.size());
        orderammountTxf.setText(size);
        //setStats in line 100
        setStats(orderlist);
        
        //Action when user presses the "Cancel" button
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }); 
    }

    //Function set the statistics in in the window.
    private void setStats(ArrayList<Orders> orderlist){
        int i = 0;
        for(Orders order: orderlist){
            totalcost = totalcost + order.getTotalCost(order.getNetPrice(), order.getTaxRate());
            totalnetcost = totalnetcost + order.getNetPrice();
            
            //Measures taken so we do not get out of bounds.
            if(i==0){
                cheapestIndex = i;
                cheapestID = order.getOrderID();
                expensiveIndex = i;
                expensiveID = order.getOrderID();
            }else{
                if(order.getNetPrice() < orderlist.get(cheapestIndex).getNetPrice()){
                    cheapestIndex = i;
                    cheapestID = order.getOrderID();
                }else if(order.getNetPrice() > orderlist.get(expensiveIndex).getNetPrice()){
                    expensiveIndex = i;
                    expensiveID = order.getOrderID();
                }else{
                }
            }
            i++;    
        }
        totalcostTxf.setText(String.valueOf(totalcost));
        totalnetcostTxf.setText(String.valueOf(totalnetcost));
        cheapestTxf.setText(String.valueOf(cheapestID));
        expensiveTxf.setText(String.valueOf(expensiveID));
        
    }
}