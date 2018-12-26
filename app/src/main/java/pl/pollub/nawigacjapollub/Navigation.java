package pl.pollub.nawigacjapollub;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.model.LatLng;

public class Navigation
{
    private Context context;

    public Navigation (Context context)
    {
        this.context = context;
    }

    public LatLng localizeByWifi()
    {
        WifiHelper wifiHelper = new WifiHelper(this.context);
        PointsDbHelper db = new PointsDbHelper(this.context);
        Double n, e;
        Cursor cursor;

        String[] macs = wifiHelper.getBestMacs(2);

        if (macs != null) cursor = db.getCoordinates(macs);
        else return null;

        if (cursor != null)
        {
            cursor.moveToNext();
            n = cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_N));
            e = cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_E));

            return new LatLng(n, e);
        }
        else return null;
    }
}