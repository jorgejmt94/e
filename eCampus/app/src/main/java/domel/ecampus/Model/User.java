package domel.ecampus.Model;


public class User {

    private static int auto_inc_id = 0;

    private int id;

    private String email;

    private String password;

    private boolean remembered;

    public User() {
    }

    public User(String email, String password, boolean remembered) {
        this.id = auto_inc_id++;
        this.email = email;
        this.password = password;
        this.remembered = remembered;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemembered() {
        return remembered;
    }

    public void setRemembered(boolean remembered) {
        this.remembered = remembered;
    }
}
