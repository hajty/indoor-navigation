package pl.pollub.nawigacjapollub;

import android.provider.BaseColumns;

public final class PointsContract
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "points.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private PointsContract(){}

    public static class PointsEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "points";

        public static final String COLUMN_NAME_SSID1 = "ssid1";
        public static final String COLUMN_NAME_MAC1 = "mac1";
        public static final String COLUMN_NAME_SSID2 = "ssid2";
        public static final String COLUMN_NAME_MAC2 = "mac2";
        public static final String COLUMN_NAME_N = "n";
        public static final String COLUMN_NAME_E = "e";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_SSID1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MAC1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SSID2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MAC2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_N + DOUBLE_TYPE + COMMA_SEP +
                COLUMN_NAME_E + DOUBLE_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
