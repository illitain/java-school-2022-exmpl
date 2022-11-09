package ru.croc.task9;
import org.testng.annotations.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.testng.AssertJUnit.assertEquals;

public class Tests {

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    private static String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(HEX_DIGITS[(b & 0xff) >> 4]);
            hex.append(HEX_DIGITS[b & 0x0f]);
        }
        return hex.toString();
    }

    private static String hashPassword(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(password.getBytes());
        byte[] bytes = digest.digest();
        return toHexString(bytes);
    }

    @Test
    private void test() throws Exception {
        int threadsNumber = 4;
        String initialHash = "40682260CC011947FC2D0B1A927138C5";

        String password = Solution.calculatePassword(threadsNumber, initialHash);
        String hash = hashPassword(password);

        assertEquals(initialHash, hash);
        }

}
