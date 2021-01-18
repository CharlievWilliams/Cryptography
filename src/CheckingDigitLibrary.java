public class CheckingDigitLibrary {

    /**
     * Create the four checking digits for a BCH(10, 6) code.
     *
     * @param input A string of the six digits used to create the checking digits.
     * @return A string of the original six  digits concatenated with the four new checking digits.
     */
    public static String createCheckingDigits(String input) {

        int[] inputArray = new int[6];
        try {
            for (int i = 0; i < input.length(); i++) {
                inputArray[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        } catch (Exception e) {
            return "Invalid input";
        }

        int checkingDigit1 = calculateDigit7(inputArray);
        int checkingDigit2 = calculateDigit8(inputArray);
        int checkingDigit3 = calculateDigit9(inputArray);
        int checkingDigit4 = calculateDigit10(inputArray);

        if (checkingDigit1 == 10 || checkingDigit2 == 10 || checkingDigit3 == 10 || checkingDigit4 == 10) {
            return "Unusable number";
        } else {
            return input + checkingDigit1 + checkingDigit2 + checkingDigit3 + checkingDigit4;
        }
    }

    /**
     * Calculate the first checking digit.
     *
     * @param inputArray The int array of the first six digits.
     * @return An int of the first checking digit.
     */
    public static int calculateDigit7(int[] inputArray) {
        return ((4 * inputArray[0]) +
                (10 * inputArray[1]) +
                (9 * inputArray[2]) +
                (2 * inputArray[3]) +
                inputArray[4] +
                (7 * inputArray[5])) % 11;
    }

    /**
     * Calculate the second checking digit.
     *
     * @param inputArray The int array of the first six digits.
     * @return An int of the second checking digit.
     */
    public static int calculateDigit8(int[] inputArray) {
        return ((7 * inputArray[0]) +
                (8 * inputArray[1]) +
                (7 * inputArray[2]) +
                inputArray[3] +
                (9 * inputArray[4]) +
                (6 * inputArray[5])) % 11;
    }

    /**
     * Calculate the third checking digit.
     *
     * @param inputArray The int array of the first six digits.
     * @return An int of the third checking digit.
     */
    public static int calculateDigit9(int[] inputArray) {
        return ((9 * inputArray[0]) +
                inputArray[1] +
                (7 * inputArray[2]) +
                (8 * inputArray[3]) +
                (7 * inputArray[4]) +
                (7 * inputArray[5])) % 11;
    }

    /**
     * Calculate the fourth checking digit.
     *
     * @param inputArray The int array of the first six digits.
     * @return An int of the fourth checking digit.
     */
    public static int calculateDigit10(int[] inputArray) {
        return (inputArray[0] +
                (2 * inputArray[1]) +
                (9 * inputArray[2]) +
                (10 * inputArray[3]) +
                (4 * inputArray[4]) +
                inputArray[5]) % 11;
    }
}