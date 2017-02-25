package twice.pbdtest;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class BrightnessActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);
    }

    public void setBrightness(View view){
        try
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this)) {
                    // To handle the auto
                    Settings.System.putInt(this.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS, 20);

                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    TextView hello = (TextView) findViewById(R.id.txtBrightness);
                    String s = hello.getText().toString();
                    float f = Float.valueOf(s);

                    lp.screenBrightness =f;// 100 / 100.0f;
                    getWindow().setAttributes(lp);

                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("Brightness", s);
                    editor.commit();
                    System.out.println("done");
                }
                else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
        catch (Exception e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
    }

    public Context getActivity() {
        return this;
    }
}
