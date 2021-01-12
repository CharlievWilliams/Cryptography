import java.util.Random;

public class SudokuSteganography {

    public static String[] numbersRepresenting1 = {"1", "3", "6", "8"};
    public static String[] numbersRepresenting2 = {"2", "4", "5", "7"};
    public static String[] anyNumber = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * Description
     *
     * @param binaryRepresentation Description
     * @param normalMessage Description
     * @return Description
     */
    public static String performFilmSteganography(String binaryRepresentation, String normalMessage) {

        // Format binary
        String formattedSecretMessage = binaryRepresentation.replaceAll("\\s+", "");
        formattedSecretMessage = formattedSecretMessage + "x";

        int count = 0;
        // Construct new message
        StringBuilder newMessage = new StringBuilder();
        Random random = new Random();

        newMessage.append(normalMessage).append("*");
        newMessage.append("\n\nOn another note, check out this sudoku puzzle I completed earlier. It took me ages!:\n");

        while (count < formattedSecretMessage.length()) {

            if (count % 9 == 0) {
                newMessage.append("\n");
            }
            switch (formattedSecretMessage.charAt(count)) {
                case '1':
                    newMessage.append(numbersRepresenting1[random.nextInt(4)]).append(" ");
                    count++;
                    break;
                case '0':
                    newMessage.append(numbersRepresenting2[random.nextInt(4)]).append(" ");
                    count++;
                    break;
                case 'x':
                    newMessage.append("9 ");
                    count++;
                    break;
            }

        }

        while (count < 81) {
            if (count % 9 == 0) {
                newMessage.append("\n");
            }
            newMessage.append(anyNumber[random.nextInt(9)]).append(" ");
            count++;
        }

        return newMessage.toString();
    }

    /**
     * Description
     *
     * @param steganographyMessage Description
     * @return Description
     */
    public static String reverseSudokuSteganography(String steganographyMessage) {

        int count = 0;
        boolean normalMessageEnded = false;

        // Construct new message
        StringBuilder secretMessage = new StringBuilder();
        StringBuilder normalMessage = new StringBuilder();

        while (count < steganographyMessage.length() && !normalMessageEnded) {
            if (steganographyMessage.charAt(count) == '*') {
                normalMessageEnded = true;
            } else {
                normalMessage.append(steganographyMessage.charAt(count));
            }
            count++;
        }

        while (count < steganographyMessage.length()) {
            if (steganographyMessage.charAt(count) == '9') {
                TextEncryptionApp.oneTimePadDecryption(String.valueOf(secretMessage));
                return String.valueOf(normalMessage);
            } else if (steganographyMessage.charAt(count) == '1' ||
                    steganographyMessage.charAt(count) == '3' ||
                    steganographyMessage.charAt(count) == '6' ||
                    steganographyMessage.charAt(count) == '8') {
                secretMessage.append("1");
            }
            else if (steganographyMessage.charAt(count) == '2' ||
                    steganographyMessage.charAt(count) == '4' ||
                    steganographyMessage.charAt(count) == '5' ||
                    steganographyMessage.charAt(count) == '7') {
                secretMessage.append("0");
            }
            count++;
        }

        TextEncryptionApp.oneTimePadDecryption(String.valueOf(secretMessage));
        return String.valueOf(normalMessage);
    }
}