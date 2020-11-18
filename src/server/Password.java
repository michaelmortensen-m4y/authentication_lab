package server;

public class Password {
    String password;
    String salt;

    public Password(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }
}
