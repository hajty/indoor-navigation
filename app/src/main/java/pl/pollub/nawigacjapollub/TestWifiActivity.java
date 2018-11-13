package pl.pollub.nawigacjapollub;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TestWifiActivity extends Activity
{
    private TextView textView;
    private WifiManager wifiManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wifi);
        textView = (TextView)findViewById(R.id.textViewWifi);
        this.context = this.getApplicationContext();
    }

    public void getWifiRecords(View v)
    {
        ActivityCompat.requestPermissions(TestWifiActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        ActivityCompat.requestPermissions(TestWifiActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                2);

        WifiHelper wifiHelper = new WifiHelper(this.context);

        String[] macs = wifiHelper.getBestMacs();
        if (macs != null)
        {
            textView.setText(macs[0]);
            textView.append("\n" + macs[1]);
        }
    }
}
