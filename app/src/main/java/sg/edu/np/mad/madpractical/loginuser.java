package sg.edu.np.mad.madpractical;

public class loginuser {
    public String username;
    public String password;

    public loginuser() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public loginuser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
