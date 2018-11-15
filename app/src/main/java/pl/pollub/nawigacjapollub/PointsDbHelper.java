package pl.pollub.nawigacjapollub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PointsDbHelper extends SQLiteOpenHelper
{

    public PointsDbHelper(Context context)
    {
        super(context, PointsContract.DATABASE_NAME, null, PointsContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String[] ssid1 = {"CONF", "INSTR", "CONF", "CONF", "INSTR", "WEII_AS", "eduroam"};
        String[] ssid2 = {"eduroam", "CONF", "eduroam", "eduroam", "WEII", "CONF", "INSTR"};
        String[] mac1 = {"fc:15:b4:bd:f3:f3", "fc:15:b4:bd:f2:b4", "fc:15:b4:bd:f2:b3",
                "fc:15:b4:bd:f3:33", "fc:15:b4:bd:f3:34", "fc:15:b4:bd:f3:32", "3c:d9:2b:82:44:80"};
        String[] mac2 = {"fc:15:b4:bd:f3:f0", "fc:15:b4:bd:f2:b3", "fc:15:b4:bd:f2:b0",
                "fc:15:b4:bd:f3:30", "fc:15:b4:bd:f3:31", "fc:15:b4:bd:f3:33", "3c:d9:2b:84:44:84"};
        Double[] n = {51.237089, 51.236938, 51.236872, 51.236220, 51.236772, 51.236741, 51.236745};
        Double[] e = {22.549339, 22.549108, 22.548992, 22.548920, 22.548833, 22.548751, 22.548593};

        db.execSQL(PointsContract.PointsEntry.CREATE_TABLE);

        for (int i = 0; i < 7; i++)
        {
            ContentValues values = new ContentValues();

            values.put(PointsContract.PointsEntry.COLUMN_NAME_SSID1, ssid1[i]);
            values.put(PointsContract.PointsEntry.COLUMN_NAME_MAC1, mac1[i]);
            values.put(PointsContract.PointsEntry.COLUMN_NAME_SSID2, ssid2[i]);
            values.put(PointsContract.PointsEntry.COLUMN_NAME_MAC2, mac2[i]);
            values.put(PointsContract.PointsEntry.COLUMN_NAME_N, n[i]);
            values.put(PointsContract.PointsEntry.COLUMN_NAME_E, e[i]);

            try
            {
                db.insert(PointsContract.PointsEntry.TABLE_NAME, null, values);
            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(PointsContract.PointsEntry.DELETE_TABLE);
        onCreate(db);
    }

    public Cursor getAllPoints()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
            PointsContract.PointsEntry.TABLE_NAME,
                null,
            null,
            null,
            null,
            null,
            null
    );

        return cursor;
    }

    public Cursor getPoint(String[] MACs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                PointsContract.PointsEntry.COLUMN_NAME_N,
                PointsContract.PointsEntry.COLUMN_NAME_E
        };
        String selection;
        String[] selectionArgs;

<<<<<<< HEAD
//        if (MACs.length == 1)
//        {
//            selection = PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?";
//            selectionArgs = new String[] {MACs[0]};
//        }
//        else if (MACs.length == 2)
//        {
//            selection = PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?"
//                      + " AND "
//                      + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?";
//            selectionArgs = new String[] {MACs[0], MACs[1]};
//        }
//        else return null;

        selection = PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?"
                + " OR "
                + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?";
        selectionArgs = new String[] {MACs[0], MACs[1]};
=======
        if (MACs != null)
        {
            switch (MACs.length)
            {
                case 1:
                    selection = PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?";
                    selectionArgs = new String[] {MACs[0]};
                    break;

                case 2:
                    selection = PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?"
                            + " AND "
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?";
                    selectionArgs = new String[] {MACs[0], MACs[1]};
                    break;

                default:
                    return null;
            }
        }
        else return null;
>>>>>>> test

        Cursor cursor = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return cursor;
    }
}
