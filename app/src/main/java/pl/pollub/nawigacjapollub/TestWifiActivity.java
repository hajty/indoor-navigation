package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TestWifiActivity extends Activity
{
    private TextView textView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wifi);
        textView = (TextView)findViewById(R.id.textViewWifi);
        this.context = getApplicationContext();
    }

    public void getWifiRecords(View v)
    {
        WifiHelper wifiHelper = new WifiHelper(getApplicationContext());

        String[] macs = wifiHelper.getBestMacs(15);

        if (macs != null)
        {
            textView.setText("");
            for (int i = 0; i < macs.length; i++)
            {
                textView.append(macs[i] + "\n");
            }
        }
    }
}
