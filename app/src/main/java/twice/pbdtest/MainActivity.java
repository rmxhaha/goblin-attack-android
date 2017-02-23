package twice.pbdtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //prosedur buat signup
    public void signUp(View view){
        Intent intentSignUp = new Intent(this, FormSignUp.class);
        startActivity(intentSignUp);
    }

    //prosedur buat signup
    public void signIn(View view){
        Intent intentSignIn = new Intent(this, FormSignIn.class);
        startActivity(intentSignIn);
    }
}
