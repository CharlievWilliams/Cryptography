import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VerifyCreditCard {

    private JPanel mainPanel;
    private JTextPane creditCardInputTextPane;
    private JTextArea resultsTextArea;
    private JButton verifyCreditCardButton;
    private JButton clearButton;

    /**
     * Main function for the software. Create the JFrame and assign its content.
     *
     * @param args Unused console args.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Verify Credit Card Number");
        frame.setContentPane(new VerifyCreditCard().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    /**
     * Create mouse click listeners for the GUI.
     */
    public VerifyCreditCard() {
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonClicked();
            }
        });
        verifyCreditCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verifyCreditCardButtonClicked();
            }
        });
    }

    /**
     * Clear all text panes on the GUI.
     */
    private void clearButtonClicked() {
        creditCardInputTextPane.setText("");
        resultsTextArea.setText("");
    }

    /**
     * Add all user input to an int array and perform a Luhn check on the credit card digits to check for a valid
     * credit card number.
     */
    private void verifyCreditCardButtonClicked() {
        int[] creditCardArray = new int[16];
        String creditCardInput = creditCardInputTextPane.getText();
        String formattedCreditCardInput = creditCardInput.replaceAll(" ", "");

        try {
            // Convert String to Array
            for (int i = 0; i < 16; i++) {
                creditCardArray[i] = Integer.parseInt(String.valueOf(formattedCreditCardInput.charAt(i)));
            }

            int calculation = 0;
            int tempNum;

            // Perform Luhn Check
            for (int i = 1; i <= creditCardArray.length; i++) {
                if (i % 2 == 1) {
                    tempNum = creditCardArray[i - 1] * 2;
                    if (tempNum >= 10) {
                        tempNum = tempNum - 9;
                    }
                    calculation = calculation + tempNum;
                } else {
                    calculation = calculation + creditCardArray[i - 1];
                }
            }

            // Display Results
            if (calculation % 10 == 0) {
                resultsTextArea.setText("Credit card is valid");
            } else {
                resultsTextArea.setText("Credit card is invalid");
            }
            // Catch invalid inputs
        } catch (Exception e) {
            e.printStackTrace();
            resultsTextArea.setText("Invalid input");
        }
    }
}
