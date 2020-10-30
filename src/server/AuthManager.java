package server;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class AuthManager {

    public static void main(String[] args) {
        String password = "password123";
        String password2 = "supersecurepassword";

        try {
            System.out.println(AuthManager.hashPassword(password));
            System.out.println(AuthManager.hashPassword(password2));
            System.out.println(AuthManager.hashPassword(password));
        } catch (Exception e) {
            System.err.println("Couldn't hash password");
            e.printStackTrace();
        }
    }

    public boolean login(String username, char[] password) {
        return username != null && password != null;
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Arrays.toString(hash);
    }
}
