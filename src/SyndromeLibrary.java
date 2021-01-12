public class SyndromeLibrary {

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    public static int calculateSyndrome1(int[] inputArray) {
        return (inputArray[0] +
                inputArray[1] +
                inputArray[2] +
                inputArray[3] +
                inputArray[4] +
                inputArray[5] +
                inputArray[6] +
                inputArray[7] +
                inputArray[8] +
                inputArray[9]) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    public static int calculateSyndrome2(int[] inputArray) {
        return (inputArray[0] +
                2 * inputArray[1] +
                3 * inputArray[2] +
                4 * inputArray[3] +
                5 * inputArray[4] +
                6 * inputArray[5] +
                7 * inputArray[6] +
                8 * inputArray[7] +
                9 * inputArray[8] +
                10 * inputArray[9]) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    public static int calculateSyndrome3(int[] inputArray) {
        return (inputArray[0] +
                4 * inputArray[1] +
                9 * inputArray[2] +
                5 * inputArray[3] +
                3 * inputArray[4] +
                3 * inputArray[5] +
                5 * inputArray[6] +
                9 * inputArray[7] +
                4 * inputArray[8] +
                inputArray[9]) % 11;
    }

    /**
     * Description
     *
     * @param inputArray Description
     * @return Description
     */
    public static int calculateSyndrome4(int[] inputArray) {
        return (inputArray[0] +
                8 * inputArray[1] +
                5 * inputArray[2] +
                9 * inputArray[3] +
                4 * inputArray[4] +
                7 * inputArray[5] +
                2 * inputArray[6] +
                6 * inputArray[7] +
                3 * inputArray[8] +
                10 * inputArray[9]) % 11;
    }
}