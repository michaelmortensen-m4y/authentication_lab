package server;

public class User {
    private final String username;
    private final Integer role;
    private final String token;

    public User(String username, Integer role, String token) {

        this.username = username;
        this.role = role;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public Integer getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}
