import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;

public class BruteForcePasswordCracking {

    private JPanel mainPanel;
    private JButton bruteForcePasswordButton;
    private JButton clearButton;
    private JTextPane passwordInputTextPane;
    private JTextArea passwordResultsTextArea;
    private JTextPane bchInputTextPane;
    private JTextArea bchResultsTextArea;
    private JButton bruteForceBchButton;

    private boolean hashFound;

    /**
     * Description
     *
     * @param args Description
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brute Force Password Cracking");
        frame.setContentPane(new BruteForcePasswordCracking().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Description
     */
    public BruteForcePasswordCracking() {
        bruteForcePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    verifyPasswordHash();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });
        bruteForceBchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    verifyBchHash();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonMouseClicked();
            }
        });
    }

    /**
     * Description
     */
    private void clearButtonMouseClicked() {
        passwordInputTextPane.setText("");
        passwordResultsTextArea.setText("");
        bchInputTextPane.setText("");
        bchResultsTextArea.setText("");
    }

    /**
     * Description
     */
    private void verifyPasswordHash() throws NoSuchAlgorithmException {
        hashFound = false;
        String hashedPassword = passwordInputTextPane.getText();

        char[] characterSet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        if (Sha1Library.isValidSHA1(hashedPassword)) {
            for (int stringLength = 1; stringLength <= 6; stringLength++) {
                printStringRecursively(characterSet, "", characterSet.length, stringLength, hashedPassword);
            }
        } else {
            passwordResultsTextArea.setText("Invalid input");
        }
    }

    /**
     * Description
     */
    private void verifyBchHash() throws NoSuchAlgorithmException {
        hashFound = false;
        String hashedBch = bchInputTextPane.getText();

        if (Sha1Library.isValidSHA1(hashedBch)) {
            char[] characterSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            printBchRecursively(characterSet, "", characterSet.length, 6, hashedBch);
        } else {
            bchResultsTextArea.setText("Invalid input");
        }
    }

    /**
     * Description
     *
     * @param set Description
     * @param prefix Description
     * @param setLength Description
     * @param stringLength Description
     * @param hashedPassword Description
     */
    private void printStringRecursively(char[] set, String prefix, int setLength, int stringLength, String hashedPassword) throws NoSuchAlgorithmException {

        if (hashFound) {
            return;
        }

        if (stringLength == 0) {
            System.out.println("Generated String: " + prefix);
            String hashedString = Sha1Library.SHA1(prefix);
            System.out.println("Hashed String: " + hashedString);

            if (hashedString.equals(hashedPassword)) {
                hashFound = true;
                passwordResultsTextArea.setText("Password found: " + prefix);
            }
            return;
        }

        for (int i = 0; i < setLength; ++i) {
            String newPrefix = prefix + set[i];
            printStringRecursively(set, newPrefix, setLength, stringLength - 1, hashedPassword);
        }
    }

    /**
     * Description
     *
     * @param set Description
     * @param prefix Description
     * @param setLength Description
     * @param stringLength Description
     * @param hashedPassword Description
     */
    private void printBchRecursively(char[] set, String prefix, int setLength, int stringLength, String hashedPassword) throws NoSuchAlgorithmException {

        if (hashFound) {
            return;
        }

        if (stringLength == 0) {
            System.out.println("Generated String: " + prefix);
            String bchCode = CheckingDigitLibrary.createCheckingDigits(prefix);
            System.out.println("Full BCH Code: " + bchCode);
            String hashedString = Sha1Library.SHA1(bchCode);
            System.out.println("Hashed String: " + hashedString);

            if (hashedString.equals(hashedPassword)) {
                hashFound = true;
                bchResultsTextArea.setText("Password found: " + bchCode);
            }
            return;
        }

        for (int i = 0; i < setLength; ++i) {
            String newPrefix = prefix + set[i];
            printBchRecursively(set, newPrefix, setLength, stringLength - 1, hashedPassword);
        }
    }
}
