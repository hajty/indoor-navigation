package pl.pollub.nawigacjapollub;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.Toast;

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

        for (int i = 0; i < this.results.size(); )
        {
            if (this.results.get(i).frequency >= 2500)
            {
                i++;
                this.results.remove(i-1);
            }
            else i++;
        }

        this.macs = new String[this.results.size()];

        for (int i = 0; i < this.results.size(); i++)
                macs[i] = results.get(i).BSSID;
    }
}
