package com.honsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Frame extends JFrame {
    private final JTextField display;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;
    private boolean isError = false;
    private boolean isSignChanged = false;

    //------ TWO TYPES OF BUTTON'S REACTION ON MOUSE CLICK ---------//

    private final ActionListener insert = new InsertAction();
    private final ActionListener command = new CommandAction();

    //------ CONSTRUCTOR FOR THE MAIN WINDOW -----------------------//

    public Frame() {

        super("Calculator");
        final JPanel mainPanel = new JPanel(new GridBagLayout());

        //------ LOOK-AND-FEEL FOR THE CALCULATOR'S DISPLAY ------------//

        display = new JTextField("0");
        Font font = display.getFont();
        font = font.deriveFont(font.getSize() * 1.8f);
        display.setFont(font);
        display.setHorizontalAlignment(SwingConstants.TRAILING);
        display.setEnabled(false);
        display.setDisabledTextColor(Color.GRAY);

        //--- ADD DISPLAY AND BUTTONS IN THE MAIN PANEL OF THE FRAME ---//

        mainPanel.add(display, new GridBagConstraints(0, 0, 5, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("7", insert), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("8", insert), new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("9", insert), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("÷", command), new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("C", command), new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("4", insert), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("5", insert), new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("6", insert), new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("*", command), new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("±", command), new GridBagConstraints(4, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("1", insert), new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("2", insert), new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("3", insert), new GridBagConstraints(2, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("-", command), new GridBagConstraints(3, 3, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("=", command), new GridBagConstraints(4, 3, 1, 2, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("0", insert), new GridBagConstraints(0, 4, 2, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton(".", insert), new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
        mainPanel.add(addButton("+", command), new GridBagConstraints(3, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));

        //-------------------- FINAL WINDOW'S SETUP --------------------//

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //----------- METHOD TO SETUP AND RETURN NEW BUTTON ------------//

    private JButton addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        Font font = button.getFont();
        font = font.deriveFont(font.getSize() * 1.5f);
        button.setFont(font);
        button.addActionListener(listener);
        button.setFocusPainted(false);
        return button;
    }

    //-------------- ACTIONS FOR "INSERT DIGIT" BUTTONS ------------//

    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            isError = false;
            String input = event.getActionCommand();

            if(start) {
                display.setText("");
                start = false;
            }

            if(display.getText().equals("") && input.charAt(0) == '.'){
                display.setText("0");
            }

            if(display.getText().length() < 14){        // DIGITS LIMIT FOR THE DISPLAY
                display.setText(display.getText() + input);
            }
        }
    }

    //------ ACTIONS FOR "CALCULATING" AND OTHER BUTTONS ------------//

    private class CommandAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();

            if(command.charAt(0) == 'C'){
                isError = false;
                startOver();
                return;
            } else if(isError){
                return;
            }

            // HERE WE TAKE DATA FROM DISPLAY TO PROCESS IT
            Double tempResult = Double.parseDouble(display.getText().replace(',', '.'));

            if((command.charAt(0) == '±') && (display.getText().length() < 16 || isSignChanged || display.getText().startsWith("-")) && !display.getText().equals("0")) {
                tempResult *= -1;
                isSignChanged = true;
                showResult(tempResult);
            } else {
                calculate(tempResult, lastCommand);  // HERE WE CALL CALCULATING ITSELF
                lastCommand = command;
                isSignChanged = false;
                start = true;
            }
        }
    }

    //--------- CALCULATE METHOD, HERE WE DO ALL THE MATH --------------//

    private void calculate(double x, String lastCommand) {

        switch (lastCommand.charAt(0)){
            case '+':
                result += x;
                break;
            case '-':
                result -= x;
                break;
            case '*':
                result *= x;
                break;
            case '÷':
                result /= x;
                break;
            case '=':
                result = x;
                break;
        }
        showResult(result);
    }

    //---- SETTING UP THE RESULT'S LOOK AND SHOWING IT ON THE DISPLAY -----//

    private void showResult(Double result) {

        if(Double.isInfinite(result)){   // THIS IS FOR THE "DIVIDING BY ZERO" CASE
            isError = true;
            display.setText("∞");
            return;
        }

        DecimalFormat format = new DecimalFormat("0.#############");
        String resultString = (format.format(result)).replace(',','.');
        if (resultString.length() <= 16) {
            display.setText(resultString);
        } else {
            display.setText(resultString.substring(0, 15) + "...");
            isError = true;
        }
    }

    //----------- THIS IS A METHOD TO RESET CALCULATING PROCESS -----------//

    private void startOver(){
        result = 0;
        display.setText("0");
        lastCommand = "=";
        start = true;
    }
}
