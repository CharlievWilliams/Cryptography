import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;

public class TextEncryptionApp {

    private JPanel mainPanel;
    private JTextPane message1TextPane;
    private JTextPane message2TextPane;
    private JTextPane encryptedMessageTextPane;
    private JTextArea encryptedMessageTextArea;
    private JTextArea decryptedMessage1TextArea;
    private JTextArea decryptedMessage2TextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton clearButton;

    /**
     * Description
     *
     * @param args description
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Verify Credit Card Number");
        frame.setContentPane(new TextEncryptionApp().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * Description
     */
    public TextEncryptionApp() {
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearButtonClicked();
            }
        });
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                oneTimePadEncryption();
            }
        });
        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reverseSteganography();
            }
        });
    }

    /**
     * Description
     */
    private void clearButtonClicked() {
        message1TextPane.setText("");
        message2TextPane.setText("");
        encryptedMessageTextArea.setText("");
        encryptedMessageTextPane.setText("");
        decryptedMessage1TextArea.setText("");
        decryptedMessage2TextArea.setText("");
    }

    /**
     * Blum Blum Shlub Generator
     */
    private String pseudoRandomGenerator() {
        int primeNumber1 = 7;
        int primeNumber2 = 11;
        int m = primeNumber1 * primeNumber2;
        int count = 3; // CoPrime
        int modularisedCount;
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            count = count * count;
            modularisedCount = count % m;
            if (modularisedCount % 2 == 0) {
                key.append("0");
            } else {
                key.append("1");
            }
        }

        return key.toString();
    }

    /**
     * Description
     */
    private void oneTimePadEncryption() {
        // Create key
        String key = pseudoRandomGenerator();

        // Retrieve text and get bytes
        String inputString = message2TextPane.getText();

        // Convert to Hexadecimal
        String keyHex = toHex(key);
        String secretHex = toHex(inputString);

        // String result = keyHex ^ secretHex; TODO: Fix this

        StringBuilder cipheredText = new StringBuilder();
/*
        // Perform XOR with secret hex and key hex
        for (int i = 0; i < secretHex.length; i++) {
            encrypted[i] = (byte) (secretString[i] ^ key[i]);
        }
        */
        // TODO: Convert to binary
        performSteganography();
    }

    /**
     * Description
     */
    private void performSteganography() {
        // Default messages
        String secretMessage = "1001 0011 1010 1010";
        String normalMessage = "How are you today? I had a very busy day! I travelled 400 miles returning to London. " +
                "It was windy and rainy. The traffic was bad too. I managed to finish my job, ref No 3789. But I am " +
                "really tired. If possible, can we cancel tonight’s meeting?";

        // Format binary
        String formattedSecretMessage = secretMessage.replaceAll("\\s+", "");
        formattedSecretMessage = formattedSecretMessage + "x ";

        int count = 0;
        // Construct new message
        StringBuilder newMessage = new StringBuilder();
        for (int i = 0; i < normalMessage.length(); i++) {

            // TODO: Convert to switch
            if (normalMessage.charAt(i) == ' ') {
                if (formattedSecretMessage.charAt(count) == '0') {
                    newMessage.append(" ");
                    count++;
                } else if (formattedSecretMessage.charAt(count) == '1') {
                    newMessage.append("  ");
                    count++;
                } else if (formattedSecretMessage.charAt(count) == 'x') {
                    newMessage.append("   ");
                    count++;
                } else if (formattedSecretMessage.charAt(count) == ' ') {
                    newMessage.append(" ");
                }
            } else {
                newMessage.append(normalMessage.charAt(i));
            }
        }
        encryptedMessageTextArea.setText(newMessage.toString());
    }

    /**
     * Description
     */
    private void reverseSteganography() {
        // Default messages
        String steganographyMessage = "How  are you today?  I had a  very  busy  day! I  travelled 400  miles " +
                "returning  to London.   It was windy and rainy. The traffic was bad too. I managed to finish my " +
                "job, ref No 3789. But I am really tired. If possible, can we cancel tonight’s meeting?";

        int loop = 0;
        boolean messageFound = false;
        // Construct new message
        StringBuilder secretMessage = new StringBuilder();
        StringBuilder normalMessage = new StringBuilder();

        while (loop < steganographyMessage.length()) {
            if (steganographyMessage.charAt(loop) == ' ' && !messageFound) {
                if (steganographyMessage.charAt(loop + 1) != ' ') {
                    normalMessage.append(" ");
                    secretMessage.append("0");
                    loop++;
                } else if (steganographyMessage.charAt(loop + 1) == ' ' && steganographyMessage.charAt(loop + 2) != ' ') {
                    normalMessage.append(" ");
                    secretMessage.append("1");
                    loop+=2;
                } else if (steganographyMessage.charAt(loop + 1) == ' ' && steganographyMessage.charAt(loop + 2) == ' ') {
                    normalMessage.append(" ");
                    loop+=3;
                    messageFound = true;
                }
            } else {
                normalMessage.append(steganographyMessage.charAt(loop));
                loop++;
            }
        }
        decryptedMessage1TextArea.setText(String.valueOf(secretMessage));
        decryptedMessage2TextArea.setText(String.valueOf(normalMessage));
    }

    /**
     * Description
     */
    private void oneTimePadDecryption() {

    }

    /**
     * Description
     */
    private String toHex(String arg) {
        return String.format("%020x", new BigInteger(1, arg.getBytes()));
    }

}
