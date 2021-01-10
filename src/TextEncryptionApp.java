import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

}
