package pl.tajchert.cracowdroidconhackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityMain extends AppCompatActivity implements FragmentCatList.ActivityContract {
    private static final String TAG = ActivityMain.class.getSimpleName();
    private static final int REQUEST_SCAN_CODE = 21;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent
    private CatItem catItem;

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
        if (requestCode == REQUEST_SCAN_CODE) {
            Log.d(TAG, "onActivityResult: ");
            if (data != null && data.hasExtra("code")) {
                String code = data.getStringExtra("code");
                if (code != null && code.length() > 0) {
                    Toast.makeText(ActivityMain.this, "CODE: " + code, Toast.LENGTH_LONG).show();
                    if (DroidconApplication.firebase != null) {
                        if (code.contains("http://kote-web/")) {
                            code = code.substring(code.lastIndexOf("-web/") + 5, code.length());
                            code = code.toLowerCase().trim().replace(".", "").replace("#", "").replace("$", "").replace("[", "").replace("]", "");
                            setOwnerForCat(code);
                        }
                    }
                }
            }
        }

        if (IntentHelper.FILE_PICK == requestCode && resultCode == RESULT_OK) {
            Uri returnUri = data.getData();
            String filePath = DocumentHelper.getPath(this, returnUri);
            //Safety check to prevent null pointer exception
            if (filePath == null || filePath.isEmpty()) return;
            chosenFile = new File(filePath);

            Log.d(TAG, "onActivityResult: choosen file=" + chosenFile);
            if (chosenFile != null) {
                createUpload(chosenFile);
                new UploadService(this).Execute(upload, new UiCallback());
            }
        }
    }


    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = "cat";
        upload.description = "cat2";
    }

    private void setOwnerForCat(String code) {
        Firebase child = DroidconApplication.firebase.child("animals").child(code);
        CatItem catItem = new CatItem();
        catItem.id = code;
        child.setValue(catItem);
        DroidconApplication.firebase.child("animals").child(catItem.id).child("lost").setValue("false");
        DroidconApplication.firebase.child("animals").child(catItem.id).child("ownerId").setValue(DroidconApplication.telecomManager.getDeviceId());
        DroidconApplication.firebase.child("animals").child(catItem.id).child("ownerName").setValue("Tajchercik");
        DroidconApplication.firebase.child("animals").child(catItem.id).child("type").setValue("cat");
        DroidconApplication.firebase.child("animals").child(catItem.id).child("name").setValue("Cat");
    }

    @Override
    public void takePhoto(CatItem catItem) {
        this.catItem = catItem;
        IntentHelper.chooseFileIntent(this);
    }

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            Log.d(TAG, "success: " + imageResponse);
            if (!imageResponse.success) {
                return;
            }
            Map<String, Object> url = new HashMap<>();
            url.put("pictureUrl", imageResponse.data.link);
            DroidconApplication.firebase.child("animals").child(catItem.id).updateChildren(url);
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            error.printStackTrace();
        }
    }
}
