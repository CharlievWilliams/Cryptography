import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Library {

    /**
     * Check if the given string is a valid Sha1 hash.
     *
     * @param string the string that will be checked.
     * @return a boolean describing if the string is a valid Sha1 hash.
     */
    public static boolean isValidSHA1(String string) {
        return string.matches("^[a-fA-F0-9]{40}$");
    }

    /**
     * Hash a given String with Sha1.
     *
     * @param text the string that will be hashed.
     * @return The hashed string.
     */
    public static String SHA1(String text) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash;
        md.update(text.getBytes(StandardCharsets.ISO_8859_1), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    /**
     * Convert a Sha1 hash to a readable string.
     *
     * @param data The Sha1 information.
     * @return The readable String.
     */
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte datum : data) {
            int halfByte = (datum >>> 4) & 0x0F;
            int twoHalves = 0;
            do {
                if (halfByte <= 9)
                    buf.append((char) ('0' + halfByte));
                else
                    buf.append((char) ('a' + (halfByte - 10)));
                halfByte = datum & 0x0F;
            } while (twoHalves++ < 1);
        }
        return buf.toString();
    }
}
