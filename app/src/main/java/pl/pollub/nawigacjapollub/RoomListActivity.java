package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class RoomListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomslist);

        final ListView listView = (ListView) findViewById(R.id.list);
        PointsDbHelper dbHelper = new PointsDbHelper(this);
        List<String> roomList = dbHelper.getAllSpinnerContent();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                RoomListActivity.this,
                android.R.layout.simple_list_item_1,
                roomList
        );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = listView.getAdapter().getItem(position);
                String value= obj.toString();
                Intent intent = new Intent(RoomListActivity.this, ChooseRouteActivity.class);
                intent.putExtra("value", value);
                finish();
            }
        });


    }
}
