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

public class FormSignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_form_sign_up);
    }

    public void signUp(View view){
        EditText emailView = (EditText) findViewById(R.id.editTxtEmail);
        String email = emailView.getText().toString();
        String temp = email;
        temp = temp.replace(".","");
        final String email_final = temp;
        EditText passwordView = (EditText) findViewById(R.id.editTxtPassword);
        String password = passwordView.getText().toString();
        EditText nameView = (EditText) findViewById(R.id.editTxtName);
        final String name = nameView.getText().toString();
        final Intent intentMain = new Intent(this, MainActivity.class);

        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = fbdb.getReference("users");
        //System.out.println(email);
        //System.out.println(password);
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(FormSignUpActivity.this, "Registration Failed :" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println(task.getException());
                        } else {
                            User user = new User(name);
                            String user_id = mAuth.getCurrentUser().getUid();
                            //System.out.println(email_final);
                            databaseReference.child(user_id).setValue(user);
                            startActivity(intentMain);
                        }
                    }
                });
    }
}
