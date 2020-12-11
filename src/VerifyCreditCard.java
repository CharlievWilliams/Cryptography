import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VerifyCreditCard {

    private JPanel mainPanel;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JTextArea textArea1;
    private JButton verifyISBNButton;
    private JButton verifyCreditCardButton;
    private JButton clearButton;

    public VerifyCreditCard() {
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonMouseClicked();
            }
        });
        verifyISBNButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                verifyISBNButtonClicked();
            }
        });
        verifyCreditCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVerifyCreditCardButtonClicked();
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("ISBN and Credit Card Checker");
        frame.setContentPane(new VerifyCreditCard().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void clearButtonMouseClicked() {
        textPane1.setText("");
        textPane2.setText("");
        textArea1.setText("");
    }

    private void verifyISBNButtonClicked() {
        int[] startArray = new int[10];
        String isbnInput;
        isbnInput = textPane1.getText();
        String formattedIsbnInput = isbnInput.replaceAll("-", "");

        try {
            for (int i = 0; i < formattedIsbnInput.length(); i++) {
                if (formattedIsbnInput.charAt(i) == 'x' || formattedIsbnInput.charAt(i) == 'X') {
                    startArray[i] = 10;
                } else {
                    startArray[i] = Integer.parseInt(String.valueOf(formattedIsbnInput.charAt(i)));
                }
            }
            int calculation = 0;

            // ISBN Check
            for (int i = 1; i < startArray.length + 1; i++) {
                calculation = calculation + (i * startArray[i - 1]);
            }

            if (calculation % 11 == 0) {
                textArea1.setText("ISBN is valid");
            } else {
                textArea1.setText("ISBN is invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea1.setText("Invalid input");
        }
    }

    private void setVerifyCreditCardButtonClicked() {
        int[] startArray = new int[16];
        String creditCardInput;
        creditCardInput = textPane2.getText();
        String formattedCreditCardInput = creditCardInput.replaceAll(" ", "");

        try {
            for (int i = 0; i < formattedCreditCardInput.length(); i++) {
                startArray[i] = Integer.parseInt(String.valueOf(formattedCreditCardInput.charAt(i)));
            }

            int calculation = 0;
            int tempNum;

            // Luhn Check
            for (int i = 1; i < startArray.length + 1; i++) {
                if (i % 2 == 1) {
                    tempNum = startArray[i - 1] * 2;
                    if (tempNum >= 10) {
                        tempNum = tempNum - 9;
                    }
                    calculation = calculation + tempNum;
                } else {
                    calculation = calculation + startArray[i - 1];
                }
            }

            if (calculation % 10 == 0) {
                textArea1.setText("Credit card is valid");
            } else {
                textArea1.setText("Credit card is invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea1.setText("Invalid input");
        }
    }
}
