public class ModuloLibrary {

    /**
     * Description
     *
     * @param input Description
     * @return Description
     */
    public static int mod(int input) {
        int result = input % 11;
        if (result < 0) {
            result += 11;
        }
        return result;
    }

    /**
     * Description
     *
     * @param negative Description
     * @return Description
     */
    public static int minus(int number, int negative) {
        return mod(number - negative);
    }

    /**
     * Description
     *
     * @param number  Description
     * @param divisor Description
     * @return Description
     */
    public static int inverse(int number, int divisor) {
        switch (divisor) {
            case 1:
                return mod(number);
            case 2:
                return mod(number * 6);
            case 3:
                return mod(number * 4);
            case 4:
                return mod(number * 3);
            case 5:
                return mod(number * 9);
            case 6:
                return mod(number * 2);
            case 7:
                return mod(number * 8);
            case 8:
                return mod(number * 7);
            case 9:
                return mod(number * 5);
            case 10:
                return mod(number * 10);
            default:
                return 0;
        }
    }

    /**
     * Description
     *
     * @param number Description
     * @return Description
     */
    public static int squared(int number) {
        return mod(number * number);
    }

    /**
     * Description
     *
     * @param number Description
     * @return Description
     */
    public static int squareRoot(int number) {
        switch (number) {
            case 1:
                return 1;
            case 3:
                return 5;
            case 4:
                return 2;
            case 5:
                return 4;
            case 9:
                return 3;
            default:
                return 0;
        }
    }

}