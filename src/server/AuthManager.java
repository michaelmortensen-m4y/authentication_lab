package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class AuthManager {

    static HashMap<String, User> tokens = new HashMap<String, User>();

    public static void main(String[] args) {
        //addUser("Alice", "kodeord", 1);
        System.out.println(login("Alice", "kodeord"));
    }

    public static String login(String username, String password) {
        String statement = "SELECT * FROM users WHERE username=? LIMIT 1";

        try (Connection connection = DBManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet  = preparedStatement.executeQuery();

            String salt = resultSet.getString("salt");
            String db_password = resultSet.getString("password");

            Password hashedPassword = hashPassword(password, salt);

            if (hashedPassword.getPassword().equals(db_password)) {
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getInt("role"),
                        UUID.randomUUID().toString()
                );

                tokens.put(user.getToken(), user);

                return user.getToken();
            }

            return null;
        } catch (SQLException e) {
            System.err.println("somethin very wong");
            System.out.println(e.getMessage());
        }

        return null;
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

    public static boolean checkPermission(String action, String token) {
        int userRole = AuthManager.tokens.get(token).getRole();

        if (action.equals("print")) {
            return userRole == 1 || userRole == 2 || userRole == 3 || userRole == 4;
        }

        if (action.equals("start")) {
            return userRole == 4;
        }

        return false;
    }
}
