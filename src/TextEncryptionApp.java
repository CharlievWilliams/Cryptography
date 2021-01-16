import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextEncryptionApp {

    private JPanel mainPanel;
    private JTextPane message1TextPane;
    private JTextPane message2TextPane;
    private JTextPane encryptedMessageTextPane;
    public JTextArea encryptedMessageTextArea;
    public JTextArea decryptedMessage1TextArea;
    public JTextArea decryptedMessage2TextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton clearButton;

    /**
     * Description
     *
     * @param args description
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Encryption App");
        frame.setContentPane(new TextEncryptionApp().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);
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
                decryptedMessage2TextArea.setText(
                        SudokuSteganography.reverseSudokuSteganography(encryptedMessageTextPane.getText())
                );
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
     * Description
     */
    public void oneTimePadEncryption() {
        // Retrieve text
        String plaintext = message2TextPane.getText();

        // Create key
        String key = PseudoRandomGenerator.pseudoRandomGenerator(plaintext.length());

        StringBuilder binaryRepresentation = new StringBuilder();
        int[] encrypted = new int[key.length()];

        for (int i = 0; i < plaintext.length(); i++) {
            encrypted[i] = (plaintext.charAt(i) ^ key.charAt(i));
            binaryRepresentation.append(String.format("%8s", Integer.toBinaryString(encrypted[i] & 0xFF))
                    .replace(' ', '0')
            );
        }
        encryptedMessageTextArea.setText(
                SudokuSteganography.performSudokuSteganography(
                        String.valueOf(binaryRepresentation), message1TextPane.getText()
                )
        );
    }

    /**
     * Description
     */
    public static void oneTimePadDecryption(String binaryRepresentation) {
        // TODO: Make this work

        // decryptedMessage1TextArea.setText(binaryRepresentation);
    }
}
