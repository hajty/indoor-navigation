package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class RoomListActivity extends Activity
{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomslist);

        listView = findViewById(R.id.list);
        PointsDbHelper dbHelper = new PointsDbHelper(this);
        List<String> roomList = dbHelper.getAllRoomsAsList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                RoomListActivity.this,
                android.R.layout.simple_list_item_1,
                roomList
        );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String result = listView.getAdapter().getItem(position).toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


    }
}
