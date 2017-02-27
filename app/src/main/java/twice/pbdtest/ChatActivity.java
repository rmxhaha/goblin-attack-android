package twice.pbdtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    public String receiverName;
    public String token;
    public String chattxt;
    public String senderUID;
    public String receiverUID;
    private FirebaseAuth mAuth;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    protected ListView listView ;

    protected void initChatHistory(){
        System.out.println(receiverName);

        mAuth = FirebaseAuth.getInstance();
        System.out.println(receiverUID);
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = fbdb.getReference("users");
        databaseReference.child(mAuth.getCurrentUser().getUid() + "/chats/" + receiverUID).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Vector<Chat> vecChat = new Vector();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Chat c = postSnapshot.getValue(Chat.class);
                    vecChat.add(c);
                }
                listView = (ListView) findViewById(R.id.list);
                initList(vecChat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("cancelled");
            }
        });
    }

    public void initList(final Vector<Chat> vecChat){
        listView = (ListView) findViewById(R.id.list);

        String[] body = new String[vecChat.size()];
        for(int i=0; i<vecChat.size(); i++){
            String str;
            if(vecChat.get(i).flag==1){
                str = "me : ";
                System.out.println(str);
            }
            else{
                str = receiverName + " : ";
                System.out.println(str);
            }
            body[i] = str + vecChat.get(i).body;
            System.out.println(body[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, body);
        listView.setAdapter(adapter);
        listView.setSelection(listView.getCount()-1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String channel = (sharedpreferences.getString("Brightness", ""));
        System.out.println(channel);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (Settings.System.canWrite(this)) {
                // To handle the auto
                Settings.System.putInt(this.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, 20);

                WindowManager.LayoutParams lp = getWindow().getAttributes();
                float f = Float.valueOf(channel);

                lp.screenBrightness = f;// 100 / 100.0f;
                getWindow().setAttributes(lp);
            }
        }

        mAuth = FirebaseAuth.getInstance();
        senderUID = mAuth.getCurrentUser().getUid();
        getName(mAuth.getCurrentUser().getUid());
        Intent intent = getIntent();
        receiverUID = intent.getStringExtra("uid");

        TextView hello = (TextView) findViewById(R.id.txtName);
        hello.setText(intent.getStringExtra("name"));
        receiverName = intent.getStringExtra("name");
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
        initChatHistory();
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

                Chat c2 = new Chat(chattxt,0);
                FirebaseDatabase fbdb2 = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference2 = fbdb2.getReference("users/" + receiverUID + "/chats/" + senderUID );
                databaseReference2.child(databaseReference2.push().getKey()).setValue(c2);
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

}
