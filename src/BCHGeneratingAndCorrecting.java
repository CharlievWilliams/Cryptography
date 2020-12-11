import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BCHGeneratingAndCorrecting {

    private JPanel mainPanel;
    private JButton createSyndromesButton;
    private JButton identifyErrorsButton;
    private JButton clearButton;
    private JButton createCheckingDigitsButton;
    private JTextPane tenDigitInputTextPane;
    private JTextPane sixDigitInputTextPane;
    private JTextArea BchGeneratorTextArea;
    private JTextArea BCHDecoderTextArea;
    private JTextArea checkDigitResultsTextArea;
    int d7, d8, d9, d10; // Checking digits
    int s1, s2, s3, s4; // Syndromes
    int p, q, r; // Formula values
    double i, j, b, a;

    /**
     * Description
     *
     * @param args Description
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(new BCHGeneratingAndCorrecting().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Description
     */
    public BCHGeneratingAndCorrecting() {
        createCheckingDigitsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createCheckingDigitsButtonClicked();
            }
        });
        createSyndromesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createSyndromesButtonClicked();
            }
        });
        identifyErrorsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                identifyErrorsButtonClicked();
            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonClicked();
            }
        });
    }

    /**
     * Description
     */
    private void clearButtonClicked() {
        tenDigitInputTextPane.setText("");
        BchGeneratorTextArea.setText("");
        BCHDecoderTextArea.setText("");
        sixDigitInputTextPane.setText("");
        checkDigitResultsTextArea.setText("");
    }

    private void createCheckingDigitsButtonClicked() {

        int[] inputArray = new int[6];
        String input = sixDigitInputTextPane.getText();

        for (int i = 0; i < input.length(); i++) {
            inputArray[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        d7 = calculateDigit7(inputArray);
        d8 = calculateDigit8(inputArray);
        d9 = calculateDigit9(inputArray);
        d10 = calculateDigit10(inputArray);

        if (d7 == 10 || d8 == 10 || d9 == 10 || d10 == 10) {
            checkDigitResultsTextArea.setText("Unusable Number");
        } else {
            checkDigitResultsTextArea.setText(input + d7 + d8 + d9 + d10);
        }
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateDigit7(int[] inputArray) {
        return ((4 * inputArray[0]) +
                (10 * inputArray[1]) +
                (9 * inputArray[2]) +
                (2 * inputArray[3]) +
                inputArray[4] +
                (7 * inputArray[5])) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateDigit8(int[] inputArray) {
        return ((7 * inputArray[0]) +
                (8 * inputArray[1]) +
                (7 * inputArray[2]) +
                inputArray[3] +
                (9 * inputArray[4]) +
                (6 * inputArray[5])) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateDigit9(int[] inputArray) {
        return ((9 * inputArray[0]) +
                inputArray[1] +
                (7 * inputArray[2]) +
                (8 * inputArray[3]) +
                (7 * inputArray[4]) +
                (7 * inputArray[5])) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateDigit10(int[] inputArray) {
        return (inputArray[0] +
                (2 * inputArray[1]) +
                (9 * inputArray[2]) +
                (10 * inputArray[3]) +
                (4 * inputArray[4]) +
                inputArray[5]) % 11;
    }

    /**
     * Description
     */
    private void createSyndromesButtonClicked() {
        int[] inputArray = new int[10];
        String userInput = tenDigitInputTextPane.getText();

        for (int i = 0; i < userInput.length(); i++) {
            inputArray[i] = Integer.parseInt(String.valueOf(userInput.charAt(i)));
        }

        s1 = calculateSyndrome1(inputArray);
        s2 = calculateSyndrome2(inputArray);
        s3 = calculateSyndrome3(inputArray);
        s4 = calculateSyndrome4(inputArray);

        if (s1 == 10 || s2 == 10 || s3 == 10 || s4 == 10) {
            BchGeneratorTextArea.setText("Unusable Number");
        } else {
            BchGeneratorTextArea.setText("" + s1 + s2 + s3 + s4);
        }
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateSyndrome1(int[] inputArray) {
        return (inputArray[0] +
                inputArray[1] +
                inputArray[2] +
                inputArray[3] +
                inputArray[4] +
                inputArray[5] +
                inputArray[6] +
                inputArray[7] +
                inputArray[8] +
                inputArray[9]) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateSyndrome2(int[] inputArray) {
        return (inputArray[0] +
                2 * inputArray[1] +
                3 * inputArray[2] +
                4 * inputArray[3] +
                5 * inputArray[4] +
                6 * inputArray[5] +
                7 * inputArray[6] +
                8 * inputArray[7] +
                9 * inputArray[8] +
                10 * inputArray[9]) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateSyndrome3(int[] inputArray) {
        return (inputArray[0] +
                4 * inputArray[1] +
                9 * inputArray[2] +
                5 * inputArray[3] +
                3 * inputArray[4] +
                3 * inputArray[5] +
                5 * inputArray[6] +
                9 * inputArray[7] +
                4 * inputArray[8] +
                inputArray[9]) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    private int calculateSyndrome4(int[] inputArray) {
        return (inputArray[0] +
                8 * inputArray[1] +
                5 * inputArray[2] +
                9 * inputArray[3] +
                4 * inputArray[4] +
                7 * inputArray[5] +
                2 * inputArray[6] +
                6 * inputArray[7] +
                3 * inputArray[8] +
                10 * inputArray[9]) % 11;
    }

    /**
     * Description TODO: Fix this function
     */
    private void identifyErrorsButtonClicked() {
        if (s1 == 0 && s2 == 0 && s3 == 0 && s4 == 0) {
            BCHDecoderTextArea.setText("No errors");
        } else {
            p = (s2 * s2 - s1 * s3) % 11;
            q = (s1 * s4 - s2 * s3) % 11;
            r = (s3 * s3 - s2 * s4) % 11;
            if (p == 0 && q == 0 && r == 0) {
                a = s1;
                i = (s2 / a) % 11;
                BCHDecoderTextArea.setText("One error");
            } else {
                double squareRoot = Math.sqrt(q * q - 4 * p * r);
                i = (((-q + squareRoot) / 2 * p) % 11);
                j = (((-q - squareRoot) / 2 * p) % 11);
                b = ((i * s1 - s2) / (i - j)) % 11;
                a = s1 - b;
                BCHDecoderTextArea.setText("Two errors"); // TODO: Add more than 2 errors section
            }
        }
    }

}
