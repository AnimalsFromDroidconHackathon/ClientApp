package pl.tajchert.cracowdroidconhackathon;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.firebase.client.Firebase;

import io.relayr.android.RelayrSdk;

/**
 * Created by Tajchert on 06.12.2015.
 */
public class DroidconApplication extends Application {

    public static RelayrSdk relaySdk;
    public static Firebase firebase;
    public static TelephonyManager telecomManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://kote.firebaseio.com/");
        telecomManager =  (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
    }
}