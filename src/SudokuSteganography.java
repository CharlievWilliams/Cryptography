import java.util.Random;

public class SudokuSteganography {

    public static String[] numbersRepresenting1 = {"1", "3", "6", "8"};
    public static String[] numbersRepresenting2 = {"2", "4", "5", "7"};
    public static String[] anyNumber = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * Perform Sudoku Steganography to hide a secret message within a facade message by encoding the binary
     * representation into a sudoku grid.
     *
     * @param binaryRepresentation A binary representation of the secret message.
     * @param normalMessage The facade message in String format.
     * @return The normal message with the secret message hidden within it in the form of a sudoku grid.
     */
    public static String performSudokuSteganography(String binaryRepresentation, String normalMessage) {

        String formattedSecretMessage = binaryRepresentation.replaceAll("\\s+", "");
        formattedSecretMessage = formattedSecretMessage + "x";

        int count = 0;

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
     * Reverse the Sudoku steganography by separating the binary representation of the secret message from the facade
     * message.
     *
     * @param steganographyMessage The message that has been created through sudoku steganography.
     * @return The facade message in its original format and the binary representation of the secret message.
     */
    public static Tuple<String, String> reverseSudokuSteganography(String steganographyMessage) {

        int count = 0;
        int spaceCount = 0;
        boolean normalMessageEnded = false;

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
                return new Tuple<>(String.valueOf(normalMessage), String.valueOf(secretMessage));
            } else if (steganographyMessage.charAt(count) == '1' ||
                    steganographyMessage.charAt(count) == '3' ||
                    steganographyMessage.charAt(count) == '6' ||
                    steganographyMessage.charAt(count) == '8') {
                secretMessage.append("1");
                spaceCount++;
            }
            else if (steganographyMessage.charAt(count) == '2' ||
                    steganographyMessage.charAt(count) == '4' ||
                    steganographyMessage.charAt(count) == '5' ||
                    steganographyMessage.charAt(count) == '7') {
                secretMessage.append("0");
                spaceCount++;
            }
            if (spaceCount == 8) {
                secretMessage.append(" ");
                spaceCount = 0;
            }
            count++;
        }

        return new Tuple<>(String.valueOf(normalMessage), String.valueOf(secretMessage));
    }
}