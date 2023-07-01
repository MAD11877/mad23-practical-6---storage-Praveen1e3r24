package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    EditText username,password;
    Button Login;
    private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.editTextText);
        password=findViewById(R.id.editTextTextPassword);
        Login=findViewById(R.id.button14);
        mDatabase = FirebaseDatabase.getInstance().getReference();
writeNewUser("praveen","123456");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us,pass;
                us=username.getText().toString();
                pass=password.getText().toString();
                if(TextUtils.isEmpty(us)){
                    Toast.makeText(Login.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
    public void writeNewUser( String name, String password) {
        loginuser user = new loginuser(name, password);

        mDatabase.child("users").setValue(user);
    }
}