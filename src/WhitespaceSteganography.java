public class WhitespaceSteganography {

    /**
     * Description
     *
     * @return Description
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
     * Description
     *
     * @return Description
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
