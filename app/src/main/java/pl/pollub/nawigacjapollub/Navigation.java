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

    public LatLng localize(String[] MACs)
    {
        PointsDbHelper db = new PointsDbHelper(this.context);
        Double n, e;
        Cursor cursor;
        LatLng position;

        cursor = db.getPoint(MACs);
        cursor.moveToNext();
        n = cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_N));
        e = cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_E));
        position = new LatLng(n, e);

        return position;
    }
}