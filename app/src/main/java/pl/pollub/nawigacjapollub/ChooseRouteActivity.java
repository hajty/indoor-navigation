package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ChooseRouteActivity extends Activity
{
    private boolean mode;
    private String startPoint;
    private String finishPoint;
    private TextView textViewStartPoint;
    private TextView textViewFinishPoint;
    private CheckBox checkBoxInvalid;
    private Button buttonChooseRoute;

    public final static int REQUEST_CODE_CHOOSE_START = 1;
    public final static int REQUEST_CODE_CHOOSE_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        textViewStartPoint = findViewById(R.id.startPoint);
        textViewFinishPoint = findViewById(R.id.finishPoint);
        checkBoxInvalid = findViewById(R.id.checkBoxInvalid);
        buttonChooseRoute = findViewById(R.id.buttonChooseRoute);

        buttonChooseRoute.setEnabled(false);
    }

    private boolean areDataCorrect()
    {
        return (!textViewStartPoint.getText().equals("Nie wybrano...")
                &&
                !textViewFinishPoint.getText().equals("Nie wybrano..."));
    }

    public void buttonChooseStartOnClick(View v)
    {
        Intent intent = new Intent(this, RoomListActivity.class);
        intent.putExtra("request_code", REQUEST_CODE_CHOOSE_START);

        startActivityForResult(intent, REQUEST_CODE_CHOOSE_START);
    }

    public void buttonChooseEndOnClick(View v)
    {
        Intent intent = new Intent(this, RoomListActivity.class);
        intent.putExtra("request_code", REQUEST_CODE_CHOOSE_FINISH);

        startActivityForResult(intent, REQUEST_CODE_CHOOSE_FINISH);
    }

    public void buttonChooseRouteOnClick(View v)
    {
        mode = checkBoxInvalid.isChecked();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("mode", mode);
        returnIntent.putExtra("startPoint", startPoint);
        returnIntent.putExtra("finishPoint", finishPoint);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CHOOSE_START && resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            this.startPoint = bundle.getString("result");

            textViewStartPoint.setText(this.startPoint);
        }

        if(requestCode == REQUEST_CODE_CHOOSE_FINISH && resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            this.finishPoint = bundle.getString("result");

            textViewFinishPoint.setText(this.finishPoint);
        }

        if (this.areDataCorrect()) buttonChooseRoute.setEnabled(true);
        else buttonChooseRoute.setEnabled(false);
    }
}
