package twice.pbdtest.CollectGem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import twice.pbdtest.R;

public class CollectGemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_gem);
        // a potentially  time consuming task
        final GemAPI g = new GemAPI("gemTokenFake");
        g.setGemGetListener( new GemAPI.GetGemListener(){

            @Override
            public void gemGotten(String type, int count) {
                TextView t = (TextView) findViewById(R.id.message);
                t.setText("You got" + count + " "+ type );
            }
        });
        new Thread(new Runnable() {
            public void run() {
                g.run();
            }
        }).start();
    }
}
