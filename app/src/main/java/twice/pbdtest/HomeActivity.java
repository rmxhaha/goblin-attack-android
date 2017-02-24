package twice.pbdtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import twice.pbdtest.AddFriend.AddFriendActivity;

public class HomeActivity extends AppCompatActivity {
    private String user_email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //get user
        final User[] user = new User[1];
        mAuth = FirebaseAuth.getInstance();
        user_email = mAuth.getCurrentUser().getEmail();
        user_email = user_email.replace(".","");
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = fbdb.getReference("users");
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user[0] = dataSnapshot.getValue(User.class);
                TextView hello = (TextView) findViewById(R.id.helloUser);
                hello.setText("hello " + user[0].getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("cancelled");
            }
        });


    }

    public void shareIntent(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://goblin-attack.herokuapp.com/");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share to"));
    }

    public void callCompass(View view){
        Intent intentCompass = new Intent(this, CompassActivity.class);
        startActivity(intentCompass);
    }

    public void addFriendIntent(View view){
        Intent intentAddFriend = new Intent(this, AddFriendActivity.class);
        startActivity(intentAddFriend);
    }
}
