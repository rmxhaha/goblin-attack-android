package twice.pbdtest;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

public class ChatActivity extends AppCompatActivity {
    public String senderName;
    public String token;
    public String chattxt;
    public String senderUID;
    public String receiverUID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        getName(mAuth.getCurrentUser().getUid());
        Intent intent = getIntent();
        receiverUID = intent.getStringExtra("uid");

        TextView hello = (TextView) findViewById(R.id.txtName);
        hello.setText(intent.getStringExtra("name"));

        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = fbdb.getReference("users");
        databaseReference.child(intent.getStringExtra("uid")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                final String user_token = user.getToken();
                token = user_token;
                final EditText chat = (EditText) findViewById(R.id.editChat);
                Button send = (Button) findViewById(R.id.btnSend);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chattxt = chat.getText().toString();
                        chat.setText("");
                        System.out.println(chattxt);
                        new MyDownloadTask().execute();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("cancelled");
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    class MyDownloadTask extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            String url = "https://fcm.googleapis.com/fcm/send";
            String USER_AGENT = "Mozilla/5.0";
            try {
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                //add request header
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Authorization", "key=AAAA-ZUf7Fk:APA91bEF4IiNuco_lxwYyjE5WubphplFSXgvtmDQPXohdFGmtyUAOn2JS1lNsNAMdQNxNDCxLvNLTzkfh_0kAdTybQCbGs22XFGTQrnf4m5idUEWcJKSWy-4Zelo9_Gbo29ENJbIF_KO");

                // Send post request
                con.setDoOutput(true);
                JSONObject obj_json = new JSONObject();
                obj_json.put("to",token);
                JSONObject obj_notif = new JSONObject();
                obj_notif.put("title",senderUID);
                obj_notif.put("body",chattxt);
                obj_json.put("notification", obj_notif);
                String str =  obj_json.toString();
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = con.getOutputStream();
                os.write( outputInBytes );
                os.close();
                String responseMsg = con.getResponseMessage();
                int response = con.getResponseCode();
                System.out.println(responseMsg);

                //done kirim chat
                Chat c = new Chat(chattxt,1);
                FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = fbdb.getReference("users/" + senderUID + "/chats/" + receiverUID );
                databaseReference.child(databaseReference.push().getKey()).setValue(c);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(Void result) {
            // dismiss progress dialog and update ui
        }
    }

    public void getName(String uid){
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = fbdb.getReference("users");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                senderName = user.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("cancelled");
            }
        });
    }

    public void callHistory(View view){
        Intent intentHistory = new Intent(this, ChatHistoryActivity.class);
        intentHistory.putExtra("uid",receiverUID);
        startActivity(intentHistory);
    }
}
