package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class ChooseRouteActivity extends Activity
{
    private Spinner spinnerLast;
    private Spinner spinnerFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        spinnerFirst = (Spinner) findViewById(R.id.listFirst);
        spinnerLast = (Spinner) findViewById(R.id.listLast);

        RoomsDbHelper helper = new RoomsDbHelper(ChooseRouteActivity.this);
        List<String> roomList = helper.getAllSpinnerContent();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ChooseRouteActivity.this,
                android.R.layout.simple_spinner_item,
                roomList
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLast.setAdapter(adapter);
        spinnerFirst.setAdapter(adapter);

        spinnerLast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}
