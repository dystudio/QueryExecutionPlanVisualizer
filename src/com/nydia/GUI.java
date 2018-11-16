package com.nydia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class GUI {

    private JFrame frame;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        Button Submit = new Button("Submit");
        Submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String userInput = textField.getText();
                System.out.println(userInput);
                Visualization.initQEP(userInput);

            }
        });
        Submit.setBounds(54, 229, 70, 22);
        frame.getContentPane().add(Submit);

        Button Clear = new Button("Clear");
        Clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });
        Clear.setBounds(167, 229, 70, 22);
        frame.getContentPane().add(Clear);

        textField = new JTextField();
        textField.setBounds(34, 42, 360, 178);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Enter SQL Query: ");
        lblNewLabel.setBounds(34, 17, 142, 14);
        frame.getContentPane().add(lblNewLabel);
    }
}
