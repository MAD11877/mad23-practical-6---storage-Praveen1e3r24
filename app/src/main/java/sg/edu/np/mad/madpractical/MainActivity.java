package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<User> allUsersList;
    private User currentUser;
    private int currentUserPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allUsersList = getIntent().getParcelableArrayListExtra("users");
        currentUser = getIntent().getParcelableExtra("user");
        currentUserPos = getIntent().getIntExtra("userpos", -1);

        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        textView4.setText(currentUser.getMsg());
        textView5.setText(currentUser.getDesc());

        Button followButton = findViewById(R.id.button1);
        followButton.setText(currentUser.isFollowed() ? "Unfollow" : "Follow");

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setFollowed(!currentUser.isFollowed());
                followButton.setText(currentUser.isFollowed() ? "Unfollow" : "Follow");

                if (currentUserPos != -1) {
                    allUsersList.set(currentUserPos, currentUser);
                }

                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("updatedUserList", allUsersList);
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
