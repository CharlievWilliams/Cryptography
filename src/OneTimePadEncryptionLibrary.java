import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class OneTimePadEncryptionLibrary {

    /**
     * Description
     *
     * @param string Description
     * @param key Description
     * @return Description
     */
    public static String encode(String string, String key) {
        return base64Encode(xorWithKey(string.getBytes(), key.getBytes()));
    }

    /**
     * Description
     *
     * @param string Description
     * @param key Description
     * @return Description
     */
    public static String decode(String string, String key) {
        return new String(xorWithKey(base64Decode(string), key.getBytes()));
    }

    /**
     * Description
     *
     * @param a Description
     * @param key Description
     * @return Description
     */
    private static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i % key.length]);
        }
        return out;
    }

    /**
     * Description
     *
     * @param string Description
     * @return Description
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
     * Description
     *
     * @param bytes Description
     * @return Description
     */
    private static String base64Encode(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes).replaceAll("\\s", "");
    }

    /**
     * Description
     *
     * @param text Description
     * @return Description
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
     * Description
     *
     * @param binaryCode Description
     * @return Description
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
