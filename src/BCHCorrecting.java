import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BCHCorrecting {
    private JPanel mainPanel;
    private JButton verifyButton;
    private JButton clearButton;
    private JTextPane textPane1;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton identifyErrorsButton;
    int s1, s2, s3, s4, p, q, r;
    double i, j, b, a;

    public BCHCorrecting() {
        verifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { verifySyndromeButtonClicked();
            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonMouseClicked();
            }
        });
        identifyErrorsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                identifyErrors();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(new BCHCorrecting().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void clearButtonMouseClicked() {
        textPane1.setText("");
        textArea1.setText("");
        textArea2.setText("");
    }

    private void verifySyndromeButtonClicked() {

        int[] startArray = new int[10];
        String syndromeInput;
        syndromeInput = textPane1.getText();

        for (int i = 0; i < syndromeInput.length(); i++) {
            startArray[i] = Integer.parseInt(String.valueOf(syndromeInput.charAt(i)));
        }

        s1 = calculateS1(startArray);
        s2 = calculateS2(startArray);
        s3 = calculateS3(startArray);
        s4 = calculateS4(startArray);

        if (s1 == 10 || s2 == 10 || s3 == 10 || s4 == 10) {
            textArea1.setText("Unusable Number");
        } else {
            textArea1.setText("" + s1 + s2 + s3 + s4);
        }
    }

    private int calculateS1(int[] startArray) {
        return (startArray[0] + startArray[1] + startArray[2] + startArray[3] + startArray[4] + startArray[5] + startArray[6] + startArray[7] + startArray[8] + startArray[9]) % 11;
    }

    private int calculateS2(int[] startArray) {
        return (startArray[0] + 2 * startArray[1] + 3 * startArray[2] + 4 * startArray[3] + 5 * startArray[4] + 6 * startArray[5] + 7 * startArray[6] + 8 * startArray[7] + 9 * startArray[8] + 10 * startArray[9]) % 11;
    }

    private int calculateS3(int[] startArray) {
        return (startArray[0] + 4 * startArray[1] + 9 * startArray[2] + 5 * startArray[3] + 3 * startArray[4] + 3 * startArray[5] + 5 * startArray[6] + 9 * startArray[7] + 4 * startArray[8] + startArray[9]) % 11;
    }

    private int calculateS4(int[] startArray) {
        return (startArray[0] + 8 * startArray[1] + 5 * startArray[2] + 9 * startArray[3] + 4 * startArray[4] + 7 * startArray[5] + 2 * startArray[6] + 6 * startArray[7] + 3 * startArray[8] + 10 * startArray[9]) % 11;
    }

    private void identifyErrors() {
        if (s1 == 0 && s2 == 0 && s3 == 0 && s4 == 0) {
            textArea2.setText("No errors");
        } else {
            p = (s2 * s2 - s1 * s3) % 11;
            q = (s1 * s4 - s2 * s3) % 11;
            r = (s3 * s3 - s2 * s4) % 11;
            if (p == 0 && q == 0 && r == 0) {
                a = s1;
                i = (s2 / a) % 11;
                textArea2.setText("One error");
            } else {
                double squareRoot = Math.sqrt(q * q - 4 * p * r);
                i = (((-q + squareRoot) / 2 * p) % 11);
                j = (((-q - squareRoot) / 2 * p) % 11);
                b = ((i * s1 - s2) / (i - j)) % 11;
                a = s1 - b;
                textArea2.setText("Two errors");
            }
        }
    }

}
