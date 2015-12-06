package pl.tajchert.cracowdroidconhackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.relayr.android.RelayrSdk;
import io.relayr.java.model.User;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

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

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        if (RelayrSdk.isUserLoggedIn()) {
            //updateUiForALoggedInUser();
        } else {
            //updateUiForANonLoggedInUser();
            logIn();
        }*/
    }

    private void logIn() {
        RelayrSdk.logIn(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override public void onCompleted() {}

                    @Override public void onError(Throwable e) {
                        Toast.makeText(ActivityMain.this, "Unsuccessful login", Toast.LENGTH_SHORT).show();
                        //updateUiForANonLoggedInUser();
                        e.printStackTrace();
                    }

                    @Override public void onNext(User user) {
                        Toast.makeText(ActivityMain.this, "Success!", Toast.LENGTH_SHORT).show();
                        invalidateOptionsMenu();
                        //updateUiForALoggedInUser();
                    }
                });
    }

    private void logOut() {
        //unSubscribeToUpdates();
        RelayrSdk.logOut();
        invalidateOptionsMenu();
        Toast.makeText(this, "LOG OUT", Toast.LENGTH_SHORT).show();
        //updateUiForANonLoggedInUser();
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
        Intent intent = new Intent(this, ActivityScanCode.class);
        startActivityForResult(intent, REQUEST_SCAN_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
