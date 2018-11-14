package pl.pollub.nawigacjapollub;

import android.provider.BaseColumns;

public class RoomsContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rooms.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    private RoomsContract(){}

    public static class RoomsEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "rooms";

        public static final String COLUMN_NAME_ROOM = "roomNumber";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_ROOM  + TEXT_TYPE + ") ";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
