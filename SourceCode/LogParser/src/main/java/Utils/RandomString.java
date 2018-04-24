package Utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    private final Random random;

    private final char[] buf;
    private final char[] symbols;

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    /**
     * This returns a 21 character random string
     *
     * @return
     */
    public String getRandomString() {

        for (int i = 0; i < buf.length; ++i)
            buf[i] = symbols[random.nextInt(symbols.length)];

        return new String(buf);
    }

    private RandomString(int length, Random random, String symbols) {
        this.random = Objects.requireNonNull(random);

        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    private RandomString(int length, Random random) {
        this(length, random, alphanum);
    }

    private RandomString(int length) {
        this(length, new SecureRandom());
    }

    public RandomString() {
        this(21);
    }
}