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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        checkingDigit1 = CheckingDigitLibrary.calculateDigit7(inputArray);
        checkingDigit2 = CheckingDigitLibrary.calculateDigit8(inputArray);
        checkingDigit3 = CheckingDigitLibrary.calculateDigit9(inputArray);
        checkingDigit4 = CheckingDigitLibrary.calculateDigit10(inputArray);

        if (checkingDigit1 == 10 || checkingDigit2 == 10 || checkingDigit3 == 10 || checkingDigit4 == 10) {
            checkDigitResultsTextArea.setText("Unusable Number");
        } else {
            checkDigitResultsTextArea.setText(input + checkingDigit1 + checkingDigit2 + checkingDigit3 + checkingDigit4);
        }
    }

    /**
     * Description
     */
    private void createSyndromesButtonClicked() {
        String userInput = tenDigitInputTextPane.getText();

        for (int i = 0; i < userInput.length(); i++) {
            inputArray[i] = Integer.parseInt(String.valueOf(userInput.charAt(i)));
        }

        syndrome1 = SyndromeLibrary.calculateSyndrome1(inputArray);
        syndrome2 = SyndromeLibrary.calculateSyndrome2(inputArray);
        syndrome3 = SyndromeLibrary.calculateSyndrome3(inputArray);
        syndrome4 = SyndromeLibrary.calculateSyndrome4(inputArray);

        bchGeneratorTextArea.setText(syndrome1 + " " + syndrome2 + " " + syndrome3 + " " + syndrome4);

    }

    /**
     * Description
     */
    private void identifyErrorsButtonClicked() {
        if (syndrome1 == 0 && syndrome2 == 0 && syndrome3 == 0 && syndrome4 == 0) { // No errors
            bchDecoderResultsTextArea.setText("No errors");
        } else {
            p = ModuloLibrary.mod(syndrome2 * syndrome2 - syndrome1 * syndrome3);
            q = ModuloLibrary.mod(syndrome1 * syndrome4 - syndrome2 * syndrome3);
            r = ModuloLibrary.mod(syndrome3 * syndrome3 - syndrome2 * syndrome4);
            if (p == 0 && q == 0 && r == 0) { // Single Error
                errorMagnitude1 = syndrome1;
                errorPosition1 = (syndrome2 / errorMagnitude1) % 11;
                int[] result = resolveSingleError(errorPosition1, errorMagnitude1);
                bchDecoderResultsTextArea.setText(
                        "One error. Corrected to " + Arrays.toString(result).replaceAll("\\[|\\]|,|\\s", "")
                );
            } else {

                // TODO: Tidy this shit up
                int partOne = ModuloLibrary.minus(0, q);
                int partTwo = ModuloLibrary.squared(q);
                int partThree = ModuloLibrary.mod(4 * p * r);
                int partFour = ModuloLibrary.minus(partTwo, partThree);
                if (ModuloLibrary.squareRoot(partFour) == 0) {
                    bchDecoderResultsTextArea.setText("More than two errors");
                    return;
                }
                int partFive = ModuloLibrary.squareRoot(partFour);
                int partSix = ModuloLibrary.mod(partOne + partFive);
                int partSeven = ModuloLibrary.mod(2 * p);
                errorPosition1 = ModuloLibrary.inverse(partSix, partSeven);

                int altPartSix = ModuloLibrary.minus(partOne, partFive);
                errorPosition2 = ModuloLibrary.inverse(altPartSix, partSeven);

                // Calculate positions and magnitudes
                // TODO: Also tidy this bollocks up
                int orderOne = ModuloLibrary.mod(errorPosition1 * syndrome1);
                int orderTwo = ModuloLibrary.minus(orderOne, syndrome2);
                int orderThree = ModuloLibrary.minus(errorPosition1, errorPosition2);
                errorMagnitude2 = ModuloLibrary.inverse(orderTwo, orderThree);
                errorMagnitude1 = ModuloLibrary.minus(syndrome1, errorMagnitude2);

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
        inputArray[errorPosition - 1] = ModuloLibrary.mod(inputArray[errorPosition - 1] - errorMagnitude);
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
        inputArray[errorPosition1 - 1] = ModuloLibrary.mod(inputArray[errorPosition1 - 1] - errorMagnitude1);
        inputArray[errorPosition2 - 1] = ModuloLibrary.mod(inputArray[errorPosition2 - 1] - errorMagnitude2);
        return inputArray;
    }

}