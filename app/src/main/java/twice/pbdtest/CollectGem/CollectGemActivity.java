package twice.pbdtest.CollectGem;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import twice.pbdtest.AddFriend.*;
import twice.pbdtest.R;

public class CollectGemActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private void initShakeDetector() {
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });
    }

    private void handleShakeEvent(int count) {
        TextView t = (TextView) findViewById(R.id.message);
        t.setText("Collecting your gem...");

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS
                            // ...
                            new RetriveGemTask().execute(new Pair<String, String>("fake",idToken));

                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                });
    }

    class RetriveGemTask extends AsyncTask<Pair<String,String>, Void, Gem >{

        @Override
        protected Gem doInBackground(Pair<String, String>... params) {
            final GemAPI g = new GemAPI(params[0].first, params[0].second);
            return g.run();
        }

        protected void onPostExecute(Gem result) {
            TextView t = (TextView) findViewById(R.id.message);

            t.setText("You got "+result.getCount() + " " + result.getType() + " gem" );

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_gem);
        initShakeDetector();
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);

    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
    }
}
