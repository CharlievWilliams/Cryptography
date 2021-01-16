public class WhitespaceSteganography {

    /**
     * Perform Sudoku Steganography to hide a secret message within a facade message by encoding the binary
     * representation into the whitespaces of the facade message.
     *
     * @param binaryRepresentation A binary representation of the secret message.
     * @param normalMessage The facade message in String format.
     * @return The normal message with the secret message hidden within it by duplicating whitespace.
     */
    public static String performSimpleSteganography(String binaryRepresentation, String normalMessage) {

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
        return newMessage.toString();
    }

    /**
     * Reverse the whitespace steganography by separating the binary representation of the secret message from the
     * facade message.
     *
     * @param steganographyMessage The message that has been created through whitespace steganography.
     * @return The facade message in its original format.
     */
    public static String reverseSimpleSteganography(String steganographyMessage) {
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

        TextEncryptionApp.oneTimePadDecryption(String.valueOf(secretMessage));
        return String.valueOf(normalMessage);
    }
}