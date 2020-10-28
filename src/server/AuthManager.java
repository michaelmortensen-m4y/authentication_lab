package server;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;

public class AuthManager {
    public boolean login(String username, char[] password) {
        return username != null && password != null;
    }

    private String hashPassword(char[] password) {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("HmacSHA512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
