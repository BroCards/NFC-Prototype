package com.example.chitphentom.brocardsutil;

/**
 * Created by Chitphentom on 10/30/2016 AD.
 */

import java.security.SecureRandom;

public class RNGText {
    private static final String Pool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String rng_alphanum(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i  = 0; i < length; i++) {
            sb.append(Pool.charAt(rnd.nextInt(Pool.length())));
        }

        return sb.toString();
    }

}
