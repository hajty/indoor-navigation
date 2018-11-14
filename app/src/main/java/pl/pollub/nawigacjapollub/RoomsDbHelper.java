package pl.pollub.nawigacjapollub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RoomsDbHelper extends SQLiteOpenHelper {

    public RoomsDbHelper(Context context) {
        super(context, RoomsContract.DATABASE_NAME, null, RoomsContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] groundFloor = {"E114, E115, E116"};

        db.execSQL(RoomsContract.RoomsEntry.CREATE_TABLE);

        for(int i=0; i<groundFloor.length; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RoomsContract.RoomsEntry.COLUMN_NAME_ROOM, groundFloor[i]);

            db.insert(RoomsContract.RoomsEntry.TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RoomsContract.RoomsEntry.DELETE_TABLE);
        onCreate(db);
    }

    public String[] getAllSpinnerContent(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RoomsContract.RoomsEntry.COLUMN_NAME_ROOM;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow(RoomsContract.RoomsEntry.COLUMN_NAME_ROOM));
                spinnerContent.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        return allSpinner;
    }
}
