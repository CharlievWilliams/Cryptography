import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

        for (int stringLength = 1; stringLength <= 6; stringLength++) {
            printStringRecursively(characterSet, "", characterSet.length, stringLength, hashedPassword);
        }
    }

    /**
     * Description
     */
    private void verifyBchHash() throws NoSuchAlgorithmException {
        hashFound = false;
        String hashedBch = bchInputTextPane.getText();

        char[] characterSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        printBchRecursively(characterSet, "", characterSet.length, 6, hashedBch);
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
            String hashedString = SHA1(prefix);
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
            String bchCode = createCheckingDigits(prefix);
            System.out.println("Full BCH Code: " + bchCode);
            String hashedString = SHA1(bchCode);
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

    private String createCheckingDigits(String input) {

        int[] inputArray = new int[6];
        for (int i = 0; i < input.length(); i++) {
            inputArray[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        int checkingDigit1 = CheckingDigitLibrary.calculateDigit7(inputArray);
        int checkingDigit2 = CheckingDigitLibrary.calculateDigit8(inputArray);
        int checkingDigit3 = CheckingDigitLibrary.calculateDigit9(inputArray);
        int checkingDigit4 = CheckingDigitLibrary.calculateDigit10(inputArray);

        if (checkingDigit1 == 10 || checkingDigit2 == 10 || checkingDigit3 == 10 || checkingDigit4 == 10) {
            return "unusable number";
        } else {
            return input + checkingDigit1 + checkingDigit2 + checkingDigit3 + checkingDigit4;
        }
    }

    /**
     * Description
     *
     * @param text Description
     * @return Description
     */
    public static String SHA1(String text) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash;
        md.update(text.getBytes(StandardCharsets.ISO_8859_1), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    /**
     * Description
     *
     * @param data Description
     * @return Description
     */
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte datum : data) {
            int halfByte = (datum >>> 4) & 0x0F;
            int twoHalves = 0;
            do {
                if (halfByte <= 9)
                    buf.append((char) ('0' + halfByte));
                else
                    buf.append((char) ('a' + (halfByte - 10)));
                halfByte = datum & 0x0F;
            } while (twoHalves++ < 1);
        }
        return buf.toString();
    }
}
