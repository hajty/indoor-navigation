package pl.pollub.nawigacjapollub;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.security.SecureRandom;
import java.util.List;

public class ChooseRouteActivity extends AppCompatActivity {

    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        spinner = (Spinner) findViewById(R.id.listFirst);
        RoomsDbHelper helper = new RoomsDbHelper(ChooseRouteActivity.this);
        List<String> roomList = helper.getAllSpinnerContent();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ChooseRouteActivity.this,
                android.R.layout.simple_spinner_item,
                roomList
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
