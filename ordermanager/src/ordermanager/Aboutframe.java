/*
*This is the file that implements the operations happening in "About" window.
*/
package ordermanager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class Aboutframe extends JFrame{
    private JLabel nameLbl, amLbl, timeLbl, imageLbl;
    private JButton closeBtn;
    private JPanel topPanel, midPanel1, midPanel2, btnPanel;
    private JPanel namePanel, amPanel, timePanel;
    private ImageIcon icon;
    private Image image;


    public Aboutframe(){
        super();
        
        icon = new ImageIcon(getClass().getResource("fasianaptiksis.png"));
        
        nameLbl = new JLabel("Name: Panagiotis Gkiokas");
        amLbl = new JLabel("A.M: 711171060");
        timeLbl = new JLabel("Development time: 4 days");
        imageLbl = new JLabel();

        closeBtn = new JButton("Close");

        topPanel = new JPanel();
        midPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        midPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    }

    //Here we prepare our window. Every action performed in the window is being handled here.
    public void prepareUI(){
        this.add(topPanel, BorderLayout.NORTH);
        this.add(midPanel1, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.SOUTH);
        //Method to get the image displayed and downscale it in order to fit the window.
        image = icon.getImage().getScaledInstance(480, 270, Image.SCALE_SMOOTH);
        imageLbl.setIcon(new ImageIcon(image));
        
        midPanel1.add(midPanel2);
        midPanel2.add(namePanel);
        namePanel.add(nameLbl);
        midPanel2.add(amPanel);
        amPanel.add(amLbl);
        midPanel2.add(timePanel);
        timePanel.add(timeLbl);
        midPanel1.add(imageLbl);
        btnPanel.add(closeBtn);

        //Action when user presses the "Cancel" button.
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    
}