package pl.tajchert.cracowdroidconhackathon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import java.lang.ref.WeakReference;

/**
 * Created by whiter
 */
public class NotificationHelper {
    public final static String TAG = NotificationHelper.class.getSimpleName();

    private WeakReference<Context> mContext;


    public NotificationHelper(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void createUploadingNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_upload);
        mBuilder.setContentTitle("Uploading");


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.button_main));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());

    }

    public void createUploadedNotification(ImageResponse response) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_gallery);
        mBuilder.setContentTitle("Upload finished");

        mBuilder.setContentText(response.data.link);

        mBuilder.setColor(mContext.get().getResources().getColor(R.color.button_main));


        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.data.link));
        PendingIntent intent = PendingIntent.getActivity(mContext.get(), 0, resultIntent, 0);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);

        Intent shareIntent = new Intent(Intent.ACTION_SEND, Uri.parse(response.data.link));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, response.data.link);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pIntent = PendingIntent.getActivity(mContext.get(), 0, shareIntent, 0);
        mBuilder.addAction(new NotificationCompat.Action(R.drawable.abc_ic_menu_share_mtrl_alpha,
                "Share", pIntent));

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }

    public void createFailedUploadNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        mBuilder.setContentTitle("Failed");


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.button_main));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }
}
