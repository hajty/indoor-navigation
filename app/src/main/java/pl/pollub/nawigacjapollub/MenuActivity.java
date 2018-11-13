package pl.pollub.nawigacjapollub;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

public class MenuActivity extends Activity
{
    public final static int REQUEST_SMS = 100;
    public final static int REQUEST_ACCESS_FINE_LOCATION = 200;
    public final static int REQUEST_ACCESS_COARSE_LOCATION = 300;
    public final static int REQUEST_ACCESS_WIFI = 400;
    public final static int REQUEST_CHANGE_WIFI = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActivityCompat.requestPermissions(MenuActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_ACCESS_FINE_LOCATION);
        ActivityCompat.requestPermissions(MenuActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_ACCESS_COARSE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia wysyłania SMS",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień wysyłania SMS",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_FINE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_COARSE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień lokalizacji",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_WIFI:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia stanu sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień stanu sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_CHANGE_WIFI:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getApplicationContext(), "Nadano uprawnienia zmiany sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Nie nadano uprawnień zmiany sieci Wi-Fi",
                            Toast.LENGTH_LONG).show();
                }
            }
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
