package natera.task.local.url_shortener.util;

import java.util.Random;

public class ShortCodeGenerator {
    
    private static final String SYMBOLS = "abcdefghijklmnopqrstuvwxyz";
    private static final Random random = new Random();

    private ShortCodeGenerator() {
    }

    public static String generateShortCode() {
        return generateShortCode(8);
    }

    public static String generateShortCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        }
        return sb.toString();
    }
}