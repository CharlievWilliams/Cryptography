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
     * Main function for the software. Create the JFrame and assign its content.
     *
     * @param args Unused console args.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Text Encryption App");
        frame.setContentPane(new TextEncryptionApp().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);
        frame.setVisible(true);
    }

    /**
     * Create mouse click listeners for the GUI.
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
                Tuple<String, String> results = SudokuSteganography.reverseSudokuSteganography(encryptedMessageTextPane.getText());
                oneTimePadDecryption(results.y);
                decryptedMessage2TextArea.setText(results.x);
            }
        });
    }

    /**
     * Clear all text panes on the GUI.
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
     * Perform a one time pad encryption on the secret message with the generated string from the Blum blum shlub
     * generator.
     */
    public void oneTimePadEncryption() {
        // Retrieve text
        String plaintext = message2TextPane.getText();

        // Create key
        String key = PseudoRandomGenerator.pseudoRandomGenerator(plaintext.length());

        // Encrypt with one time pad encryption
        String encoded = OneTimePadEncryptionLibrary.encode(plaintext, key);

        String binaryRepresentation = OneTimePadEncryptionLibrary.stringToBinary(encoded);

        encryptedMessageTextArea.setText(SudokuSteganography.
                performSudokuSteganography(binaryRepresentation, message1TextPane.getText())
        );
    }

    /**
     * Perform a one time pad decryption on the secret message with the generated string from the Blum blum shlub
     * generator.
     *
     * @param binaryRepresentation The binary representation of the encrypted secret message.
     */
    public void oneTimePadDecryption(String binaryRepresentation) {

        // Convert secret message to string
        String encoded = OneTimePadEncryptionLibrary.BinaryToString(binaryRepresentation);

        // Create key
        String key = PseudoRandomGenerator.pseudoRandomGenerator(encoded.length());

        // Decode message with the same key
        String decoded = OneTimePadEncryptionLibrary.decode(encoded, key);

        decryptedMessage1TextArea.setText(decoded);
    }
}