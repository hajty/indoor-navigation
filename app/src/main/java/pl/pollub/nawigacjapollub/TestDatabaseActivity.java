package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TestDatabaseActivity extends Activity
{
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);
        listView = (ListView)findViewById(R.id.listView);
    }

    public void getRecords(View v)
    {
        PointsDbHelper db = new PointsDbHelper(this);
        Cursor cursor = db.getAllPoints();

        List items = new ArrayList<>();

        items.add("POINTS");

        while(cursor.moveToNext())
        {
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry._ID)));
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_DEPARTMENT)));
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_FLOOR)));
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_SSID1)));
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_MAC1)));
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_SSID2)));
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_MAC2)));
            items.add(cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_N)));
            items.add(cursor.getDouble(cursor.getColumnIndexOrThrow(PointsContract.PointsEntry.COLUMN_NAME_E)));
        }
        cursor.close();

        cursor = db.getAllEdges();

        items.add("EDGES");

        while(cursor.moveToNext())
        {
            items.add("ID");
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry._ID)));
            items.add("FROM POINT ID");
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry.COLUMN_NAME_FROM_POINT_ID)));
            items.add("TO POINT ID");
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry.COLUMN_NAME_TO_POINT_ID)));
            items.add("LENGTH");
            items.add(cursor.getString(cursor.getColumnIndexOrThrow(PointsContract.EdgesEntry.COLUMN_NAME_LENGTH)));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                items);

        listView.setAdapter(arrayAdapter);
    }
}
