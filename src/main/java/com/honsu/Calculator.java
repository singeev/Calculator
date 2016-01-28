package com.honsu;

import java.awt.*;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

public class Calculator {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

    //---- CREATE AND SETUP LOOK-AND-FEEL OF THE MAIN WINDOW ------//

                Frame frame = new Frame();
                setDefaultLookAndFeelDecorated(true);
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
                frame.setSize(260, 225);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}



