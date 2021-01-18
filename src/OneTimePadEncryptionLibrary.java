import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class OneTimePadEncryptionLibrary {

    /**
     * Perform one time pad encryption with the pseudo random generated key.
     *
     * @param string The secret message.
     * @param key The pseudo random generated key.
     * @return The encrypted secret message.
     */
    public static String encode(String string, String key) {
        return base64Encode(xorWithKey(string.getBytes(), key.getBytes()));
    }

    /**
     * Perform one time pad decryption with the pseudo random generated key.
     *
     * @param string The secret message.
     * @param key The pseudo random generated key.
     * @return The decrypted secret message.
     */
    public static String decode(String string, String key) {
        return new String(xorWithKey(base64Decode(string), key.getBytes()));
    }

    /**
     * Perform a xor function between the message and the key to encrypt the message.
     *
     * @param string The secret message in bytes.
     * @param key The pseudo random generated key in bytes.
     * @return The decrypted secret message in bytes.
     */
    private static byte[] xorWithKey(byte[] string, byte[] key) {
        byte[] out = new byte[string.length];
        for (int i = 0; i < string.length; i++) {
            out[i] = (byte) (string[i] ^ key[i % key.length]);
        }
        return out;
    }

    /**
     * Convert byte representation to String representation.
     *
     * @param bytes The byte representation of a String.
     * @return A human readable string.
     */
    private static String base64Encode(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes).replaceAll("\\s", "");
    }

    /**
     * Convert String representation to byte representation.
     *
     * @param string A human readable string.
     * @return The byte representation of the string.
     */
    private static byte[] base64Decode(String string) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert a string to a binary string representation.
     *
     * @param text A human readable string.
     * @return The binary representation of the string.
     */
    public static String stringToBinary(String text) {
        StringBuilder binaryString= new StringBuilder();
        String temp;
        for(int i = 0; i < text.length(); i++) {
            StringBuilder tempBuilder = new StringBuilder(Integer.toBinaryString(text.charAt(i)));
            for(int j = tempBuilder.length(); j<8; j++) {
                tempBuilder.insert(0, "0");
            }
            temp = tempBuilder.toString();
            binaryString.append(temp).append(" ");
        }
        return binaryString.toString();
    }

    /**
     * Convert a binary representation string to a human readable string.
     *
     * @param binaryCode The binary representation of a string.
     * @return A human readable version of that string.
     */
    public static String BinaryToString(String binaryCode) {
        String[] code = binaryCode.split(" ");
        StringBuilder word = new StringBuilder();
        for (String s : code) {
            word.append((char) Integer.parseInt(s, 2));
        }
        return word.toString();
    }

}
