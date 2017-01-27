package com.kocomojo.simpleexample;

import android.app.Application;
import com.kocomojo.KocomojoSDK;

/**
 * Created by elijahwindsor on 1/27/17.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        KocomojoSDK.setApplication(this);

        KocomojoSDK
                .getInstance()
                .setApiKey("YOUR_API_KEY_HERE")
                .setRadiusInMiles(15.0)
                .setShowPushNotifications(true)
                .setPushNotificationTitle("Kocomojo")
                .setPushLaunchActivityClass(MainActivity.class)
                .setPushSmallIcon(R.mipmap.ic_launcher)
                .setDisabledImageName("kocomojo_disabled")
                .setEnabledImageName("kocomojo_enabled");
    }

}
