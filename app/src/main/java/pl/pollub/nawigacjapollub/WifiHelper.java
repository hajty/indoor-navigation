package pl.pollub.nawigacjapollub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiHelper
{
    private Context context;
    private WifiManager wifiManager;
    private List<ScanResult> results;
    private String[] macs;

    public WifiHelper(Context context)
    {
        this.context = context;
    }

    public String[] getBestMacs()
    {
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent intent)
            {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);

                if (success)
                {
                    scanSuccess();
                }
                else scanFailure();
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) scanFailure();

        return this.macs;
    }

    private void scanSuccess()
    {
        this.macs = new String[2];
        this.results = wifiManager.getScanResults();

        if (this.results.size() == 1) macs[0] = results.get(0).BSSID;
        else if (this.results.size() > 1)
        {
            macs[0] = results.get(0).BSSID;
            macs[1] = results.get(1).BSSID;
        }
    }

    private void scanFailure()
    {
        this.macs = new String[2];
        this.results = wifiManager.getScanResults();

        if (this.results.size() == 1) macs[0] = results.get(0).BSSID;
        else if (this.results.size() > 1)
        {
            macs[0] = results.get(0).BSSID;
            macs[1] = results.get(1).BSSID;
        }
    }
}
