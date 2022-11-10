package ru.croc.task9.tests;

import org.testng.annotations.Test;
import ru.croc.task9.Solution;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;


public class PasswordHashTest {

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    private static final String INITIAL_HASH = "40682260CC011947FC2D0B1A927138C5";


    @Test
    private void positiveMainCaseTest() {
        int threadsNumber = 4;

        String password = Solution.calculatePassword(threadsNumber, INITIAL_HASH);
        String hash = hashPassword(password);

        assertEquals(hash, INITIAL_HASH);
    }


    @Test
    private void negativeMainCaseTest() {
        int threadsNumber = 4;

        String someStrForTest = "smsmbls";
        String someStringHash = hashPassword(someStrForTest);
        String password = Solution.calculatePassword(threadsNumber, someStringHash);

        assertNotSame(password, someStrForTest);
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

    private static String toHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(HEX_DIGITS[(b & 0xff) >> 4]);
            hex.append(HEX_DIGITS[b & 0x0f]);
        }
        return hex.toString();
    }
}
