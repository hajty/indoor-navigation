package pl.pollub.nawigacjapollub;

import android.provider.BaseColumns;

public final class PointsContract
{
    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "pollub_nav.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private PointsContract(){}

    public static class PointsEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "points";

        public static final String COLUMN_NAME_DEPARTMENT = "department";
        public static final String COLUMN_NAME_FLOOR = "floor";
        public static final String COLUMN_NAME_SSID1 = "ssid1";
        public static final String COLUMN_NAME_MAC1 = "mac1";
        public static final String COLUMN_NAME_SSID2 = "ssid2";
        public static final String COLUMN_NAME_MAC2 = "mac2";
        public static final String COLUMN_NAME_N = "n";
        public static final String COLUMN_NAME_E = "e";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_DEPARTMENT + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_FLOOR + INTEGER_TYPE + COMMA_SEP +
                COLUMN_NAME_SSID1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MAC1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_SSID2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_MAC2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_N + DOUBLE_TYPE + COMMA_SEP +
                COLUMN_NAME_E + DOUBLE_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class EdgesEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "edges";

        public static final String COLUMN_NAME_FROM_POINT_ID = "from_point_id";
        public static final String COLUMN_NAME_TO_POINT_ID = "to_point_id";
        public static final String COLUMN_NAME_LENGTH = "length";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_FROM_POINT_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_NAME_TO_POINT_ID + INTEGER_TYPE + COMMA_SEP +
                COLUMN_NAME_LENGTH + INTEGER_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class RoomsEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "rooms";

        public static final String COLUMN_NAME_ID_POINTS = "id_points";
        public static final String COLUMN_ROOM_NAME = "room_name";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_ID_POINTS+ INTEGER_TYPE + COMMA_SEP +
                COLUMN_ROOM_NAME  + TEXT_TYPE + ")";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
