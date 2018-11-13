package pl.pollub.nawigacjapollub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void buttonWeiiOnClick(View v)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void buttonTestDatabaseOnClick(View v)
    {
        Intent intent = new Intent(this, TestDatabaseActivity.class);
        startActivity(intent);
    }
}
