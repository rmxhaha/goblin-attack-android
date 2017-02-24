package twice.pbdtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class ChatHistoryActivity extends AppCompatActivity {
    ListView listView ;
    private FirebaseAuth mAuth;
    private String receiverUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        Intent intent = getIntent();
        receiverUID = intent.getStringExtra("uid");

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
            body[i] = vecChat.get(i).body;
            System.out.println(body[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, body);
        for(int i=0; i<vecChat.size(); i++){
            body[i] = vecChat.get(i).body;
            System.out.println(body[i]);
        }

        listView.setAdapter(adapter);
    }
}
