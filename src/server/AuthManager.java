package server;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class AuthManager {

    public static void main(String[] args) {
        String password = "password123";
        String password2 = "supersecurepassword";

        try {
            String test1 = AuthManager.hashPassword2(password);
            String test2 = AuthManager.hashPassword2(password2);
            String test3 = AuthManager.hashPassword2(password);

            System.out.println(test1);
            System.out.println(test2);
            System.out.println(test3);

            
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

    private static String hashPassword2(String password) {
        char[] passwordChars = password.toCharArray();
        byte[] passwordBytes = password.getBytes();

        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);

            final byte[] salted = md.digest(password.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(salted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
