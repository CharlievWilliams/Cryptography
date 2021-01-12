import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;

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
        encryptedMessageTextArea.setLineWrap(true);
        decryptedMessage2TextArea.setLineWrap(true);
        decryptedMessage1TextArea.setLineWrap(true);
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
    private byte[] pseudoRandomGenerator() {
        int primeNumber1 = 7;
        int primeNumber2 = 11;
        int m = primeNumber1 * primeNumber2;
        int count = 3; // CoPrime
        int modularisedCount;
        byte[] key = new byte[8];

        for (int i = 0; i < 8; i++) {
            count = count * count;
            modularisedCount = count % m;
            if (modularisedCount % 2 == 0) {
                key[i] = 0;
            } else {
                key[i] = 1;
            }
        }

        return key;
    }

    /**
     * Description
     */
    private void oneTimePadEncryption() {
        // Create key
        byte[] key = pseudoRandomGenerator();

        // Retrieve text and get byte representation
        byte[] plaintext = message2TextPane.getText().getBytes(StandardCharsets.UTF_8);

        StringBuilder binaryRepresentation = new StringBuilder();
        byte[] encrypted = new byte[key.length];

        for (int i = 0; i < plaintext.length; i++) {
            encrypted[i] = (byte) (plaintext[i] ^ key[i]);
            binaryRepresentation.append(String.format("%8s", Integer.toBinaryString(encrypted[i] & 0xFF)).replace(' ', '0'));
        }

        performSteganography(String.valueOf(binaryRepresentation));
    }

    /**
     * Description
     */
    private void performSteganography(String binaryRepresentation) {
        // Default messages
        String normalMessage = message1TextPane.getText();

        // Format binary
        String formattedSecretMessage = binaryRepresentation.replaceAll("\\s+", "");
        formattedSecretMessage = formattedSecretMessage + "x ";

        int count = 0;
        // Construct new message
        StringBuilder newMessage = new StringBuilder();
        for (int i = 0; i < normalMessage.length(); i++) {

            if (normalMessage.charAt(i) == ' ') {
                switch (formattedSecretMessage.charAt(count)) {
                    case '0':
                        newMessage.append(" ");
                        count++;
                        break;
                    case '1':
                        newMessage.append("  ");
                        count++;
                        break;
                    case 'x':
                        newMessage.append("   ");
                        count++;
                        break;
                    case ' ':
                        newMessage.append(" ");
                        break;
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
        String steganographyMessage = encryptedMessageTextPane.getText();

        int count = 0;
        boolean messageFound = false;
        // Construct new message
        StringBuilder secretMessage = new StringBuilder();
        StringBuilder normalMessage = new StringBuilder();

        while (count < steganographyMessage.length()) {
            if (steganographyMessage.charAt(count) == ' ' && !messageFound) {
                if (steganographyMessage.charAt(count + 1) != ' ') {
                    normalMessage.append(" ");
                    secretMessage.append("0");
                    count++;
                } else if (steganographyMessage.charAt(count + 1) == ' ' && steganographyMessage.charAt(count + 2) != ' ') {
                    normalMessage.append(" ");
                    secretMessage.append("1");
                    count += 2;
                } else if (steganographyMessage.charAt(count + 1) == ' ' && steganographyMessage.charAt(count + 2) == ' ') {
                    normalMessage.append(" ");
                    count += 3;
                    messageFound = true;
                }
            } else {
                normalMessage.append(steganographyMessage.charAt(count));
                count++;
            }
        }

        oneTimePadDecryption(String.valueOf(secretMessage));
        decryptedMessage2TextArea.setText(String.valueOf(normalMessage));
    }

    /**
     * Description
     */
    private void oneTimePadDecryption(String secretMessage) {
        // Create key
        byte[] key = pseudoRandomGenerator();

        // Retrieve text and get byte representation
        byte[] plaintext = secretMessage.getBytes(StandardCharsets.UTF_8);

        byte[] decrypted = new byte[plaintext.length];

        for (int i = 0; i < key.length; i++) {
            decrypted[i] = (byte) (plaintext[i] ^ key[i]);
            // TODO: Convert binary to String
        }

        String s = new String(decrypted, StandardCharsets.UTF_8);
        decryptedMessage1TextArea.setText(s);

    }

}
