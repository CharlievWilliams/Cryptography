public class CheckingDigitLibrary {

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
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
     * Description
     *
     * @param inputArray Description
     * @return Description
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
     * Description
     *
     * @param inputArray Description
     * @return Description
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
     * Description
     *
     * @param inputArray Description
     * @return Description
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