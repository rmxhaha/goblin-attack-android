package twice.pbdtest.CollectGem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import twice.pbdtest.R;

public class CollectGemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_gem);
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                GemAPI g = new GemAPI("gemTokenFake");
            }
        }).start();

    }
}
