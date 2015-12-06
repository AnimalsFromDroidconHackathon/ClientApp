package pl.tajchert.cracowdroidconhackathon;

import android.app.Application;

import io.relayr.android.RelayrSdk;

/**
 * Created by Tajchert on 06.12.2015.
 */
public class DroidconApplication extends Application {

    public static RelayrSdk relaySdk;
    @Override
    public void onCreate() {
        super.onCreate();
        //new RelayrSdk.Builder(this);
    }
}