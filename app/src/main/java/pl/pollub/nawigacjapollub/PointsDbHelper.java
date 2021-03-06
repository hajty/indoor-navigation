package pl.pollub.nawigacjapollub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PointsDbHelper extends SQLiteOpenHelper
{

    public PointsDbHelper(Context context)
    {
        super(context, PointsContract.DATABASE_NAME, null, PointsContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*--------------------------------POINTS--------------------------------*/
        {
            String[] department = {"weii", "weii", "weii", "weii", "weii", "weii", "weii", "weii",                      // parter
                    "weii", "weii", "weii", "weii", "weii", "weii", "weii", "weii",                      // I pietro
                    "weii", "weii", "weii", "weii", "weii", "weii", "weii"};                             // II pietro

            int[] floor = {0, 0, 0, 0, 0, 0, 0, 0,                                                                      // parter
                    1, 1, 1, 1, 1, 1, 1, 1,                                                                      // I pietro
                    2, 2, 2, 2, 2, 2, 2};                                                                        // II pietro

            String[] ssid1 = {"CONF", "", "INSTR", "CONF", "CONF", "INSTR", "WEII_AS", "eduroam",                       // parter
                    "eduroam", "CONF", "INSTR", "CONF", "CONF", "CONF", "INSTR", "CONF",                      // I pietro
                    "CONF", "WEII_AES", "INSTR", "eduroam", "WEII_AES", "CONF", "CONF"};                      // II pietro

            String[] ssid2 = {"eduroam", "", "CONF", "eduroam", "eduroam", "WEII", "CONF", "INSTR",                     // parter
                    "INSTR", "eduroam", "CONF", "eduroam", "eduroam", "eduroam", "CONF", "WEII_AES",          // I pietro
                    "eduroam", "CONF", "CONF", "CONF", "WEII", "eduroam", "eduroam"};                         // II pietro

            String[] mac1 = {"fc:15:b4:bd:f3:f3", "", "fc:15:b4:bd:f2:b4", "fc:15:b4:bd:f2:b3",
                    "fc:15:b4:bd:f3:33", "fc:15:b4:bd:f3:34", "fc:15:b4:bd:f3:32", "3c:d9:2b:82:44:80",                 // parter
                    "fc:15:b4:bd:f3:90", "fc:15:b4:bd:f3:73", "fc:15:b4:bd:f3:74", "3c:d9:2b:82:44:53",
                    "3c:d9:2b:82:44:53", "3c:d9:2b:82:44:83", "3c:d9:2b:82:44:84", "3c:d9:2b:82:44:83",                 // I pietro
                    "fc:15:b4:bd:e5:d3", "fc:15:b4:bd:f2:f2", "3c:d9:2b:82:54:14", "3c:d9:2b:82:54:10",
                    "fc:15:b4:bd:f2:f1", "3c:d9:2b:82:54:13", "3c:d9:2b:82:84:43"};                                     // II pietro

            String[] mac2 = {"fc:15:b4:bd:f3:f0", "", "fc:15:b4:bd:f2:b3", "fc:15:b4:bd:f2:b0",
                    "fc:15:b4:bd:f3:30", "fc:15:b4:bd:f3:31", "fc:15:b4:bd:f3:33", "3c:d9:2b:84:44:84",                 // parter
                    "fc:15:b4:bd:f3:94", "fc:15:b4:bd:f3:70", "fc:15:b4:bd:f3:73", "3c:d9:2b:82:44:50",
                    "3c:d9:2b:82:44:80", "3c:d9:2b:82:44:80", "3c:d9:2b:82:44:83", "3c:d9:2b:82:44:82",                 // I pietro
                    "fc:15:b4:bd:e5:d0", "fc:15:b4:bd:f2:f3", "3c:d9:2b:82:54:13", "3c:d9:2b:82:54:13",
                    "3c:d9:2b:82:54:11", "3c:d9:2b:82:54:10", "3c:d9:2b:82:54:40"};                                     // II pietro

            Double[] n = {51.237089, 51.236986, 51.236938, 51.236872, 51.236820, 51.236772, 51.236741, 51.236745,       // parter
                    51.237059, 51.236990, 51.236910, 51.236845, 51.236811, 51.236753, 51.236721, 51.236762,       // I pietro
                    51.237045, 51.236992, 51.236935, 51.236875, 51.236826, 51.236779, 51.236744};                 // II pietro

            Double[] e = {22.549339, 22.549185, 22.549108, 22.548992, 22.548920, 22.548833, 22.548751, 22.548593,       // parter
                    22.549281, 22.549179, 22.549044, 22.548939, 22.548875, 22.548794, 22.548740, 22.548655,       // I pietro
                    22.549278, 22.549192, 22.549095, 22.549000, 22.548922, 22.548841, 22.548782};                 // II pietro

            db.execSQL(PointsContract.PointsEntry.CREATE_TABLE);

            for (int i = 0; i < ssid1.length; i++) {
                ContentValues values = new ContentValues();

                values.put(PointsContract.PointsEntry.COLUMN_NAME_DEPARTMENT, department[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_FLOOR, floor[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_SSID1, ssid1[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_MAC1, mac1[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_SSID2, ssid2[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_MAC2, mac2[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_N, n[i]);
                values.put(PointsContract.PointsEntry.COLUMN_NAME_E, e[i]);

                try {
                    db.insert(PointsContract.PointsEntry.TABLE_NAME, null, values);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        /*--------------------------------EDGES--------------------------------*/
        {
            int[] fromPointId = {1, 2, 3, 4, 5, 6, 7,  2,  7,  9, 10, 11, 12, 13, 14, 15, 10, 15, 17, 18, 19, 20, 21, 22};
            int[] toPointId =   {2, 3, 4, 5, 6, 7, 8, 10, 15, 10, 11, 12, 13, 14, 15, 16, 18, 23, 18, 19, 20, 21, 22, 23};
            int[] length =      {8, 4, 3, 3, 3, 6, 5, 15, 15,  7,  5,  4,  3,  3,  5,  6, 15, 15,  6,  5,  4,  3,  3,  6};

            db.execSQL(PointsContract.EdgesEntry.CREATE_TABLE);

            for (int i = 0; i < fromPointId.length; i++) {
                ContentValues values = new ContentValues();

                values.put(PointsContract.EdgesEntry.COLUMN_NAME_FROM_POINT_ID, fromPointId[i]);
                values.put(PointsContract.EdgesEntry.COLUMN_NAME_TO_POINT_ID, toPointId[i]);
                values.put(PointsContract.EdgesEntry.COLUMN_NAME_LENGTH, length[i]);

                try {
                    db.insert(PointsContract.EdgesEntry.TABLE_NAME, null, values);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        /*--------------------------------ROOMS--------------------------------*/
        {
            int[] idPoints = {1, 1, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8,            // parter
                    9, 9, 9, 10, 11, 11, 11, 11, 12, 12, 12, 12, 12, 13, 13, 14, 14, 15, 16,        // I pietro
                    17, 17, 17, 17, 17, 19, 19, 19, 19, 19, 20, 20, 20, 21, 21, 21, 22, 23};        // II pietro


            String[] roomsNames = {"Portiernia", "Szatnia", "E112", "E112B", "E112A", "E113A", "E113",
                    "E109", "E110", "E111", "E114", "E108", "E115", "E118", "E106A", "E2", "E102", "E102A",
                    "E103", "E103A", "E104", "E105", "E119",                                                  // parter
                    "E211A", "E212", "E213", "E211", "E210", "E214", "E209", "E215", "E217", "E206", "E216",
                    "E207", "E208", "Dziekanat", "E219", "Kier. dziekanatu", "E220A", "E220", "E201",         // I pietro
                    "E311", "E311A", "E312", "E310", "E309", "E313", "E314", "E308", "E307", "E315",
                    "E316", "E317", "E306", "E305", "E318", "E319", "E303", "E320"};                          // II pietro

            db.execSQL(PointsContract.RoomsEntry.CREATE_TABLE);

            for (int i = 0; i < idPoints.length; i++)
            {
                ContentValues values = new ContentValues();

                values.put(PointsContract.RoomsEntry.COLUMN_ROOM_NAME, roomsNames[i]);
                values.put(PointsContract.RoomsEntry.COLUMN_NAME_ID_POINTS, idPoints[i]);

                try
                {
                    db.insert(PointsContract.RoomsEntry.TABLE_NAME, null, values);
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(PointsContract.PointsEntry.DELETE_TABLE);
        db.execSQL(PointsContract.RoomsEntry.DELETE_TABLE);
        db.execSQL(PointsContract.EdgesEntry.DELETE_TABLE);
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

    public Cursor getAllEdges()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PointsContract.EdgesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    public Cursor getAllRooms()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PointsContract.RoomsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PointsContract.RoomsEntry.COLUMN_ROOM_NAME
        );

        return cursor;
    }

    public List<String> getAllRoomsAsList()
    {
        Cursor cursor = this.getAllRooms();

        List<String> spinnerContent = new ArrayList<>();

        while (cursor.moveToNext())
        {
            String roomName = cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.RoomsEntry.COLUMN_ROOM_NAME));
//            String idPoints = cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.RoomsEntry.COLUMN_NAME_ID_POINTS));
            spinnerContent.add(roomName);
//            spinnerContent.add(idPoints);
        }

        return spinnerContent;
    }

    public Cursor getCoordinates(String[] MACs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                PointsContract.PointsEntry.COLUMN_NAME_N,
                PointsContract.PointsEntry.COLUMN_NAME_E
        };
        String selection;
        String[] selectionArgs;

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
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?"
                            + " OR "
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?"
                            + " AND "
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?";

                    selectionArgs = new String[] {MACs[0], MACs[1], MACs[1], MACs[0]};
                    break;

                default:
                    return null;
            }
        }
        else return null;

        Cursor cursor = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() == 1)
        {
            return cursor;
        }
        else return null;
    }

    public int getFloor(int idPoint)
    {
        int floor;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PointsContract.PointsEntry.COLUMN_NAME_FLOOR
        };
        String selection = PointsContract.PointsEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(idPoint)};

        Cursor cursor = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToNext();

        floor = cursor.getInt(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_FLOOR));

        return floor;
    }

    public LatLng getCoordinates(int idPoint)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PointsContract.PointsEntry.COLUMN_NAME_N,
                PointsContract.PointsEntry.COLUMN_NAME_E
        };
        String selection = PointsContract.PointsEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(idPoint)};

        Cursor cursor = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToNext();

        return new LatLng(
            cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_N)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_E)));
    }

    public List<String> getRooms(int idPoint)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> rooms = new ArrayList<>();

        String[] projection = {
                PointsContract.RoomsEntry.COLUMN_ROOM_NAME
        };
        String selection = PointsContract.PointsEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(idPoint)};

        Cursor cursor = db.query(
                PointsContract.RoomsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext())
            rooms.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.RoomsEntry.COLUMN_ROOM_NAME)));

        return rooms;
    }

    public int getPoint(LatLng coordinates)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Double n = coordinates.latitude;
        Double e = coordinates.longitude;

        String[] projection = {
                PointsContract.PointsEntry._ID
        };
        String selection = PointsContract.PointsEntry.COLUMN_NAME_N + " = ?"
                        + " AND "
                        + PointsContract.PointsEntry.COLUMN_NAME_E + " = ?";
        String[] selectionArgs = {String.valueOf(n), String.valueOf(e)};

        Cursor cursor = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null)
        {
            cursor.moveToNext();
            return cursor.getInt(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry._ID));
        }
        else return 0;
    }

    public int getPoint(String roomName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] roomsProjection = {
                PointsContract.RoomsEntry.COLUMN_NAME_ID_POINTS
        };
        String roomsSelection = PointsContract.RoomsEntry.COLUMN_ROOM_NAME + " = ?";
        String[] roomsSelectionArgs = new String[] {roomName};

        Cursor rooms = db.query(
                PointsContract.RoomsEntry.TABLE_NAME,
                roomsProjection,
                roomsSelection,
                roomsSelectionArgs,
                null,
                null,
                null
        );

        if (rooms != null) rooms.moveToNext();
        else return 0;

        String[] pointsProjection = {
                PointsContract.PointsEntry._ID,
                PointsContract.PointsEntry.COLUMN_NAME_FLOOR,
                PointsContract.PointsEntry.COLUMN_NAME_N,
                PointsContract.PointsEntry.COLUMN_NAME_E
        };
        String pointsSelection = PointsContract.PointsEntry._ID + " = ?";
        String[] pointsSelectionArgs = new String[] {
                String.valueOf(rooms.getInt(rooms.getColumnIndexOrThrow(PointsContract.RoomsEntry.COLUMN_NAME_ID_POINTS)))};

        Cursor points = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                pointsProjection,
                pointsSelection,
                pointsSelectionArgs,
                null,
                null,
                null
        );

        if (points != null)
        {
            points.moveToNext();
            return points.getInt(points.getColumnIndexOrThrow(PointsContract.PointsEntry._ID));
        }
        else return 0;
    }

    public Cursor getPoint(String[] MACs)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                PointsContract.PointsEntry._ID
        };
        String selection;
        String[] selectionArgs;

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
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?"
                            + " OR "
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC1 + " = ?"
                            + " AND "
                            + PointsContract.PointsEntry.COLUMN_NAME_MAC2 + " = ?";

                    selectionArgs = new String[] {MACs[0], MACs[1], MACs[1], MACs[0]};
                    break;

                default:
                    return null;
            }
        }
        else return null;

        Cursor cursor = db.query(
                PointsContract.PointsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() == 1)
        {
            return cursor;
        }
        else return null;
    }
}
