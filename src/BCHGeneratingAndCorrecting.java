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
    int[] inputArray = new int[10];

    /**
     * Main function for the software. Create the JFrame and assign its content.
     *
     * @param args Unused console args.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("BCH Generating and Correcting");
        frame.setContentPane(new BCHGeneratingAndCorrecting().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Create mouse click listeners for the GUI.
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
     * Clear all text panes on the GUI.
     */
    private void clearButtonClicked() {
        tenDigitInputTextPane.setText("");
        bchGeneratorTextArea.setText("");
        bchDecoderResultsTextArea.setText("");
        sixDigitInputTextPane.setText("");
        checkDigitResultsTextArea.setText("");
    }

    /**
     * Create the four checking digits for a BCH(10, 6) code.
     */
    private void createCheckingDigitsButtonClicked() {

        String input = sixDigitInputTextPane.getText();
        String bchCode = CheckingDigitLibrary.createCheckingDigits(input);

        if (bchCode.equals("Unusable number")) {
            checkDigitResultsTextArea.setText("Unusable Number");
        } else if (bchCode.equals("Invalid input")) {
            checkDigitResultsTextArea.setText("Invalid input");
        } else {
            int[] inputArray = new int[10];
            for (int i = 6; i < bchCode.length(); i++) {
                inputArray[i] = Integer.parseInt(String.valueOf(bchCode.charAt(i)));
            }

            checkingDigit1 = inputArray[6];
            checkingDigit2 = inputArray[7];
            checkingDigit3 = inputArray[8];
            checkingDigit4 = inputArray[9];

            checkDigitResultsTextArea.setText(input + checkingDigit1 + checkingDigit2 + checkingDigit3 + checkingDigit4);
        }
    }

    /**
     * Calculate the four syndromes from a 10 digit BCH(10, 6) code.
     */
    private void createSyndromesButtonClicked() {
        String userInput = tenDigitInputTextPane.getText();

        try {
            for (int i = 0; i < 10; i++) {
                inputArray[i] = Integer.parseInt(String.valueOf(userInput.charAt(i)));
            }
            syndrome1 = SyndromeLibrary.calculateSyndrome1(inputArray);
            syndrome2 = SyndromeLibrary.calculateSyndrome2(inputArray);
            syndrome3 = SyndromeLibrary.calculateSyndrome3(inputArray);
            syndrome4 = SyndromeLibrary.calculateSyndrome4(inputArray);

            bchGeneratorTextArea.setText(syndrome1 + " " + syndrome2 + " " + syndrome3 + " " + syndrome4);
        } catch (Exception e) {
            e.printStackTrace();
            bchGeneratorTextArea.setText("Invalid input");
        }
    }

    /**
     * Using the syndromes, calculate the number of errors in the code, and if there are less than two, calculate the
     * error position and magnitudes in order to resolve them.
     */
    private void identifyErrorsButtonClicked() {
        int errorMagnitude1, errorPosition1, errorPosition2, errorMagnitude2;

        if (syndrome1 == 0 && syndrome2 == 0 && syndrome3 == 0 && syndrome4 == 0) { // No errors
            bchDecoderResultsTextArea.setText("No errors");
        } else {
            int p = ModuloLibrary.mod(syndrome2 * syndrome2 - syndrome1 * syndrome3);
            int q = ModuloLibrary.mod(syndrome1 * syndrome4 - syndrome2 * syndrome3);
            int r = ModuloLibrary.mod(syndrome3 * syndrome3 - syndrome2 * syndrome4);
            if (p == 0 && q == 0 && r == 0) { // Single Error
                singleErrorScenario();
            } else {
                /*
                 * Calculate error positions - Formulae for error positions under mod 11
                 * errorPosition1 = (-Q + sqrt(Q * Q - 4 * P * R)) / 2 * P
                 * errorPosition1 = (-Q - sqrt(Q * Q - 4 * P * R)) / 2 * P
                 * */
                int innerBrackets = ModuloLibrary.minus(ModuloLibrary.squared(q), ModuloLibrary.mod(4 * p * r));
                // Check for scenario with more than two errors
                if (ModuloLibrary.squareRoot(innerBrackets) == 0) {
                    bchDecoderResultsTextArea.setText("More than two errors");
                    return;
                }
                errorPosition1 = ModuloLibrary.inverse(ModuloLibrary.mod(ModuloLibrary.minus(0, q) +
                        ModuloLibrary.squareRoot(innerBrackets)), ModuloLibrary.mod(2 * p));
                errorPosition2 = ModuloLibrary.inverse(ModuloLibrary.minus(ModuloLibrary.minus(0, q),
                        ModuloLibrary.squareRoot(innerBrackets)), ModuloLibrary.mod(2 * p));

                /*
                 * Calculate error magnitudes - Formulae for error magnitudes under mod 11
                 * errorMagnitude2 = (errorPosition1 * syndrome1 - syndrome2) / (errorPosition1 - errorPosition2)
                 * errorMagnitude1 = syndrome1 - errorMagnitude2
                 * */
                errorMagnitude2 = ModuloLibrary.inverse(ModuloLibrary.minus(ModuloLibrary.mod(
                        errorPosition1 * syndrome1), syndrome2), ModuloLibrary.minus(errorPosition1, errorPosition2));
                errorMagnitude1 = ModuloLibrary.minus(syndrome1, errorMagnitude2);

                // Check for scenario with more than two errors
                if (errorPosition1 == 0 || errorPosition2 == 0) {
                    bchDecoderResultsTextArea.setText("More than two errors");
                } else {

                    // Check for scenario with more than two errors
                    int[] result = resolveDoubleError(errorPosition1, errorPosition2, errorMagnitude1, errorMagnitude2);
                    boolean isTen = false;
                    for (int digits : result) {
                        if (digits == 10) {
                            isTen = true;
                            break;
                        }
                    }

                    if (!isTen) { // Check for scenario with double errors
                        bchDecoderResultsTextArea.setText("Two errors. Corrected to " + Arrays.toString(result)
                                .replaceAll("\\[|]|,|\\s", ""));
                    } else { // More than two errors
                        bchDecoderResultsTextArea.setText("More than two errors");
                    }
                }
            }
        }
    }

    /**
     * Calculate the error position and magnitude when solving a single error.
     */
    private void singleErrorScenario() {
        int errorMagnitude1 = syndrome1;
        int errorPosition1 = (syndrome2 / errorMagnitude1) % 11;
        try {
            int[] result = resolveSingleError(errorPosition1, errorMagnitude1);
            bchDecoderResultsTextArea.setText(
                    "One error. Corrected to " + Arrays.toString(result).replaceAll("\\[|]|,|\\s", "")
            );
        } catch (Exception e) {
            bchDecoderResultsTextArea.setText("More than two errors");
        }
    }

    /**
     * Resolve a single error in a BCH(10,6) code.
     *
     * @param errorPosition  The position of the error in the BCH (10,6) code.
     * @param errorMagnitude The magnitude of the error at the position.
     * @return the corrected code.
     */
    private int[] resolveSingleError(int errorPosition, int errorMagnitude) {
        inputArray[errorPosition - 1] = ModuloLibrary.mod(inputArray[errorPosition - 1] - errorMagnitude);
        return inputArray;
    }

    /**
     * Resolve a double error in a BCH(10,6) code.
     *
     * @param errorPosition1  The position of the first error in the BCH (10,6) code.
     * @param errorPosition2  The position of the second error in the BCH (10,6) code.
     * @param errorMagnitude1 The magnitude of the first error at the first position.
     * @param errorMagnitude2 The magnitude of the second error at the second position.
     * @return the corrected code.
     */
    private int[] resolveDoubleError(int errorPosition1, int errorPosition2, int errorMagnitude1, int errorMagnitude2) {
        inputArray[errorPosition1 - 1] = ModuloLibrary.mod(inputArray[errorPosition1 - 1] - errorMagnitude1);
        inputArray[errorPosition2 - 1] = ModuloLibrary.mod(inputArray[errorPosition2 - 1] - errorMagnitude2);
        return inputArray;
    }

}