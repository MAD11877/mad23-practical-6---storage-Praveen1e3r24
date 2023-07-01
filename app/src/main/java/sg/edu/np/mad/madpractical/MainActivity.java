package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<User> allUsersList;
    private User currentUser;
    private int currentUserPos;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        int UserID = getIntent().getIntExtra("userClickedID", -1);
        User user = dataBaseHelper.findUser(UserID);
        Toast.makeText(this, "id"+user.getID(), Toast.LENGTH_SHORT).show();
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        textView4.setText(user.getMsg());
        textView5.setText(user.getDesc());

        Button followButton = findViewById(R.id.button1);
        followButton.setText(user.isFollowed() ? "Unfollow" : "Follow");

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFollowed(!user.isFollowed());
                followButton.setText(user.isFollowed() ? "Unfollow" : "Follow");

                dataBaseHelper.updateUser(user);

                Intent intent = new Intent();

                setResult(RESULT_OK, intent);
            }
        });

        Button x = findViewById(R.id.button3);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityName = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(activityName);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("updatedUserList", allUsersList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
