/**
 *
 * @author Panagiotis Gkiokas 711171060
 * 
 * Main file initializing the central window called frame and its class is called Mainframe
 * (Go to Mainframe.java for further analysis)
 */

package ordermanager;

import java.util.ArrayList;

import javax.swing.JFrame;


public class Ordermanager{

    public static void main(String[] args) {
        Mainframe frame = new Mainframe();
        //Close operation set to do nothing because we want to control what will happen when the user presses the X button.("Cancel" button would not work otherwise)
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(950, 500);
        //We pass this frame so we can use it later to return data on it.
        frame.prepareUI(frame);
        frame.setTitle("Order Manager (711171060)");
        frame.setVisible(true);
    }
    
}
