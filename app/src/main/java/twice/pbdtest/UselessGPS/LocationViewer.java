package twice.pbdtest.UselessGPS;

import android.app.ActionBar;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import twice.pbdtest.R;

public class LocationViewer extends AppCompatActivity {
    private LocationManager mLocationManager;
    private LocationFinder mLocationFinder;
    private TextView mTextMessage;

    private void initLocationFinder() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationFinder = new LocationFinder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_viewer);
        mTextMessage = (TextView) findViewById(R.id.message);
        initLocationFinder();
        mLocationFinder.getLocation(this, mLocationManager, new LocationFinder.OnLocationFoundListener() {
            @Override
            public void onLocationFound(Location location) {
                handleLocationChange(location);
            }
        });
    }

    private void handleLocationChange(Location location) {
        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());

        mTextMessage.setText("You are at lat=" + latitude + ", lon=" + longitude);

    }
}
