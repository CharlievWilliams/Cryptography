public class PseudoRandomGenerator {

    /**
     * Blum Blum Shlub Generator
     */
    public static String pseudoRandomGenerator(int length) {
        int primeNumber1 = 7;
        int primeNumber2 = 11;
        int m = primeNumber1 * primeNumber2;
        int count = 3; // CoPrime
        int modularisedCount;

        StringBuilder key = new StringBuilder();

        for (int i = 0; i < length; i++) {
            count = count * count;
            modularisedCount = count % m;
            if (modularisedCount % 2 == 0) {
                key.append(0);
            } else {
                key.append(1);
            }
        }

        return String.valueOf(key);
    }

}
