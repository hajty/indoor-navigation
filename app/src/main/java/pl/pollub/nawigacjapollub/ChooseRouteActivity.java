package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class ChooseRouteActivity extends Activity
{
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        textView1 = (TextView)findViewById(R.id.startPoint);
        textView2 = (TextView) findViewById(R.id.finishPoint);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRouteActivity.this, RoomListActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRouteActivity.this, RoomListActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String value = bundle.getString("value");
            textView1.setText(value);
            textView2.setText(value);
        }
    }
}
