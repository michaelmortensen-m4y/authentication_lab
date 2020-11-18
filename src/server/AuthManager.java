package server;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

public class AuthManager {

    public static void main(String[] args) {
        //addUser("Alice", "kodeord", 1);
        System.out.println(login("Alice", "kodeord"));
    }

    public static boolean login(String username, String password) {
        String statement = "SELECT * FROM users WHERE username=? LIMIT 1";

        try (Connection connection = DBManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet  = preparedStatement.executeQuery();

            String salt = resultSet.getString("salt");
            String db_password = resultSet.getString("password");

            Password hashedPassword = hashPassword(password, salt);

            return hashedPassword.getPassword().equals(db_password);
        } catch (SQLException e) {
            System.err.println("somethin very wong");
            System.out.println(e.getMessage());
        }

        return false;
    }

//    private static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[32];
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        final byte[] hash = factory.generateSecret(spec).getEncoded();
//
//        return Base64.getEncoder().encodeToString(hash);
//    }

    private static Password hashPassword(String password) {
        String hashed_password = null;
        String salt = null;

        SecureRandom random = new SecureRandom();
        byte[] salt_bytes = new byte[16];
        random.nextBytes(salt_bytes);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt_bytes);

            final byte[] salted = md.digest(password.getBytes(StandardCharsets.UTF_8));

            hashed_password = Base64.getEncoder().encodeToString(salted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new Password(hashed_password, Base64.getEncoder().encodeToString(salt_bytes));
    }

    private static Password hashPassword(String password, String salt) {
        String hashed_password = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));

            final byte[] salted = md.digest(password.getBytes(StandardCharsets.UTF_8));

            hashed_password = Base64.getEncoder().encodeToString(salted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new Password(hashed_password, salt);
    }

    public static void addUser(String username, String password, Integer role) {
        Password hashedPassword = hashPassword(password);
        String statement = "INSERT INTO users(username, password, salt, role) VALUES(?,?,?,?)";

        try {
            Connection connection = DBManager.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword.password);
            preparedStatement.setString(3, hashedPassword.salt);
            preparedStatement.setInt(4, role);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
