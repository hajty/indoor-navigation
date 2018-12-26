package pl.pollub.nawigacjapollub;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends Activity
{
    public final static int PERMISSIONS_ALL = 600;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.context = this.getApplicationContext();
        this.setTitle(getResources().getString(R.string.title_activity_menu));

        this.checkForPermissions();
    }

    private static boolean hasPermissions(Context context, String[] permissions)
    {
        if (context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkForPermissions()
    {
        String[] PERMISSIONS = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        if(!hasPermissions(this, PERMISSIONS))
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_ALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        if (requestCode == PERMISSIONS_ALL)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this.context, "Nadano wszystkie potrzebne uprawnienia",
                        Toast.LENGTH_LONG).show();
            }
            else this.checkForPermissions();
        }
    }

    public void buttonWeiiOnClick(View v)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void buttonTestDatabaseOnClick(View v)
    {
        Intent intent = new Intent(this, TestDatabaseActivity.class);
        startActivity(intent);
    }

    public void buttonTestWifiOnClick(View v)
    {
        Intent intent = new Intent(this, TestWifiActivity.class);
        startActivity(intent);
    }
}
