package pl.tajchert.cracowdroidconhackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = ActivityMain.class.getSimpleName();
    private static final int REQUEST_SCAN_CODE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        Intent intent = new Intent(this, ActivityScanCode.class);
        startActivityForResult(intent, REQUEST_SCAN_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SCAN_CODE) {
            Log.d(TAG, "onActivityResult: ");
            if(data != null && data.hasExtra("code")) {
                String code = data.getStringExtra("code");
                if(code != null) {
                    Toast.makeText(ActivityMain.this, "CODE: " + code, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
