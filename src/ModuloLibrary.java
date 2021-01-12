public class ModuloLibrary {

    /**
     * Description
     *
     * @param input Description
     * @return Description
     */
    public static int mod(int input) {
        int result = input % 11;
        if (result < 0)
            result += 11;
        return result;
    }

    /**
     * Description
     *
     * @param number Description
     * @return Description
     */
    public static int negative(int number) {
        switch (number) {
            case 1:
                return 10;
            case 2:
                return 9;
            case 3:
                return 8;
            case 4:
                return 7;
            case 5:
                return 6;
            case 6:
                return 5;
            case 7:
                return 4;
            case 8:
                return 3;
            case 9:
                return 2;
            case 10:
                return 1;
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
        switch (number) {
            case 1:
            case 10:
                return 1;
            case 2:
            case 9:
                return 4;
            case 3:
            case 8:
                return 9;
            case 4:
            case 7:
                return 5;
            case 5:
            case 6:
                return 3;
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