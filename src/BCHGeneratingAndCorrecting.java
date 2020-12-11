import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class BCHGeneratingAndCorrecting {

    private JPanel mainPanel;
    private JButton createSyndromesButton;
    private JButton identifyErrorsButton;
    private JButton clearButton;
    private JButton createCheckingDigitsButton;
    private JTextPane tenDigitInputTextPane;
    private JTextPane sixDigitInputTextPane;
    private JTextArea bchGeneratorTextArea;
    private JTextArea bchDecoderResultsTextArea;
    private JTextArea checkDigitResultsTextArea;
    int checkingDigit1, checkingDigit2, checkingDigit3, checkingDigit4; // Checking digits
    int syndrome1, syndrome2, syndrome3, syndrome4; // Syndromes
    int p, q, r;
    int errorMagnitude1, errorPosition1, errorPosition2, errorMagnitude2;
    int[] inputArray = new int[10];

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
        bchGeneratorTextArea.setText("");
        bchDecoderResultsTextArea.setText("");
        sixDigitInputTextPane.setText("");
        checkDigitResultsTextArea.setText("");
    }

    private void createCheckingDigitsButtonClicked() {

        int[] inputArray = new int[6];
        String input = sixDigitInputTextPane.getText();

        for (int i = 0; i < input.length(); i++) {
            inputArray[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        checkingDigit1 = calculateDigit7(inputArray);
        checkingDigit2 = calculateDigit8(inputArray);
        checkingDigit3 = calculateDigit9(inputArray);
        checkingDigit4 = calculateDigit10(inputArray);

        if (checkingDigit1 == 10 || checkingDigit2 == 10 || checkingDigit3 == 10 || checkingDigit4 == 10) {
            checkDigitResultsTextArea.setText("Unusable Number");
        } else {
            checkDigitResultsTextArea.setText(input + checkingDigit1 + checkingDigit2 + checkingDigit3 + checkingDigit4);
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
        String userInput = tenDigitInputTextPane.getText();

        for (int i = 0; i < userInput.length(); i++) {
            inputArray[i] = Integer.parseInt(String.valueOf(userInput.charAt(i)));
        }

        syndrome1 = calculateSyndrome1(inputArray);
        syndrome2 = calculateSyndrome2(inputArray);
        syndrome3 = calculateSyndrome3(inputArray);
        syndrome4 = calculateSyndrome4(inputArray);

        if (syndrome1 == 10 || syndrome2 == 10 || syndrome3 == 10 || syndrome4 == 10) {
            bchGeneratorTextArea.setText("Unusable Number");
        } else {
            bchGeneratorTextArea.setText("" + syndrome1 + syndrome2 + syndrome3 + syndrome4);
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
     * Description
     *
     * @param input Description
     * @return Description
     */
    private int mod(int input) {
        int result = input % 11;
        if (result < 0)
            result += 11;
        return result;
    }

    /**
     * Description
     */
    private void identifyErrorsButtonClicked() {
        if (syndrome1 == 0 && syndrome2 == 0 && syndrome3 == 0 && syndrome4 == 0) { // No errors
            bchDecoderResultsTextArea.setText("No errors");
        } else {
            p = mod(syndrome2 * syndrome2 - syndrome1 * syndrome3);
            q = mod(syndrome1 * syndrome4 - syndrome2 * syndrome3);
            r = mod(syndrome3 * syndrome3 - syndrome2 * syndrome4);
            if (p == 0 && q == 0 && r == 0) { // Single Error
                errorMagnitude1 = syndrome1 % 11;
                errorPosition1 = (syndrome2 / errorMagnitude1) % 11;
                int[] result = resolveSingleError(errorPosition1, errorMagnitude1);
                bchDecoderResultsTextArea.setText(
                        "One error. Corrected to " + Arrays.toString(result).replaceAll("\\[|\\]|,|\\s", "")
                );
/*            } else if (Math.sqrt(mod(q * q - 4 * p * r)) == 0) { // More than Double error TODO: This is broken
                bchDecoderResultsTextArea.setText("More than two errors");*/
            } else {
                // TODO: I think this is being calculated incorrectly
                int temp = q * q;
                int temp2 = 4 * p * r;
                int temp3 = temp - temp2;
                int temp4 = mod(temp3);
                double temp5 = Math.sqrt(temp4);
                int temp6 = (int) temp5;

                errorPosition1 = mod((-q + temp6) / 2 * p);
                errorPosition2 = mod((-q - temp6) / 2 * p);
                errorMagnitude1 = mod((errorPosition1 * syndrome1 - syndrome2) / (errorPosition1 - errorPosition2));
                errorMagnitude2 = mod(syndrome1 - errorMagnitude1);
                if (errorPosition1 == 0 || errorPosition2 == 0) { // More than Double error
                    bchDecoderResultsTextArea.setText("More than two errors");
                } else {
                    int[] result = resolveDoubleError(errorPosition1, errorPosition2, errorMagnitude1, errorMagnitude2);
                    boolean isTen = false;

                    for (int digits : result) {
                        if (digits == 10) {
                            isTen = true;
                            break;
                        }
                    }

                    if (!isTen) { // Double error
                        bchDecoderResultsTextArea.setText("Two errors. Corrected to " + Arrays.toString(result).replaceAll("\\[|\\]|,|\\s", ""));
                    } else { // More than double error
                        bchDecoderResultsTextArea.setText("More than two errors");
                    }
                }
            }
        }
    }

    /**
     * Description
     *
     * @param errorPosition  Description
     * @param errorMagnitude Description
     * @return Description
     */
    private int[] resolveSingleError(int errorPosition, int errorMagnitude) {
        inputArray[errorPosition - 1] = inputArray[errorPosition - 1] - errorMagnitude; // TODO: What if it's +

        return inputArray;
    }

    /**
     * Description
     *
     * @param errorPosition1  Description
     * @param errorPosition2  Description
     * @param errorMagnitude1 Description
     * @param errorMagnitude2 Description
     * @return Description
     */
    private int[] resolveDoubleError(int errorPosition1, int errorPosition2, int errorMagnitude1, int errorMagnitude2) {
        inputArray[errorPosition1 - 1] = inputArray[errorPosition1 - 1] - errorMagnitude1;
        inputArray[errorPosition2 - 1] = inputArray[errorPosition2 - 1] - errorMagnitude2;
        return inputArray;
    }
}
