package twice.pbdtest;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alif on 23/02/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private FirebaseAuth mAuth;

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String token = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + token);

        if(mAuth.getCurrentUser()!=null) {
            FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = fbdb.getReference("users");
            String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference userRef = databaseReference.child(user_id);
            Map<String, Object> userUpdates = new HashMap<String, Object>();
            userUpdates.put("token", token);
            userRef.updateChildren(userUpdates);
        }
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server

    }
}
