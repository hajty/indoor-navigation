package pl.pollub.nawigacjapollub;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.ArrayList;
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

    public String[] getBestMacs(int howManyToReturn)
    {
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String[] macsToReturn = new String[howManyToReturn];

        if (!wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(true);
        }

        boolean success = wifiManager.startScan();

        if (success) scanSuccess();
        else
        {
            Toast.makeText(this.context, "Odczekaj chwilę i spróbuj ponownie...", Toast.LENGTH_LONG).show();
            return null;
        }

        for (int i = 0; i < howManyToReturn; i++)
            macsToReturn[i] = this.macs[i];

        return macsToReturn;
    }

    private void scanSuccess()
    {
        this.results = wifiManager.getScanResults();
        List<String> only24 = new ArrayList<>();

        for (int i = 0; i < this.results.size(); i++)
        {
            if (this.results.get(i).frequency < 2500)
                only24.add(this.results.get(i).BSSID);
        }

        this.macs = new String[only24.size()];

        for (int i = 0; i < only24.size(); i++)
                macs[i] = only24.get(i);
    }
}
