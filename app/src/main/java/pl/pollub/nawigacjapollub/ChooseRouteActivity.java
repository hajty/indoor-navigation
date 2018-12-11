package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ChooseRouteActivity extends Activity
{
    private TextView startPoint;
    private TextView finishPoint;

    public final static int REQUEST_CODE_CHOOSE_START = 1;
    public final static int REQUEST_CODE_CHOOSE_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        startPoint = findViewById(R.id.startPoint);
        finishPoint = findViewById(R.id.finishPoint);
    }

    public void buttonChooseStart(View v)
    {
        Intent intent = new Intent(this, RoomListActivity.class);

        startActivityForResult(intent, REQUEST_CODE_CHOOSE_START);
    }

    public void buttonChooseEnd(View v)
    {
        Intent intent = new Intent(this, RoomListActivity.class);

        startActivityForResult(intent, REQUEST_CODE_CHOOSE_FINISH);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CHOOSE_START && resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            String value = bundle.getString("result");

            startPoint.setText(value);
        }

        if(requestCode == REQUEST_CODE_CHOOSE_FINISH && resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            String value = bundle.getString("result");

            finishPoint.setText(value);
        }
    }
}
