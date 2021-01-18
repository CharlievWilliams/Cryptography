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
     * Main function for the software. Create the JFrame and assign its content.
     *
     * @param args Unused console args.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brute Force Password Cracking");
        frame.setContentPane(new BruteForcePasswordCracking().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Create mouse click listeners for the GUI.
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
     * Clear all text panes on the GUI.
     */
    private void clearButtonMouseClicked() {
        passwordInputTextPane.setText("");
        passwordResultsTextArea.setText("");
        bchInputTextPane.setText("");
        bchResultsTextArea.setText("");
    }

    /**
     * Verify that the string in the text pane is a valid Sha1 hash, and print all possible recursive strings within a
     * character set between 1 and 6 characters long.
     */
    private void verifyPasswordHash() throws NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();

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
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);  // divide by 1000000 to get milliseconds.
        System.out.println(duration);
    }

    /**
     * Verify that the string in the text pane is a valid Sha1 hash, and print all possible recursive strings within a
     * character set between 1 and 6 characters long.
     */
    private void verifyBchHash() throws NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();
        hashFound = false;
        String hashedBch = bchInputTextPane.getText();

        char[] characterSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        if (Sha1Library.isValidSHA1(hashedBch)) {
            printBchRecursively(characterSet, "", characterSet.length, 6, hashedBch);
        } else {
            bchResultsTextArea.setText("Invalid input");
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);  // divide by 1000000 to get milliseconds.
        System.out.println(duration);
    }

    /**
     * Recursively print the generated String and its hashed counterpart and compare it to the hash provided by the
     * user.
     *
     * @param set The character set used for the string generation.
     * @param prefix The String used for the creation of the generated String.
     * @param setLength The length of the character set.
     * @param stringLength The length of the generated strings.
     * @param hashedPassword The hash that the generated hashes will be compared against.
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
     * Recursively print the generated String and its hashed counterpart and compare it to the hash provided by the
     * user. The code will generate 6 digit length BCH codes, and then create the remaining 4 checking digits. The full
     * 10 digit code will be hashed and compared against the input hash.
     *
     * @param set The character set used for the string generation.
     * @param prefix The String used for the creation of the generated String.
     * @param setLength The length of the character set.
     * @param stringLength The length of the generated strings.
     * @param hashedPassword The hash that the generated hashes will be compared against.
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
