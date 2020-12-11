import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class BruteForcePasswordCracking {

    private JPanel mainPanel;
    private JButton bruteForcePasswordButton;
    private JButton clearButton;
    private JTextPane passwordInputTextPane;
    private JTextArea passwordResultsTextArea;

    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 6;

    /**
     * Description
     *
     * @param args Description
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(new BruteForcePasswordCracking().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
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
                    verifyHash();
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
    }

    /**
     * Description
     */
    private void verifyHash() throws NoSuchAlgorithmException {
        String hashedPassword = passwordInputTextPane.getText();
        boolean hasFoundHash = false;
        while (!hasFoundHash) {
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            if (SHA1(generatedString).equals(hashedPassword)) {
                hasFoundHash = true;
                passwordResultsTextArea.setText("Password found: " + generatedString);
            }
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
