package twice.pbdtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class FormSignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_form_sign_in);
    }

    public void signIn(View view){
        EditText emailView = (EditText) findViewById(R.id.editTxtEmail);
        String email = emailView.getText().toString();
        EditText passwordView = (EditText) findViewById(R.id.editTxtPassword);
        String password = passwordView.getText().toString();
        final Intent intentHome = new Intent(this, HomeActivity.class);

        System.out.println(email);
        System.out.println(password);

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(FormSignInActivity.this, "Sign in Failed : " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String token = FirebaseInstanceId.getInstance().getToken();

                        //update token di database
                        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference = fbdb.getReference("users");
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference userRef = databaseReference.child(user_id);;
                        Map<String, Object> userUpdates = new HashMap<String, Object>();
                        userUpdates.put("token", token);
                        userRef.updateChildren(userUpdates);

                        startActivity(intentHome);
                    }
                }
            });
    }
}
